package cn.edu.haut.cssp.acms.util.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
/**
 * 新版本的json操作工具类，提供的方法比JSONUtil精减，但是更加灵活
 * Description:
 * @author: xulihua
 * @date: 2017年1月5日上午11:47:38
 */
public class JsonMapper {
	
	/**
	 * 输出所有属性到Json字符串
	 */
	private static final JsonMapper alwaysMapper = new JsonMapper();
	/**
	 * 获取输出所有属性到Json字符串的Mapper
	 */
	public static JsonMapper alwaysMapper() {
		return alwaysMapper;
	}

	/**
	 * 只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串
	 */
	private static final JsonMapper nonEmptyMapper = new JsonMapper(Include.NON_EMPTY);
	/**
	 * 获取只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper
	 */
	public static JsonMapper nonEmptyMapper() {
		return nonEmptyMapper;
	}
	
	/**
	 * 只输出非空属性到Json字符串
	 */
	private static final JsonMapper nonNullMapper = new JsonMapper(Include.NON_NULL);
	/**
	 * 获取只输出非空属性到Json字符串的Mapper
	 */
	public static JsonMapper nonNullMapper() {
		return nonNullMapper;
	}
	
	/**
	 * 只输出初始值被改变的属性到Json字符串
	 */
	private static final JsonMapper nonDefaultMapper = new JsonMapper(Include.NON_DEFAULT);
	/**
	 * 获取只输出初始值被改变的属性到Json字符串的Mapper
	 */
	public static JsonMapper nonDefaultMapper() {
		return nonDefaultMapper;
	}
	
	private ObjectMapper mapper;
	
	private JsonMapper() {
		this(null);
	}
	
	private JsonMapper(Include include) {
		mapper = new ObjectMapper();
		// 设置输出时包含属性的风格
		if (include != null) {
			mapper.setSerializationInclusion(include);
		}
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.getFactory().enable(Feature.ALLOW_COMMENTS);
        mapper.getFactory().enable(Feature.ALLOW_SINGLE_QUOTES);
	}
	
	/**
	 * 
	 * 把json输入流中的内容转换为指定类型的对象
	 * @param jsonInputStream
	 * @param type
	 * @return
	 * @throws JSONException
	 */
	public <T> T fromJson(InputStream jsonInputStream, TypeReference<T> type) throws JSONException {
		try {
    		return mapper.readValue(jsonInputStream, type);
    	} catch (Exception t) {
    		throw new JSONException("把json输入流中的内容转换为指定类型的对象（" + t.getMessage() + "）", t);
    	}
	}
	
	/**
	 * 
	 * 把json输入流中的内容转换为指定类型的对象
	 * @param jsonInputStream
	 * @param clazz
	 * @return
	 * @throws JSONException
	 */
	public <T> T fromJson(InputStream jsonInputStream, Class<T> clazz) throws JSONException {
		try {
			return mapper.readValue(jsonInputStream, clazz);
		} catch (Exception t) {
			throw new JSONException("把json输入流中的内容转换为指定类型的对象（" + t.getMessage() + "）", t);
		}
	}
	
	/**
	 * 
	 * 把json字节数组转换为指定类型的对象
	 * @param jsonByteArray
	 * @param type
	 * @return
	 * @throws JSONException
	 */
	public <T> T fromJson(byte[] jsonByteArray, TypeReference<T> type) throws JSONException {
    	try {
    		return mapper.readValue(jsonByteArray, type);
    	} catch (Exception t) {
    		throw new JSONException("把json字节数组转换为指定类型的对象是出错（" + t.getMessage() + "）", t);
    	}
    }
	
	/**
	 * 
	 * 把json字节数组转换为指定类型的对象
	 * @param jsonByteArray
	 * @param clazz
	 * @return
	 * @throws JSONException
	 */
	public <T> T fromJson(byte[] jsonByteArray, Class<T> clazz) throws JSONException {
		try {
			return mapper.readValue(jsonByteArray, clazz);
		} catch (Exception t) {
			throw new JSONException("把json字节数组转换为指定类型的对象是出错（" + t.getMessage() + "）", t);
		}
	}
	
	/**
	 * 	
	 * 把json字符串转换为指定类型的对象
	 * @param jsonString
	 * @param type
	 * @return
	 * @throws JSONException
	 */
	public <T> T fromJson(String jsonString, TypeReference<T> type) throws JSONException {
    	try {
    		return mapper.readValue(jsonString, type);
    	} catch (Exception t) {
    		throw new JSONException("把json字符串转换为指定类型的对象出错（" + t.getMessage() + "）", t);
    	}
    }
	
