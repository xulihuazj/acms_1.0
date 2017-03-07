package cn.edu.haut.cssp.acms.util.json;
/**
 * json操作通用异常
 * Description:
 * @author: xulihua
 * @date: 2017年1月5日上午11:47:19
 */
public class JSONException extends Exception {

	private static final long serialVersionUID = -2240310015211812084L;
	
	public static final int PARSE_ERROR = -32700;

	/**
	 * @Description：构造函数
	 * @param message 异常消息
	 */
	public JSONException(String message) {
		super(message);
	}
	
	/**
	 * @Description：构造函数
	 * @param message 异常消息
	 * @param cause 引起该异常的异常
	 */
	public JSONException(String message, Throwable cause) {
		super(message, cause);
	}
}
