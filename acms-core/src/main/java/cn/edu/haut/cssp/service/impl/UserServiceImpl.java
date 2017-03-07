package cn.edu.haut.cssp.service.impl;

import java.security.cert.X509Certificate;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.googlecode.genericdao.search.Search;
import com.xdja.platform.rpc.proxy.page.LitePaging;

import cn.edu.haut.cssp.dao.AUserDao;
import cn.edu.haut.cssp.entity.AcmsUser;
import cn.edu.haut.cssp.service.IUserService;

@Component
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private AUserDao userDao;
	
	@Autowired
	private AUserRoleDao userRoleDao;
	
	@Override
	public AcmsUser getUserByUserName(String uName) {
		Search search = new Search(TUser.class);
		search.addFilterEqual("userName", name);
		return userDao.searchUnique(search);
	}


	@Override
	public LitePaging<AcmsUser> queryAllUsers(AcmsUser condition, Integer pageNo, Integer pageSize, String sortName,
			String sortType) {
		
		return PagingConverter.convert(userDao.queryAllUsers(condition, pageNo, pageSize, sortName, sortType));
	}

	@Override
	public void saveUser(AcmsUser user, Long[] roleId) {
		if(null == user.getId()) {
			user.setType(ENUM_USER_TYPE.consumerUser.value);
			user.setStatus(ENUM_USER_STATUS.normalStatus.value);
			user.setDeleteFlag(ENUM_DELETE_FLAG.normalFlag.value);
			user.setCreateTime(System.currentTimeMillis());
			user.setPassword(PasswordUtils.encodePasswordSHA1(AcmsUser.ADMIN_USER_DEFAULT_PASSWORD));
		}
		
		userDao.save(user);
		userRoleDao.removeUserRoleByUserId(user.getId());
		
		if(null != roleId) {
			AcmsUserRole[] userRoles = new AcmsUserRole[roleId.length];
			for(int i = 0; i < roleId.length; i++) {
				AcmsUserRole userRole = new AcmsUserRole();
				userRole.seAcmsUserId(user.getId());
				userRole.setRoleId(roleId[i]);
				userRoles[i] = userRole;
			}
			userRoleDao.save(userRoles);
		}
	}

	@Override
	public AcmsUser findUserById(Long userId) {
		if (null != userId) {
			return userDao.find(userId);
		} else {
			throw new IllegalArgumentException("用户Id为空");
		}
	}


	@Override
	public void deleteUser(Long userId) {
		AcmsUser user = userDao.find(userId);
		
		//将用户表中deleteFlag设置为2
		user.setDeleteFlag(ENUM_DELETE_FLAG.deleteFlag.value);
		user.seAcmsUserName("[" + user.geAcmsUserName() + "]");
		
		//删除用户-角色关系
		userRoleDao.removeUserRoleByUserId(userId);
		
		/*//删除用户—集团关系
		userEcDao.deleteByUserId(userId);*/
		
		//根据user_id删除用户-卡绑定信息
		userCertDao.removeUserCertByUserId(userId);
	}

	@Override
	public void resetPassword(Long userId) {
		AcmsUser user = userDao.find(userId);
		user.setPassword(PasswordUtils.encodePasswordSHA1(AcmsUser.ADMIN_USER_DEFAULT_PASSWORD));
	}

	@Override
	public void suspendUser(Long userId) {
		AcmsUser user = userDao.find(userId);
		user.setStatus(ENUM_USER_STATUS.stopStatus.value);
	}

	@Override
	public void startUser(Long userId) {
		AcmsUser user = userDao.find(userId);
		user.setStatus(ENUM_USER_STATUS.normalStatus.value);
	}

	@Override
	public boolean isNameExist(String userName, Long userId) {
		Search search = new Search(AcmsUser.class);
		search.addFilterEqual("userName", userName);
		if (null != userId) {
			search.addFilterNotEqual("id", userId);
		}
		
		return userDao.count(search) > 0;
	}
	@Override
	public void updateUser(AcmsUser user) {
		userDao.save(user);		
	}

