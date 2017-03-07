package cn.edu.haut.cssp.acms.action;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.haut.cssp.acms.common.BaseAction;

/**
 * 用户管理Action类
 * Description:
 * @author: xulihua
 * @date: 2017年1月5日下午12:04:01
 */
@Controller
@RequestMapping("/acms")
public class UserManagerAction extends BaseAction{
	
	/**
	 *  修改登录密码页面
	 * @author: xulihua
	 * @date: 2017年1月5日下午12:37:19
	 * @return: String
	 */
	@RequestMapping("/system/toModifyPsd.do")
	public String toModifyPsd(){
		return "system/modifyPsd";
	}
	
	/**
	 * 修改登录密码
	 * @author: xulihua
	 * @date: 2017年1月5日下午12:39:39
	 * @return: String
	 */
	@RequestMapping("/system/modifyPas.do")
	public String modifyPsd(String newPassword,String oldPassword,String verifyPassword){
		return "";
	}
	
	
	
}
