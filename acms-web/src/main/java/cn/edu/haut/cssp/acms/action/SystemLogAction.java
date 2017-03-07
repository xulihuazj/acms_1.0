package cn.edu.haut.cssp.acms.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.haut.cssp.acms.common.BaseAction;

/**
 * 系统日志处理Action
 * Description:
 * @author: xulihua
 * @date: 2017年1月5日下午1:41:10
 */
@Controller
@RequestMapping("/acms")
public class SystemLogAction extends BaseAction{

	/**
	 * 跳转到日志页面
	 * @author: xulihua
	 * @date: 2017年1月9日下午2:46:21
	 * @return: String
	 */
	@RequestMapping("/system/syslog/toList.do")
	public String toList(){
		return "system/syslog";
	}
	
	/**
	 * 系统日志列表
	 * @author: xulihua
	 * @date: 2017年1月5日下午2:07:40
	 * @return: Object
	 */
	public Object ajaxList(){
		
		return "";
	}
	
}
