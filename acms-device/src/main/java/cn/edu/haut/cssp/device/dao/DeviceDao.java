package cn.edu.haut.cssp.device.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.springframework.stereotype.Repository;

import com.xdja.cssp.oms.core.util.OMS;
import com.xdja.cssp.oms.device.bean.DeviceInfoBean;
import com.xdja.cssp.oms.device.bean.DeviceQueryBean;
import com.xdja.cssp.oms.device.entity.DeviceKeyInfo;
import com.xdja.platform.microservice.db.nutz.Dao;

/**
 * 操作远程库 ams
 * Project Name：oms-service-device
 * ClassName：DeviceDao
 * Description：设备信息dao
 * @author: 谢文超
 * @date: 2015-10-22 下午3:43:52
 * note:
 * 
 */
@Repository
public class DeviceDao {
	
	private Dao dao = Dao.use(OMS.DB_AMS);
	
	private Dao umsDao = Dao.use(OMS.DB_UMS);
	
	private Dao omsDao = Dao.use(OMS.DB_OMS);
	
	private Dao amsCoreDao = Dao.use(OMS.DB_AMSCORE);

	 public List<Map<String, Object>> queryDevice(DeviceQueryBean queryBean) {
		 StringBuilder sql = new StringBuilder();
		 Map<String, Object> ps = new HashMap<String, Object>();
		 sql.append(" SELECT cert.c_card_no cardNo, cert.n_ca_alg alg, cert.c_cert_sn sn, cert.n_cert_type certType,")
		 	.append(" mobile.c_imei imei, mobile.c_model model, mobile.c_os_name osName, mobile.c_os_version osVersion,")
		 	.append(" mobile.c_sn_no snNo, assert.c_serial_code serialCode, assert.n_asset_type assertType, assert.n_time time")
		 	.append(" FROM t_cert_info cert INNER JOIN t_asset_info assert ON assert.c_card_no = cert.c_card_no")
		 	.append(" LEFT JOIN t_asset_mobiles_info mobile ON assert.n_id = mobile.n_asset_id")
		 	.append(" WHERE assert.n_asset_type <> @type");
		 
		 ps.put("type", DeviceInfoBean.ASSERT_TYPE_6);
		 switch (queryBean.getSearchType()) {
			case DeviceQueryBean.SEARCH_TYPE_CARD_NO: {
				sql.append(" AND cert.c_card_no =  @cardNo");
				ps.put("cardNo", queryBean.getSearchValue());
				break;
			}
			case DeviceQueryBean.SEARCH_TYPE_IMEI: {
				sql.append(" AND mobile.c_imei =  @imei");
				ps.put("imei", queryBean.getSearchValue());
				break;
			}
			case DeviceQueryBean.SEARCH_TYPE_SERIAL_CODE: {
				sql.append(" AND ( assert.c_serial_code = @serialCode");
				sql.append(" OR mobile.c_sn_no = @snNo )");
				ps.put("serialCode", queryBean.getSearchValue());
				ps.put("snNo", queryBean.getSearchValue());
				break;
			}
		}
	
		return dao.queryForList(sql.toString(), ps);
	 }
	 
	 /**
	  * 
	  * 方法描述：查询指定设备的数字证书
	  * @author: 谢文超
	  * @date: 2015-10-22 下午8:12:39
	  * @param cardNo	设备卡号
	  * @param isQueryCert	是否查询证书 （Base64编码）
	  * @return
	  */
	 public List<Map<String, Object>> queryDeviceCert(String cardNo, boolean isQueryCert) {
		 StringBuilder sql = new StringBuilder();
		 Map<String, Object> ps = new HashMap<String, Object>();
		 sql.append(" SELECT cert.n_id id, cert.c_card_no cardNo, cert.n_ca_alg alg, cert.n_cert_type certType,")
		 	.append(" cert.c_cert_sn sn, cert.n_cert_state certStatus, cert.c_public_key pubkye,")
		 	.append(" cert.n_not_before beforeTime, cert.n_not_after afterTime");
		 if (isQueryCert) {
			 sql.append(", cert.c_cert cert ");
		 }
		 sql.append(" FROM t_cert_info cert INNER JOIN t_asset_info assert ON assert.c_card_no = cert.c_card_no")
		 	.append(" WHERE assert.c_card_no =  @cardNo");
		 ps.put("cardNo", cardNo);
		 
		 return dao.queryForList(sql.toString(), ps);
	 }
	 
	 
	 public Map<String, Object> queryCertBySN(String sn) {
		 StringBuilder sql = new StringBuilder();
		 Map<String, Object> ps = new HashMap<String, Object>();
		 sql.append(" SELECT cert.n_id id, cert.c_card_no cardNo, cert.n_ca_alg alg, cert.c_cert cert,")
		 	.append(" cert.n_cert_type certType, cert.c_cert_sn sn, cert.n_cert_state certStatus, cert.c_public_key pubkye,")
		 	.append(" cert.n_not_before beforeTime, cert.n_not_after afterTime")
		 	.append(" FROM t_cert_info cert ")
		 	.append(" WHERE cert.c_cert_sn = @sn");
		 
		 ps.put("sn", sn);
	
		 return dao.queryForMap(sql.toString(), ps);
	 }
	 
	 
	 /**
	  * 
	  * 方法描述：查询指定设备的数字证书SN
	  * @author: 谢文超
	  * @date: 2015-10-22 下午8:12:39
	  * @param cardNo	设备卡号
	  * @return
	  */
	 public List<Map<String, Object>> queryDeviceCertSN(String cardNo) {
		 StringBuilder sql = new StringBuilder();
		 Map<String, Object> ps = new HashMap<String, Object>();
		 sql.append(" SELECT cert.c_cert_sn sn, cert.n_ca_alg alg FROM t_cert_info cert")
		 	.append(" INNER JOIN t_asset_info assert")
		 	.append(" ON assert.c_card_no = cert.c_card_no")
		 	.append(" WHERE assert.c_card_no = @cardNo");
		 ps.put("cardNo", cardNo);
		 
		 return dao.queryForList(sql.toString(), ps);
	 }
	 
