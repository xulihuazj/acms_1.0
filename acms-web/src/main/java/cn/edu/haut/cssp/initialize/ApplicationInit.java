package cn.edu.haut.cssp.initialize;

/**
 *	系统初始化
 * Description:
 * @author: xulihua
 * @date: 2017年1月5日上午11:11:43
 */

import java.io.IOException;
import java.net.BindException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

/**
 * acms 工程初始化
 * 
 */
public class ApplicationInit {

	/**
	 * 系统配置文件
	 */
	public static Prop sysProp;

	private static final Logger logger = LoggerFactory.getLogger(ApplicationInit.class);

	private static final String SERVICES = "oms";

	/**
	 * 默认服务ip 本机
	 */
	public static final String SERVERIP = "127.0.0.1";
	/**
	 * 默认服务端口
	 */
	public static final int SERVERPORT = 6666;

	/**
	 * 默认超时时间
	 */
	public static final int SESSION_TIMEOUT = 10000;

	private static RedisClient client;

	private static Prop prop = PropUtil.getSystemProp();

	private static IFastDfsClient fastDfsClient;

	private static final String IP = "127.0.0.1";
	private static final int PORT = 6666;
	private static final int MAXWORKTHREAD = 5000;
	
	/**
	 * 初始化服务器（注册）
	 * 
	 * @Description：
	 * @author 谢文超
	 * @date 2015-11-16
	 */
	public static void initialize() {
		sysProp = PropKit.use("system.properties");
		// 注册rpc service
		Service rpcService = null;
		String[] services = StringUtils.split(sysProp.get("rpc.services", SERVICES), "|");

		for (String service : services) {
			rpcService = new Service();
			rpcService.setAddr(sysProp.get(("rpc." + service + ".ip"), SERVERIP));
			rpcService.setPort(sysProp.getInt(("rpc." + service + ".port"), SERVERPORT));
			rpcService.setTimeoutMillis(sysProp.getInt(("rpc." + service + ".timeoutMillis"), SESSION_TIMEOUT));
			ServicePool.addService(service, rpcService);
			logger.info("注册rpc service（{}==>{}:{}）成功", service, rpcService.getAddr(), rpcService.getPort());
		}

		WebConstants.HTTPS_PORT = sysProp.getInt("https.port", 8443);
		
		//启动本地的rpc服务
		String ip = prop.get("rpc.ip", IP);
		int port = prop.getInt("rpc.port", PORT);
		int maxWorkThread = prop.getInt("rpc.maxWorkThread", MAXWORKTHREAD);
		try {
			ProviderStarter.NETTY.setMaxWorkThread(maxWorkThread).start(ip, port);
			
			logger.info("====>OMS服务{}:{}启动成功，最大工作线程数为{}个！", ip, port, maxWorkThread);
		} catch (BindException e) {
			logger.error("服务绑定异常", e);
		} catch (InterruptedException e) {
			logger.error("线程异常", e);
		}

		// 初始化数据源
		try {
			initDataSource();
		} catch (Exception e) {
			logger.error("初始化数据源异常{}", e.getMessage(), e);
			return;
		}

		// 初始化rabbitMQ地址
		try {
			MQSetting.init(sysProp.get("rabbitMq.username"), sysProp.get("rabbitMq.password"),
					sysProp.get("rabbitMq.url"), sysProp.get("rabbitMq.APP_FLAG"));
			logger.info("初始化rabbitMQ成功");
		} catch (Exception e) {
			logger.error("初始化rabbitMQ失败", e);
		}

		/**
		 * 初始化Redis<br>
		 * 安通家配置服务
		 * 
		 * @author mayanpei
		 */
		initRedis();

		try {
			/**
			 * 推送服务初始化<br>
			 * 根据系统配置来判断启动老版本推送还是新版本推送
			 * 
			 * @author mayanpei
			 */
			if (prop.getBoolean("nps.use")) {
				PushServerInfo pushServerInfo = new PushServerInfo(prop.get("nps.ip"), prop.getInt("nps.port"),
						prop.getInt("nps.timeout"));
				IdGenServerInfo idGenServerInfo = new IdGenServerInfo(prop.get("nps.id.ip"),
						prop.getInt("nps.id.port"), prop.getInt("nps.id.timeout"));
				PushClient.init(pushServerInfo, idGenServerInfo);
				logger.info("使用新推送...........");
			} else {
				PnService.init(prop.get("pn.url"), prop.get("pn.port"), prop.get("pn.appId"));
				logger.info("使用老推送...........");
			}
		} catch (ThriftClientInitException e) {
			logger.error("推送初始化失败", e);
		} catch (Exception e) {
			logger.error("系统初始化失败", e);
		}
		
		// 初始化业务配置
		try {
			initConfigProperties();
		} catch (Exception e) {
			logger.error("初始化业务配置时异常{}", e.getMessage(), e);
			return;
		}

		// 分页初始化
		PagingConverter.pageNoMethodName = "getPageNo";
		PagingConverter.pageSizeMethodName = "getPageSize";
		PagingConverter.totalCountMethodName = "getTotalCount";
		PagingConverter.totalPageMethodName = "getTotalPage";
		PagingConverter.dataListMethodName = "getList";
	}

