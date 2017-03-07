package cn.edu.haut.cssp.acms.kit.json;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.util.JSONPObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

public class JsonMapper {
	private static final JsonMapper alwaysMapper = new JsonMapper();

	private static final JsonMapper nonEmptyMapper = new JsonMapper(JsonInclude.Include.NON_EMPTY);

	private static final JsonMapper nonNullMapper = new JsonMapper(JsonInclude.Include.NON_NULL);

	private static final JsonMapper nonDefaultMapper = new JsonMapper(JsonInclude.Include.NON_DEFAULT);
	private ObjectMapper mapper;

	public static JsonMapper alwaysMapper() {
		return alwaysMapper;
	}

	public static JsonMapper nonEmptyMapper() {
		return nonEmptyMapper;
	}

	public static JsonMapper nonNullMapper() {
		return nonNullMapper;
	}

	public static JsonMapper nonDefaultMapper() {
		return nonDefaultMapper;
	}

	private JsonMapper() {
		this(null);
	}

	private JsonMapper(JsonInclude.Include include) {
		this.mapper = new ObjectMapper();

		if (include != null) {
			this.mapper.setSerializationInclusion(include);
		}

		this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		this.mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		this.mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		this.mapper.getFactory().enable(JsonParser.Feature.ALLOW_COMMENTS);
		this.mapper.getFactory().enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
	}

	public <T> T fromJson(InputStream jsonInputStream, TypeReference<T> type) throws JSONException {
		try {
			return this.mapper.readValue(jsonInputStream, type);
		} catch (Exception t) {
			throw new JSONException("把json输入流中的内容转换为指定类型的对象（" + t.getMessage() + "）", t);
		}
	}

	public <T> T fromJson(InputStream jsonInputStream, Class<T> clazz) throws JSONException {
		try {
			return this.mapper.readValue(jsonInputStream, clazz);
		} catch (Exception t) {
			throw new JSONException("把json输入流中的内容转换为指定类型的对象（" + t.getMessage() + "）", t);
		}
	}

	public <T> T fromJson(byte[] jsonByteArray, TypeReference<T> type) throws JSONException {
		try {
			return this.mapper.readValue(jsonByteArray, type);
		} catch (Exception t) {
			throw new JSONException("把json字节数组转换为指定类型的对象是出错（" + t.getMessage() + "）", t);
		}
	}

	public <T> T fromJson(byte[] jsonByteArray, Class<T> clazz) throws JSONException {
		try {
			return this.mapper.readValue(jsonByteArray, clazz);
		} catch (Exception t) {
			throw new JSONException("把json字节数组转换为指定类型的对象是出错（" + t.getMessage() + "）", t);
		}
	}

	public <T> T fromJson(String jsonString, TypeReference<T> type) throws JSONException {
		try {
			return this.mapper.readValue(jsonString, type);
		} catch (Exception t) {
			throw new JSONException("把json字符串转换为指定类型的对象出错（" + t.getMessage() + "）", t);
		}
	}

	public <T> T fromJson(String jsonString, Class<T> clazz) throws JSONException {
		try {
			return this.mapper.readValue(jsonString, clazz);
		} catch (Exception t) {
			throw new JSONException("把json字符串转换为指定类型的对象出错（" + t.getMessage() + "）", t);
		}
	}

	public <T> T fromJson(Object obj, TypeReference<T> type) throws JSONException {
		if (obj instanceof String)
			return fromJson(obj.toString(), type);
		try {
			return this.mapper.convertValue(obj, type);
		} catch (Exception t) {
			throw new JSONException("把obj转换为指定类型的对象出错（" + t.getMessage() + "）", t);
		}
	}

	public <T> T fromJson(Object obj, Class<T> clazz) throws JSONException {
		if (obj instanceof String)
			return fromJson(obj.toString(), clazz);
		try {
			return this.mapper.convertValue(obj, clazz);
		} catch (Exception t) {
			throw new JSONException("把obj转换为指定对象出错（" + t.getMessage() + ")", t);
		}
	}

	public <T> T fromJson(Reader reader, TypeReference<T> type) throws JSONException {
		try {
			return this.mapper.readValue(reader, type);
		} catch (Exception t) {
			throw new JSONException("从reader中读取json信息并转换为指定对象出错（" + t.getMessage() + "）", t);
		}
	}

	public <T> T fromJson(Reader reader, Class<T> clazz) throws JSONException {
		try {
			return this.mapper.readValue(reader, clazz);
		} catch (Exception t) {
			throw new JSONException("从reader中读取json信息并转换为指定对象出错（" + t.getMessage() + "）", t);
		}
	}

	public String toJson(Object obj) throws JSONException {
		if (obj == null)
			return "{}";
		try {
			return this.mapper.writeValueAsString(obj);
		} catch (Exception t) {
			throw new JSONException("把obj转换为json字符串出错（" + t.getMessage() + "）", t);
		}
	}

	public byte[] toJsonBytes(Object obj) throws JSONException {
		if (obj == null)
			return "{}".getBytes(Charset.forName("UTF-8"));
		try {
			return this.mapper.writeValueAsBytes(obj);
		} catch (Exception t) {
			throw new JSONException("把obj转换为json字符串出错（" + t.getMessage() + "）", t);
		}
	}

	public String toJsonP(String functionName, Object object) throws JSONException {
		return toJson(new JSONPObject(functionName, object));
	}

	public void update(String jsonString, Object object) throws JSONException {
		try {
			this.mapper.readerForUpdating(object).readValue(jsonString);
		} catch (JsonProcessingException e) {
			throw new JSONException("更新json字符串到对象出错（" + e.getMessage() + "）", e);
		} catch (IOException e) {
			throw new JSONException("更新json字符串到对象出错（" + e.getMessage() + "）", e);
		}
	}

	public ObjectMapper getMapper() {
		return this.mapper;
	}
}