	 /**
	  * 
	  * 方法描述：查询卡号对应账号
	  * @author: 谢文超
	  * @date: 2015-10-27 下午7:20:47
	  * @param cardNo
	  * @return
	  */
	 public String queryAccountByCardNo(String cardNo) {
		 StringBuilder sb = new StringBuilder();
		 sb.append(" SELECT assert.c_account account, assert.c_card_no cardNo FROM t_account_asset assert")
		   .append(" WHERE assert.c_card_no = @cardNo");
		 Map<String, Object> ps = new HashMap<String, Object>();
		 
		 ps.put("cardNo", cardNo);
		 return umsDao.queryForStr(sb.toString(), ps);
	 }
	 
	 /**
	  * 
	  * 方法描述：根据卡号查询设备外壳序列号
	  * @author: 谢文超
	  * @date: 2015-10-28 上午10:22:00
	  * @param cardNo 设备卡号
	  * @return
	  */
	 public Map<String, Object> queryDeviceSN(String cardNo) {
		StringBuilder sql = new StringBuilder();
		Map<String, Object> ps = new HashMap<String, Object>();
		sql.append(" SELECT assert.c_card_no cardNo, mobile.c_sn_no snNo, assert.c_serial_code serialCode, ")
		 	.append(" assert.n_asset_type assertType ")
		 	.append(" FROM t_asset_info assert LEFT JOIN t_asset_mobiles_info mobile")
		 	.append(" ON assert.n_id = mobile.n_asset_id")
		 	.append(" WHERE assert.c_card_no = @cardNo");
		 
		ps.put("cardNo", cardNo);
	
		return dao.queryForMap(sql.toString(), ps);
	 }
	 
	 public void saveDeviceKey(DeviceKeyInfo deviceKeyInfo) {
		this.omsDao.insert(deviceKeyInfo);
	 }

	 public DeviceKeyInfo getDeviceKeyByCardno(String cardno, Integer opt) {
		return this.omsDao.fetch(DeviceKeyInfo.class, 
				Cnd.where("cardno", "=", cardno).and("opt", "=", opt));
	 }

	 public void updateDeviceKey(DeviceKeyInfo keyInfo) {
		this.omsDao.updateIgnoreNull(keyInfo);
	 }

	 public List<DeviceKeyInfo> queryDeviceKeysForAddOrDel(Integer status, Integer opt) {
		return this.omsDao.query(DeviceKeyInfo.class, Cnd.where("status", "=", status).and("opt", "=", opt));
	 }

	 /**
	  * 根据uid获取卡关所属客户id
	  * @Description：
	  * @author: wupengwei
	  * @date: Feb 24, 2016 11:34:30 AM
	  * @param uid
	  * @return
	  */
	public Long queryCustomerId(String uid) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT n_customer_id FROM t_asset_info  ")
			.append("WHERE c_card_no = ( ")
			.append("SELECT c_card_no FROM t_terminal_unlock_code WHERE c_card_uid = @uid) ");
		Map<String, Object> param = new HashMap<>();
		param.put("uid", uid);
		List<Map<String,Object>> list = amsCoreDao.queryForList(sqlBuilder.toString(), param);
		return list.size() == 0 ? null : (Long)list.get(0).get("n_customer_id");
	}
	 
	 
}
