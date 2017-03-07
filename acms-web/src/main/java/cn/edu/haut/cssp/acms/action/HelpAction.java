package cn.edu.haut.cssp.acms.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.edu.haut.cssp.acms.common.BaseAction;

/**
 * 首页
 * Description:
 * @author: xulihua
 * @date: 2017年1月5日上午11:56:43
 */
@Controller
@RequestMapping("/acms")
public class HelpAction extends BaseAction{
	
	@RequestMapping(value = "/404",method = RequestMethod.GET)
	public String PageNotFound(HttpServletRequest request){
		return "/404";
	}

	@RequestMapping(value = "/help",method = RequestMethod.GET)
	public String help(HttpServletRequest request){
		return "/help";	
	}
}
