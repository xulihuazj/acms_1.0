package cn.edu.haut.cssp.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
/**
 * 异常处理
 * Description:
 * @author: xulihua
 * @date: 2017年1月5日上午11:26:26
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -1487596660253714452L;
	private Level level;
	private String code;
	private String message;
	private String className;
	private String method;
	private Exception preException;
	private int errCode;

	public static final ServiceException create(int errCode, String message, Exception preException) {
		return create(Level.LEVEL_COMMON, errCode, message, preException);
	}

	public static final ServiceException create(Level level, int errCode, String message, Exception preException) {
		ServiceException instance = new ServiceException(level, errCode, message, preException);

		for (StackTraceElement stack : instance.getStackTrace()) {
			if (!(ServiceException.class.getName().equals(stack.getClassName()))) {
				instance.className = stack.getClassName();
				instance.method = stack.getMethodName();

				break;
			}
		}

		return instance;
	}

	private ServiceException(Level level, int errCode, String message, Exception preException) {
		super(message, preException);

		this.level = level;
		this.errCode = errCode;
		this.code = Integer.toHexString(errCode);
		this.message = message;
		this.preException = preException;
	}

	public String getStackTraceInfo() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		if (this.preException != null)
			this.preException.printStackTrace(printWriter);
		else {
			printStackTrace(printWriter);
		}
		return stringWriter.getBuffer().toString();
	}

	public Level getLevel() {
		return this.level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getClassName() {
		return this.className;
	}

	public String getMethod() {
		return this.method;
	}

	public Exception getPreException() {
		return this.preException;
	}

	public void setPreException(Exception preException) {
		this.preException = preException;
	}

	public int getErrCode() {
		return this.errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeHexString() {
		return "0x" + Integer.toHexString(this.errCode).toUpperCase();
	}

	public static enum Level

	{
    LEVEL_COMMON, LEVEL_SERIOUS;
  }
}