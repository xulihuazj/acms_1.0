package cn.edu.haut.cssp.service;

import org.springframework.stereotype.Service;

import com.xdja.platform.rpc.proxy.page.LitePaging;

import cn.edu.haut.cssp.entity.AcmsUser;

/**
 * 用户管理服务接口类
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午2:43:53
 */
@Service
public interface IUserService {
	
	/**
	 * 根据用户名获取用户信息
	 * @author: xulihua
	 * @date: 2017年1月18日下午2:47:06
	 * @return: AcmsUser
	 */
	public AcmsUser getUserByUserName(String userName);
	
	/**
	 * 分页查询用户信息
	 * @author: xulihua
	 * @date: 2017年1月18日下午3:17:39
	 * @return: LitePaging<AcmsUser>
	 */
	public LitePaging<AcmsUser> queryAllUsers(AcmsUser condition,Integer pageNo,Integer pageSize
			,String sortName,String sortType);
	
	/**
	 * 保存用户信息
	 * @author: xulihua
	 * @date: 2017年1月18日下午2:50:43
	 * @return: void
	 */
	public void saveUser(AcmsUser user,Long[] roleId);
	
	/**
	 * 对密码进行修改，更新用户信息
	 * @author: xulihua
	 * @date: 2017年1月18日下午3:09:19
	 * @return: void
	 */
	public void updateUser(AcmsUser user);
	
	/**
	 * 通过用户id查询信息
	 * @author: xulihua
	 * @date: 2017年1月18日下午3:03:02
	 * @return: AcmsUser
	 */
	public AcmsUser findUserById(Long userId);
	
	/**
	 * 通过userId删除用户
	 * @author: xulihua
	 * @date: 2017年1月18日下午3:04:19
	 * @return: void
	 */
	public void deleteUser(Long userId);
	
	/**
	 * 通过userId重置密码
	 * @author: xulihua
	 * @date: 2017年1月18日下午3:05:12
	 * @return: void
	 */
	public void resetPassword(Long userId);
	
	/**
	 * 根据userId开启用户
	 * @author: xulihua
	 * @date: 2017年1月18日下午3:06:49
	 * @return: void
	 */
	public void startUser(Long userId);
	
	/**
	 * 根据userId暂停用户
	 * @author: xulihua
	 * @date: 2017年1月18日下午3:07:44
	 * @return: void
	 */
	public void suspendUser(Long userId);
	
}
