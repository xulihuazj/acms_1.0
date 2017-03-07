package cn.edu.haut.cssp.acms.action;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.haut.cssp.acms.common.BaseAction;
import cn.edu.haut.cssp.acms.kit.json.DataTablesParameters;
import cn.edu.haut.cssp.acms.util.OperatorUtil;
import cn.edu.haut.cssp.acms.util.WebConstants;
import cn.edu.haut.cssp.entity.AcmsRole;
import cn.edu.haut.cssp.entity.AcmsUser;
import cn.edu.haut.cssp.entity.SystemLog;
import cn.edu.haut.cssp.service.IRoleService;
import cn.edu.haut.cssp.util.LitePaging;

/**
 * 角色管理action类
 * Description:
 * @author: xulihua
 * @date: 2017年1月19日下午4:18:09
 * @note
 */
@Controller
@RequestMapping("/acms")
public class RoleManagerAction extends BaseAction {
	
	@Autowired
	private IRoleService roleService;
	
	/**
	 * 
	 * @Description：角色管理列表
	 * @return
	 */
	@RequestMapping("/system/role/index.do")
	public String index() {
		return "system/role/index";
	}

	/**
	 * 
	 * @Description：分页查询用户信息
	 * @param roleCondition
	 * @return
	 */
	@RequestMapping("/system/role/ajaxList.do")
	@ResponseBody
	public Object ajaxList(AcmsRole roleCondition) {
		DataTablesParameters tables = DataTablesParameters.newInstance();
		String[] orderColumns = {"name", "note"};
		
		LitePaging<AcmsRole> pagination = roleService.queryAllRoles(roleCondition, tables.getPage(), tables.getLength(), 
			tables.getOrderColName(orderColumns), tables.getOrderDir());
		return tables.getDataTablesReply(pagination);
	}
	
	/**
	 * 
	 * @Description：跳转至角色添加页面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/system/role/addRole.do")
	public String addRole(ModelMap modelMap) {
		modelMap.addAttribute("roleFuncTree", toJsonStr(roleService.queryAllFunctions(null)));
		return "system/role/add";
	}
	
	/**
	 * 
	 * @Description：保存角色
	 * @param role
	 * @param funcIds
	 * @param response
	 */
	@RequestMapping("/system/role/saveRole.do")
	public void saveRole(AcmsRole role, String funcIds, HttpServletResponse response) {
		String message = SUCCESS;
		AcmsUser user = OperatorUtil.getOperator().getCurrUser();
		try {
			roleService.saveRole(role, funcIds);
			
			//根据操作类型保存日志信息
			if (null == role.getId()) {
				logger.info(SystemLog.ENUM_LOG_TYPE.operateLog.value, LoggerExtData.create("modelType", 
					SystemLog.ENUM_LOG_MODEL_TYPE.systemManagerLog.value), "管理员{}添加角色{}成功",
					user.geAcmsUserName(), role.getName());
			} else {
				logger.info(SystemLog.ENUM_LOG_TYPE.operateLog.value, LoggerExtData.create("modelType", 
					SystemLog.ENUM_LOG_MODEL_TYPE.systemManagerLog.value), "管理员{}修改角色{}成功",
					user.geAcmsUserName(), role.getName());
			}
		} catch (IllegalArgumentException e) {
			message = e.getMessage();
			logger.error("管理员{}保存角色{}时参数异常", user.geAcmsUserName(), role.getName());
		} catch (Exception e) {
			message = "保存角色失败";
			logger.error("管理员{}保存角色{}时出现异常", user.geAcmsUserName(), role.getName());
		}
		renderText(response, message);
	}
	
	/**
	 * 
	 * @Description：跳转至角色编辑页面
	 * @author 马德成
	 * @date 2015-10-21
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/system/role/ediAcmsRole.do")
	public String ediAcmsRole(String roleId, ModelMap modelMap) {
		AcmsRole role = roleService.geAcmsRoleById(roleId);
		modelMap.addAttribute("roleFuncTree", toJsonStr(roleService.queryAllFunctions(roleId)));
		modelMap.addAttribute("role", role);
		return "system/role/edit";
	}
	
	/**
	 * 
	 * @Description：删除角色
	 * @author 马德成
	 * @date 2015-10-21
	 * @param roleId
	 * @param response
	 */
	@RequestMapping("/system/role/delRole.do")
	public void delRole(String roleId, String roleName, HttpServletResponse response) {
		String message = SUCCESS;
		AcmsUser user = OperatorUtil.getOperator().getCurrUser();
		try {
			Boolean isSuccDel = roleService.deleteRoleById(roleId);
			if (!isSuccDel) {
				message = "当前角色被用户绑定，不允许删除！";

				//保存日志信息
				logger.info(SystemLog.ENUM_LOG_TYPE.operateLog.value, LoggerExtData.create("modelType", 
					SystemLog.ENUM_LOG_MODEL_TYPE.systemManagerLog.value), "管理员{}删除{}角色失败，当前角色已被用户绑定", 
					user.geAcmsUserName(), roleName);
			} else {
				//保存日志信息
				logger.info(SystemLog.ENUM_LOG_TYPE.operateLog.value, LoggerExtData.create("modelType", 
					SystemLog.ENUM_LOG_MODEL_TYPE.systemManagerLog.value), "管理员{}删除{}角色成功", 
					user.geAcmsUserName(), roleName);
			}
		} catch (IllegalArgumentException e) {
			message = e.getMessage();
			logger.error("管理员{}删除{}角色时参数异常", user.geAcmsUserName(), roleName);
		} catch (Exception e) {
			message = "删除角色失败";
			logger.error("管理员{}删除{}角色时异常", user.geAcmsUserName(), roleName);
		}
		renderText(response, message);
	}
	
	/**
	 * 校验角色名是否重复
	 * @Description：
	 * @author 马德成
	 * @date 2015-10-21
	 * @param roleId
	 * @param roleName
	 * @return
	 */
	@RequestMapping("/system/role/checkRoleName.do")
	@ResponseBody
	public Object checkRoleName(String roleId, String name) {
		try {
			return roleService.isRoleNameExist(roleId, name) ? EncoderUtil.encode("角色名重复") : SUCCESS;
		} catch (Exception e) {
			logger.error("服务异常", e);
			return EncoderUtil.encode(WebConstants.SERVICE_BREAKE);
		}
	}
}
