package cn.edu.haut.cssp.acms.kit.json;

/**
 * Description:
 * @author: xulihua
 * @date: 2017年1月19日下午2:38:21
 * @note
 */
public class JSONException extends Exception {
	private static final long serialVersionUID = -2240310015211812084L;
	public static final int PARSE_ERROR = -32700;

	public JSONException(String message) {
		super(message);
	}

	public JSONException(String message, Throwable cause) {
		super(message, cause);
	}
}