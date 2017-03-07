package cn.edu.haut.cssp.acms.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.h2.engine.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import cn.edu.haut.cssp.acms.bean.Operator;
import cn.edu.haut.cssp.acms.common.BaseAction;
import cn.edu.haut.cssp.acms.util.OperatorUtil;
import cn.edu.haut.cssp.acms.util.WebConstants;
import cn.edu.haut.cssp.entity.AcmsUser;
import cn.edu.haut.cssp.entity.SystemLog;
import cn.edu.haut.cssp.service.IFunctionService;
import cn.edu.haut.cssp.service.IUserService;

/**
 * 登录退出管理Action
 * Description:
 * @author: xulihua
 * @date: 2017年1月5日上午11:08:34
 */
@Controller("/acms")
public class LoginAction extends BaseAction{
	
	private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);
	
	
	// 注入service
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IFunctionService functionService;
	
	@RequestMapping(value = "/login.do",method = RequestMethod.GET)
	public String login(ModelMap model,HttpServletRequest request){
		StringBuilder url = new StringBuilder("https://");
		url.append(request.getServerName()).append(":").append(WebConstants.HTTPS_PORT)
			.append(request.getRequestURI());
		model.put("url", url.toString());
		return "/login";
	}
	
	/**
	 * 登录
	 * @author: xulihua
	 * @date: 2017年1月18日下午1:31:19
	 * @return: String
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(String userName,String password,String identifyCode ,ModelMap model,HttpServletRequest request
		,HttpServletResponse response){
		if(StringUtils.isBlank(userName)){
			model.put("message", "用户名不能为空!");
		}else if(StringUtils.isBlank(password)){
			model.put("message", "密码不能为空！");
		}else if(StringUtils.isBlank(identifyCode)){
			model.put("message", "验证码不能为空！");
		}else{
			boolean isLogined = true;
			try{
				// shiro 权限管理
				UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
				SecurityUtils.getSubject().login(token);
			}catch(LockedAccountException e){
				isLogined = false;
				model.put("message", "用户已经被锁定");
			}catch (UnknownAccountException e) {
				isLogined = false;
				model.put("message", "用户名或密码错误");
			}catch (IncorrectCredentialsException e) {
				isLogined = false;
				model.put("message", "用户名或密码错误");
			}catch (AuthenticationException e) {
				isLogined = false;
				model.put("message", e.getMessage());
			}
			if(isLogined){
				AcmsUser user = userService.getUserByUserName(userName);
				OperatorUtil.setOperator(new Operator(userName, functionService.queryAllFunctions(user)));
				OperatorUtil.getOperator().setCurrUser(user);
				logger.info("管理员{}登录成功",user.getUserName());
				return "redirect:/index.do";
			}
		}
		model.put("userName", userName);
		model.put("error", true);
		return "/login";
	}
	
	/**
	 * 退出登录
	 * @author: xulihua
	 * @date: 2017年1月18日下午4:31:01
	 * @return: String
	 */
	@RequestMapping("/logout.do")
	public String logout(HttpServletResponse response){
		AcmsUser user = OperatorUtil.getOperator().getCurrUser();
		logger.info("管理员：{}退出成功", user.getUserName());
		SecurityUtils.getSubject().logout();
		return "redirect:/login.do";
	}
	
}
