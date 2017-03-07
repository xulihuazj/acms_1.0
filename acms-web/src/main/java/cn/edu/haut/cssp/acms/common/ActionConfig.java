package cn.edu.haut.cssp.acms.common;
import java.util.Properties;
/**
 *
 * Description:
 * @author: xulihua
 * @date: 2017年1月5日上午11:21:17
 */
public class ActionConfig {
	public static final ActionConfig DEFAULT_CONFIG = new ActionConfig();
	private boolean showST;
	private Properties exceptionMappings;

	public ActionConfig() {
		this.showST = true;
	}

	public boolean isShowST() {
		return this.showST;
	}

	public void setShowST(boolean showST) {
		this.showST = showST;
	}

	public Properties getExceptionMappings() {
		return this.exceptionMappings;
	}

	public void setExceptionMappings(Properties exceptionMappings) {
		this.exceptionMappings = exceptionMappings;
	}
}