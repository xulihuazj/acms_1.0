package cn.edu.haut.cssp.acms.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.edu.haut.cssp.acms.common.BaseAction;

/**
 * 首页
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午4:43:30
 * @note
 */
public class IndexAction extends BaseAction{

	
	@RequestMapping("/index.do")
	public String index(HttpServletRequest request) {
		return "/index";
	}
	
	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String pagenotfound(Boolean sessionTimeoutFlag, ModelMap model) {
		return "/404";
	}
}
