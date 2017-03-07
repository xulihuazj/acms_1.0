package cn.edu.haut.cssp.acms.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import cn.edu.haut.cssp.acms.bean.Operator;

/**
 * 登录系统的操作工具类
 * Description:
 * @author: xulihua
 * @date: 2017年1月5日下午1:31:14
 */
public class OperatorUtil {
	
	/**
	 * @Description：从session中获取当前操作员
	 * @return 当前操作员
	 */
	public static final Operator getOperator() {
		return (Operator) SecurityUtils.getSubject().getSession().getAttribute(Operator.OPERATOR);
	}
	
	/**
	 * @Description：把当前操作员放入session
	 * @param operator 当前操作员
	 */
	public static final void setOperator(Operator operator) {
		SecurityUtils.getSubject().getSession().setAttribute(Operator.OPERATOR, operator);
	}
	
	/**
	 * 把指定的操作员踢下线
	 * @param subject 操作员对应的会话
	 */
	public static final void kickout(Subject subject) {
		try {
			if (null != subject) {
				Session kickoutSession = subject.getSession();
				
				if(kickoutSession != null) {
		            //设置会话的kickout属性表示踢出了
		            kickoutSession.setAttribute("kickout", true);
		        }
			}
		} catch (Exception e) {//ignore exception
		}
	}
}
