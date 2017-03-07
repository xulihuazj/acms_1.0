package cn.edu.haut.cssp.acms.util.json;

import java.io.InputStream;
import java.io.Reader;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * 
 * @ProjectName：platform-common
 * @ClassName：JSONUtil
 * @Description：json工具类
 * @author: 任瑞修
 * @date: 2013-11-5 下午4:23:48
 *
 * 建议使用JsonMapper替换该工具类，下个版本中该工具类将设置为@Deprecated
 * @modify：任瑞修
 * @date：2014-8-5 08:30
 */
public final class JSONUtil {
    
    private JSONUtil() {
        
    }

	/**
	 * 
	 * @Description：转换JSON字符串为指定对象
	 * @author: 任瑞修
	 * @date: 2013-11-12 上午9:44:03
	 * @param jsonString 要转换的JSON字符串
	 * @param <T> 模板
	 * @param clazz 要转换为的类型
	 * @return T 转换结果
	 * @throws JSONException 异常
	 */
	public static <T> T toObjecFromJSONString(String jsonString, Class<T> clazz) throws JSONException {
		return JsonMapper.nonEmptyMapper().fromJson(jsonString, clazz);
	}
	
	/**
	 * 
	 * @Description：从reader中读取json信息并转换为指定对象
	 * @author: 任瑞修
	 * @date: 2013-11-6 上午11:55:36
	 * @param reader 包含有JSON信息的输入
	 * @param <T> 模板
	 * @param clazz 要转换为的类型
	 * @return T 转换结果
	 * @throws JSONException 异常
	 */
	public static <T> T toObjecFromReader(Reader reader, Class<T> clazz) throws JSONException {
		return JsonMapper.nonEmptyMapper().fromJson(reader, clazz);
	}
	
    /**
     * 
     * @Description：把obj转换为指定对象
     * @author: 任瑞修
     * @date: 2013-11-5 下午4:25:03
     * @param obj 要转换的对象
     * @param <T> 模板
     * @param clazz 要转换为的类型
     * @return T 转换结果
     * @throws JSONException 异常
     */
    public static <T> T toSimpleJavaBean(Object obj, Class<T> clazz) throws JSONException {
    	return JsonMapper.nonEmptyMapper().fromJson(obj, clazz);
    }
    
    /**
     * 
     * @Description：把obj转换为指定类型的对象
     * @author: 任瑞修
     * @date: 2013-11-5 下午5:21:44
     * @param obj 要转换的对象
     * @param <T> 模板
     * @param type 要转换为的类型
     * @return T 转换结果
     * @throws JSONException 异常
     */
    public static <T> T toSimpleJavaBean(Object obj, TypeReference<T> type) throws JSONException {
    	return JsonMapper.nonEmptyMapper().fromJson(obj, type);
    }
    
    /**
     * 
     * @Description：把json字符串转换为指定类型的对象
     * @author: 任瑞修
     * @date: 2013-11-6 上午10:04:32
     * @param jsonString 要转换的json字符串
     * @param <T> 模板
     * @param type 要转换为的类型
     * @return T 转换结果
     * @throws JSONException 异常
     */
    public static <T> T toSimpleJavaBean(String jsonString, TypeReference<T> type) throws JSONException {
    	return JsonMapper.nonEmptyMapper().fromJson(jsonString, type);
    }
    
    /**
     * 
     * @Description：把json字节数组转换为指定类型的对象
     * @author: renruixiu
     * @date: 2014年3月20日
     * @param jsonByteArray 要转换的json字节数组
     * @param type
     * @return
     * @throws JSONException
     */
    public static <T> T toJavaBean(byte[] jsonByteArray, TypeReference<T> type) throws JSONException {
    	return JsonMapper.nonEmptyMapper().fromJson(jsonByteArray, type);
    }
    
    /**
     * 
     * @Description：把json输入流转换为指定类型的对象
     * @author: renruixiu
     * @date: 2014年3月21日
     * @param jsonInputStream 要转换的json输入流
     * @param type
     * @return
     * @throws JSONException
     */
    public static <T> T toJavaBean(InputStream jsonInputStream, TypeReference<T> type) throws JSONException {
    	return JsonMapper.nonEmptyMapper().fromJson(jsonInputStream, type);
    }
    
    /**
     * 
     * @Description：把obj转换为json字符串
     * @author: renruixiu
     * @date: 2014年3月20日
     * @param obj 要转换的对象
     * @return
     * @throws JSONException
     */
    public static String toJSONString(Object obj) throws JSONException {
    	return JsonMapper.nonEmptyMapper().toJson(obj);
    }
    
    /**
     * 
     * @Description：把obj转换为json字节数组
     * @author: renruixiu
     * @date: 2014年3月20日
     * @param obj
     * @return
     * @throws JSONException
     */
    public static byte[] toJSONBytes(Object obj) throws JSONException {
    	return JsonMapper.nonEmptyMapper().toJsonBytes(obj);
    }
    
    /**
     * 
     * 输出JSONP格式数据
     * @param functionName
     * @param object
     * @return
     */
	public static String toJsonP(String functionName, Object object) throws JSONException {
		return JsonMapper.nonEmptyMapper().toJsonP(functionName, object);
	}
    
	/**
	 * 
	 * 当JSON里只含有Bean的部分属性时，更新一个已存在Bean，只覆盖该部分的属性.
	 * @param jsonString
	 * @param object
	 */
	public static void update(String jsonString, Object object) throws JSONException {
		JsonMapper.nonEmptyMapper().update(jsonString, object);
	}
}
