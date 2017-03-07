package cn.edu.haut.cssp.service.impl;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.genericdao.search.Search;
import com.xdja.cssp.oms.system.business.IRoleBusiness;
import com.xdja.cssp.oms.system.dao.FunctionDao;
import com.xdja.cssp.oms.system.dao.TRoleDao;
import com.xdja.cssp.oms.system.dao.TRoleFunctionDao;
import com.xdja.cssp.oms.system.dao.TUserRoleDao;
import com.xdja.cssp.oms.system.entity.Function;
import com.xdja.cssp.oms.system.entity.TRole;
import com.xdja.cssp.oms.system.entity.TRoleFunction;
import com.xdja.cssp.oms.system.entity.TUserRole;
import com.xdja.platform.datacenter.jpa.business.BaseBusiness;
import com.xdja.platform.datacenter.jpa.page.Pagination;

/**
 * 
 * 
 * Project Name：pms-service-oms-system
 * ClassName：RoleBusinessImpl
 * Description：角色管理业务类
 * @author 马德成
 * @date 2015-10-21
 * note:
 *
 */
@Service
public class RoleBusinessImpl extends BaseBusiness implements IRoleBusiness {

	@Autowired
	private TRoleDao roleDao;
	
	@Autowired
	private TUserRoleDao userRoleDao;
	
	@Autowired
	private TRoleFunctionDao roleFunctionDao;
	
	@Autowired
	private AFunctionDao aFunctionDao;

	@Override
	public void saveRole(TRole role, String funcIds) {
		String[] funcIdsArr = funcIds.split(",");
		TRoleFunction[] roleFunctionArray = new TRoleFunction[funcIdsArr.length];
		TRoleFunction roleFunction = null;
		
		// 角色新增时，默认将角色设置为普通角色
		if (null == role.getId()) {
			role.setType(TRole.ENUM_ROLE_TYPE.consumerRole.value);	
		}
		roleDao.save(role);
		
		// 循环装载要保存的角色-功能关系实体
		for (int index = 0; index < funcIdsArr.length; index++) {
			roleFunction = new TRoleFunction();
			roleFunction.setRoleId(role.getId());
			roleFunction.setFunctionId(Long.parseLong(funcIdsArr[index]));
			roleFunctionArray[index] = roleFunction;
		}
		roleFunctionDao.removeRoleFuncById(role.getId());
		roleFunctionDao.save(roleFunctionArray);
	}

	@Override
	public Boolean deleteRoleById(Long roleId) {
		//根据roleId得到使用该角色的用户数
		Integer userRoleCount = userRoleDao.getUserCountByRoleId(roleId);
		if (userRoleCount == 0) {
			roleDao.removeById(roleId); //删除角色信息
			roleFunctionDao.removeRoleFuncById(roleId); //删除角色-功能关系
			
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Pagination<TRole> queryAllRoles(TRole roleCondition,
			Integer pageNo, Integer pageSize, String sortName, String sortType) {
		return roleDao.queryAllRoles(roleCondition, pageNo, pageSize, sortName, sortType);
	}

	@Override
	public List<Function> queryAllFunctions() {
		return aFunctionDao.queryAllFunctions();
	}

	@Override
	public TRole getRoleById(Long roleId) {
		return roleDao.find(roleId);
	}

	@Override
	public List<TRoleFunction> queryRoleFunctionByRoleId(Long roleId) {
		return roleFunctionDao.queryRoleFunctionByRoleId(roleId);
	}

	@Override
	public List<TRole> queryListRoles() {
		return roleDao.findAll();
	}

	@Override
	public List<TUserRole> queryUserRoleListByUserId(Long userId) {
		return userRoleDao.queryUserRoleListByUserId(userId);
	}

	@Override
	public Boolean isRoleNameExist(String roleId, String roleName) {
		Search search = new Search(TRole.class);
		search.addFilterEqual("name", roleName);
		if (StringUtils.isNotBlank(roleId)) {
			search.addFilterNotEqual("id", roleId);
		}
		return roleDao.count(search) > 0;
	}
}
