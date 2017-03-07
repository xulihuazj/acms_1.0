package cn.edu.haut.cssp.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.xdja.cssp.oms.system.business.IRoleBusiness;
import com.xdja.cssp.oms.system.entity.Function;
import com.xdja.cssp.oms.system.entity.TRole;
import com.xdja.cssp.oms.system.entity.TRoleFunction;
import com.xdja.cssp.oms.system.entity.TUserRole;
import com.xdja.cssp.oms.system.service.IRoleService;
import com.xdja.platform.rpc.proxy.PagingConverter;
import com.xdja.platform.rpc.proxy.page.LitePaging;

/**
 * 
 * 
 * Project Name：pms-service-oms-system
 * ClassName：RoleServiceImpl
 * Description：角色管理服务实现类
 * @author 马德成
 * @date 2015-10-21
 * note:
 *
 */
@Component
public class RoleServiceImpl implements IRoleService {

	@Resource
	private IRoleBusiness roleBusiness;

	@Override
	public void saveRole(TRole role, String funcIds) {
		if (null != role) {
			if (StringUtils.isNotBlank(funcIds)) {
				roleBusiness.saveRole(role, funcIds);
			} else {
				throw new IllegalArgumentException("为角色绑定的功能权限为空");
			}
		} else {
			throw new IllegalArgumentException("角色信息为空");
		}
	}

	@Override
	public Boolean deleteRoleById(String roleId) {
		if (StringUtils.isNotBlank(roleId)) {
			return roleBusiness.deleteRoleById(Long.valueOf(roleId));
		} else {
			throw new IllegalArgumentException("角色id为空");
		}
	}

	@Override
	public LitePaging<TRole> queryAllRoles(TRole roleCondition,
			Integer pageNo, Integer pageSize, String sortName, String sortType) {
		return PagingConverter.convert(roleBusiness.queryAllRoles(roleCondition, pageNo, pageSize, sortName, sortType));
	}

	@Override
	public List<Map<String,Object>> queryAllFunctions(String roleId) {
		List<Long> roleFuncIds = new ArrayList<Long>();
		
		//编辑时，得到与该角色已经绑定的功能id列表
		if(StringUtils.isNotBlank(roleId)){
			List<TRoleFunction> roleFunctionList = roleBusiness.queryRoleFunctionByRoleId(Long.valueOf(roleId));
			if (!roleFunctionList.isEmpty()) {
				for (TRoleFunction roleFunction : roleFunctionList) {
					roleFuncIds.add(roleFunction.getFunctionId());
				}
			}
		}
		
		//得到系统中所有的普通功能列表，组装成功能菜单树
		List<Function> listFunction = roleBusiness.queryAllFunctions();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> funcMap;
		for (Function func : listFunction) {
			funcMap = new HashMap<String, Object>();
			funcMap.put("id", func.getId());
			funcMap.put("pId", func.getParentId());
			funcMap.put("name", func.getName());
			funcMap.put("checked", roleFuncIds.contains(func.getId()));
			result.add(funcMap);
		}
		return result;
	}

	@Override
	public TRole getRoleById(String roleId) {
		if (StringUtils.isNotBlank(roleId)) {
			return roleBusiness.getRoleById(Long.valueOf(roleId));
		} else {
			throw new IllegalArgumentException("角色id为空");
		}
	}

	@Override
	public List<TRole> queryListRoles() {
		return roleBusiness.queryListRoles();
	}

	@Override
	public List<TRole> queryListRolesOrCheck(Long userId) {
		List<Long> roleIdList = new ArrayList<Long>();
		List<TRole> roleReturnList = new ArrayList<TRole>();
		
		//封装当前用户已拥有的角色id为List
		List<TUserRole> userRoleList = roleBusiness.queryUserRoleListByUserId(userId);
		if (!userRoleList.isEmpty()) {
			for(TUserRole userRole : userRoleList){
				roleIdList.add(userRole.getRoleId());
			}
		}
		
		//查询搜有的角色列表，并将用户已拥有的角色的checkFlag设置为true
		List<TRole> roleList = roleBusiness.queryListRoles();
		if (!roleList.isEmpty()) {
			for (TRole role : roleList) {
				if (roleIdList.contains(role.getId())) {
					role.setCheckFlag(true);
				}
				roleReturnList.add(role);
			}
		}
		return roleReturnList;
	}

	@Override
	public Boolean isRoleNameExist(String roleId, String roleName) {
		return roleBusiness.isRoleNameExist(roleId, roleName);
	}
}