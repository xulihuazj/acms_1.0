package cn.edu.haut.cssp.acms.common;

import java.io.FileNotFoundException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import com.xdja.platform.log.Logger;
import com.xdja.platform.log.LoggerFactory;

/**
 * 防御XSS攻击，字符串转义工具
 *
 */
public class XSSEscapeUtils {
	
	protected static final Logger logger = LoggerFactory.getLogger(XSSEscapeUtils.class);

	/**
	 * 根据antisamy-xdja.xml中的策略进行转义
	 * @param source 输入数据
	 * @return 清理后的数据
	 */
	public static String cleanWithPolicy(String source){
		String result = "";
		Policy policy;
		String policyFilePath = XSSEscapeUtils.class.getResource("/").getFile() + "antisamy-xdja.xml";
		
		try {
			policy = Policy.getInstance(policyFilePath);
			AntiSamy as = new AntiSamy();
			CleanResults cr = as.scan(source, policy);
			result = cr.getCleanHTML();

		} catch (PolicyException e) {
			if(e.getCause().getClass().equals(FileNotFoundException.class)){
				logger.error("找不到XSS策略文件：" + policyFilePath);
			} else {
				logger.error("加载xss策略文件异常，异常信息为：" + e.getMessage());
			}
			
		} catch (ScanException e) {
			logger.error("根据策略清理输入数据异常，异常信息为：" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 简单的html转义使用org.apache.commons.lang3.StringEscapeUtils
	 * @param source 输入数据
	 * @return 清理后的数据
	 */
	public static String simpleClean(String source){
		return StringEscapeUtils.escapeHtml4(source);
	}
	
}