	/**
	 * 	
	 * 转换json字符串为指定对象
	 * @param jsonString
	 * @param clazz
	 * @return
	 * @throws JSONException
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) throws JSONException {
		try {
			return mapper.readValue(jsonString, clazz);
		} catch (Exception t) {
			throw new JSONException("把json字符串转换为指定类型的对象出错（" + t.getMessage() + "）", t);
		}
	}
	
	/**
	 * 
	 * 把obj转换为指定类型的对象
	 * @param obj
	 * @param type
	 * @return
	 * @throws JSONException
	 */
	public <T> T fromJson(Object obj, TypeReference<T> type) throws JSONException {
    	if (obj instanceof String) {
    		return fromJson(obj.toString(), type);
    	}
    	try {
    		return mapper.convertValue(obj, type);
    	} catch (Exception t) {
    		throw new JSONException("把obj转换为指定类型的对象出错（" + t.getMessage() + "）", t);
    	}
    }
	
	/**
	 * 
	 * 把obj转换为指定对象
	 * @param obj
	 * @param clazz
	 * @return
	 * @throws JSONException
	 */
	public <T> T fromJson(Object obj, Class<T> clazz) throws JSONException {
    	if (obj instanceof String) {
    		return fromJson(obj.toString(), clazz);
    	}
        try {
            return mapper.convertValue(obj, clazz);
        } catch (Exception t) {
            throw new JSONException("把obj转换为指定对象出错（" + t.getMessage() + ")", t);
        }
    }
	
	/**
	 * 
	 * 从reader中读取json信息并转换为指定对象
	 * @param reader
	 * @param type
	 * @return
	 * @throws JSONException
	 */
	public <T> T fromJson(Reader reader, TypeReference<T> type) throws JSONException {
		try {
			return mapper.readValue(reader, type);
		} catch (Exception t) {
			throw new JSONException("从reader中读取json信息并转换为指定对象出错（" + t.getMessage() + "）", t);
		}
	}
	
	/**
	 * 
	 * 从reader中读取json信息并转换为指定对象
	 * @param reader
	 * @param clazz
	 * @return
	 * @throws JSONException
	 */
	public <T> T fromJson(Reader reader, Class<T> clazz) throws JSONException {
		try {
			return mapper.readValue(reader, clazz);
		} catch (Exception t) {
			throw new JSONException("从reader中读取json信息并转换为指定对象出错（" + t.getMessage() + "）", t);
		}
	}
	
	/**
	 * 
	 * 把obj转换为json字符串
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
	public String toJson(Object obj) throws JSONException {
		if (obj == null) {
            return "{}";
        } else {
        	try {
        		return mapper.writeValueAsString(obj);
        	} catch (Exception t) {
                throw new JSONException("把obj转换为json字符串出错（" + t.getMessage() + "）", t);
            }
        }
	}
	
	/**
	 * 
	 * 把obj转换为json字节数组
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
	public byte[] toJsonBytes(Object obj) throws JSONException {
		if (obj == null) {
            return "{}".getBytes(Charset.forName("UTF-8"));
        } else {
        	try {
        		return mapper.writeValueAsBytes(obj);
        	} catch (Exception t) {
                throw new JSONException("把obj转换为json字符串出错（" + t.getMessage() + "）", t);
            }
        }
	}
	
	/**
     * 
     * 输出JSONP格式数据
     * @param functionName
     * @param object
     * @return
     */
	public String toJsonP(String functionName, Object object) throws JSONException {
		return toJson(new JSONPObject(functionName, object));
	}
	
	/**
	 * 
	 * 当JSON里只含有Bean的部分属性时，更新一个已存在Bean，只覆盖该部分的属性.
	 * @param jsonString
	 * @param object
	 */
	public void update(String jsonString, Object object) throws JSONException {
		try {
			mapper.readerForUpdating(object).readValue(jsonString);
		} catch (JsonProcessingException e) {
			throw new JSONException("更新json字符串到对象出错（" + e.getMessage() + "）", e);
		} catch (IOException e) {
			throw new JSONException("更新json字符串到对象出错（" + e.getMessage() + "）", e);
		}
	}
	
	/**
	 * 
	 * 获取ObjectMapper，便于扩展json处理方式
	 * @return
	 */
	public ObjectMapper getMapper() {
		return mapper;
	}
}
