package cn.edu.haut.cssp.acms.kit.prop;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 配置文件操作类
 * Description:
 * @author: xulihua
 * @date: 2017年1月16日下午3:19:05
 */
public class Prop {
	private static final Logger logger = LoggerFactory.getLogger(Prop.class);
	private Properties properties;

	public Prop(String fileName) {
		this(fileName, PropKit.DEFAULT_ENCODING);
	}

	public Prop(String fileName, String encoding) {
		this.properties = null;

		InputStream inputStream = null;
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			if (inputStream == null)
				throw new IllegalArgumentException("Properties file not found in classpath: " + fileName);
			this.properties = new Properties();
			this.properties.load(new InputStreamReader(inputStream, encoding));
		} catch (IOException e) {
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
		}
	}

	public Prop(File file) {
		this(file, PropKit.DEFAULT_ENCODING);
	}

	public Prop(File file, String encoding) {
		this.properties = null;

		if (file == null)
			throw new IllegalArgumentException("File can not be null.");
		if (!(file.isFile())) {
			throw new IllegalArgumentException("Not a file : " + file.getName());
		}
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			this.properties = new Properties();
			this.properties.load(new InputStreamReader(inputStream, encoding));
		} catch (IOException e) {
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
		}
	}

	public String get(String key) {
		return this.properties.getProperty(key);
	}

	public String get(String key, String defaultValue) {
		String value = get(key);
		return ((value != null) ? value : defaultValue);
	}

	public Integer getInt(String key) {
		String value = get(key);
		return ((value != null) ? Integer.valueOf(Integer.parseInt(value)) : null);
	}

	public Integer getInt(String key, Integer defaultValue) {
		String value = get(key);
		return Integer.valueOf((value != null) ? Integer.parseInt(value) : defaultValue.intValue());
	}

	public Long getLong(String key) {
		String value = get(key);
		return ((value != null) ? Long.valueOf(Long.parseLong(value)) : null);
	}

	public Long getLong(String key, Long defaultValue) {
		String value = get(key);
		return Long.valueOf((value != null) ? Long.parseLong(value) : defaultValue.longValue());
	}

	public Boolean getBoolean(String key) {
		String value = get(key);
		return ((value != null) ? Boolean.valueOf(Boolean.parseBoolean(value)) : null);
	}

	public Boolean getBoolean(String key, Boolean defaultValue) {
		String value = get(key);
		return Boolean.valueOf((value != null) ? Boolean.parseBoolean(value) : defaultValue.booleanValue());
	}

	public boolean containsKey(String key) {
		return this.properties.containsKey(key);
	}

	public Properties getProperties() {
		return this.properties;
	}
}