	private static void initDataSource() throws Exception {
		// 加载外部数据源
		String dbNamesStr = sysProp.get("dbnames");
		if (StrKit.isBlank(dbNamesStr)) {
			throw ServiceException.create("请在配置文件system.properties中添加dbNames的配置", null);
		}
		String[] dbNames = StringUtils.split(dbNamesStr, "|");
		String url = null;
		String userName = null;
		String password = null;
		DruidPlugin plugin = null;
		NutzDaoPlugin daoPlugin = null;
		for (String dbName : dbNames) {
			url = sysProp.get(dbName + ".jdbc.url");
			if (StrKit.isBlank(url)) {
				throw ServiceException.create("请在配置文件system.properties中添加" + dbName + "jdbcUrl的配置", null);
			}
			userName = sysProp.get(dbName + ".jdbc.userName");
			if (StrKit.isBlank(userName)) {
				throw ServiceException.create("请在配置文件system.properties中添加" + dbName + "userName的配置", null);
			}
			password = sysProp.get(dbName + ".jdbc.password");

			plugin = new DruidPlugin(url, userName, password);
			plugin.start();

			daoPlugin = new NutzDaoPlugin(OMS.DAO_PLUGIN_PREFIX + dbName, plugin);
			if (!sysProp.getBoolean(dbName + ".jdbc.debug", true)) {
				daoPlugin.disableDebug();
			}
			daoPlugin.start();
		}
	}

	/**
	 * 获取Redis 客户端实例
	 * 
	 * @author: mayanpei
	 * @date: 2015-12-16 下午4:43:34
	 * @return
	 */
	public static RedisClient getClient() {
		if (client == null) {
			initRedis();
		}
		return client;
	}

	/**
	 * redis 初始化
	 * 
	 * @author: mayanpei
	 * @date: 2015-12-21 下午5:35:59
	 */
	private static void initRedis() {
		try {
			client = RedisClientFactory.getClient(prop.get("redis.host"), prop.getInt("redis.port"));
			logger.info("redis客户端初始化成功.............");
		} catch (Exception e) {
			client = null;
			logger.error("redis 初始化失败", e);
		}
	}

	/**
	 * 获取fastdfs客户端
	 * 
	 * @author: mayanpei
	 * @date: 2016-1-14 下午7:55:38
	 * @return
	 * @throws IOException
	 */
	public static IFastDfsClient getFastDfsClient() throws IOException {
		if (fastDfsClient == null) {
			java.util.Properties properties = new java.util.Properties();
			properties.setProperty("fdfs.server.host", prop.get("fdfs.server.host"));
			properties.setProperty("fdfs.server.port", prop.get("fdfs.server.port"));
			properties.setProperty("fdfs.client.appid", prop.get("fdfs.client.appid"));
			properties.setProperty("fdfs.client.appkey", prop.get("fdfs.client.appkey"));
			return fastDfsClient = FastDfsClientBuilder.builder(properties);
		}
		return fastDfsClient;
	}

	public static String getPictureSuffix() {
		return prop.get("picture.suffix");
	}

	public static String getPackageSuffix() {
		return prop.get("package.suffix");
	}
	
	/**
	 * 加载业务配置
	 * @Description：
	 * @author: shaoyuehua
	 * @date: 2016-10-27 下午2:09:37
	 */
	private static void initConfigProperties() {
		Prop prop = PropKit.use("config.properties");
		String serviceConfig = prop.get("serviceConfig", null);
		String serviceConfigChecked = prop.get("serviceConfigChecked", null);
		
		if (StringUtils.isBlank(serviceConfig) || StringUtils.isBlank(serviceConfigChecked)) {
			throw ServiceException.create("初始化合作伙伴服务配置项异常,请检查配置文件！", null);
		}
		
		String[] serviceConfigArr = serviceConfig.split(",");
		String[] serviceConfigCheckedArr = serviceConfigChecked.split(",");
		for (int i = 0; i < serviceConfigArr.length; i++) {
			Constants.TPOMS_SERVICE_CONFIG.put(serviceConfigArr[i].split("-")[0], serviceConfigArr[i].split("-")[1]);
			Constants.TPOMS_SERVICE_CONFIG_CHECKED.put(serviceConfigCheckedArr[i].split("-")[0], Boolean.valueOf(serviceConfigCheckedArr[i].split("-")[1]));
		}
		
		if (Constants.TPOMS_SERVICE_CONFIG.size() != Constants.TPOMS_SERVICE_CONFIG_CHECKED.size()) {
			throw ServiceException.create("初始化合作伙伴服务配置项异常,请检查配置文件！", null);
		}
	}
}
