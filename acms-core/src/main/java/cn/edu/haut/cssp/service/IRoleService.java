package cn.edu.haut.cssp.service;
import java.util.List;
import java.util.Map;

import com.xdja.platform.rpc.RemoteService;
import com.xdja.platform.rpc.proxy.page.LitePaging;

import cn.edu.haut.cssp.entity.AcmsRole;

/**
 * 角色管理服务接口类
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午3:39:34
 * @note
 */
public interface IRoleService {

	/**
	 * 
	 * @Description：保存、修改角色信息
	 * @author: shaoyuehua
	 * @date: 2015-1-27 上午11:51:54
	 * @param role 角色实体
	 * @param funcIds 功能菜单ID集合，以逗号隔开，如1,2,3
	 */				
	void saveRole(AcmsRole role, String funcIds);
	
	/**
	 * 根据角色id删除角色及角色-用户关联、角色-功能关联信息
	 * @Description：
	 * @author: shaoyuehua
	 * @date: 2015-2-4 上午10:46:07
	 * @param roleId
	 * @return 返回true,删除成功；返回false,已经有用户绑定此角色，删除失败
	 */
	Boolean deleteRoleById(String roleId);
	
	/**
	 * 
	 * @Description：分页查询角色列表
	 * @author: shaoyuehua
	 * @date: 2015-1-27 下午4:15:08
	 * @param roleCondition
	 * @param pageNo   页码
	 * @param pageSize 每页显示条数
	 * @param sortName 排序名
	 * @param sortType 排序类型
	 * @return
	 */
	LitePaging<AcmsRole> queryAllRoles(AcmsRole roleCondition, Integer pageNo, Integer pageSize, 
		String sortName, String sortType);

	/**
	 * 
	 * @Description：查询所有的普通功能列表
	 * @author: shaoyuehua
	 * @date: 2015-1-28 上午8:48:01
	 * @return
	 */
	List<Map<String,Object>> queryAllFunctions(String roleId);
	
	/**
	 * 
	 * @Description：根据角色id查询角色
	 * @author: shaoyuehua
	 * @date: 2015-1-28 下午2:16:05
	 * @param roleId
	 * @return
	 */
	AcmsRole getRoleById(String roleId);

	/**
	 * 
	 * @Description：查询角色列表
	 * @author: 张亚如
	 * @date: 2015-1-29 上午11:28:33
	 * @return
	 */
	List<AcmsRole> queryListRoles();
	
	/**
	 * 
	 * @Description：查找所有的角色（根据userId判断是否拥有相应角色）
	 * @author: 张亚如
	 * @date: 2015-1-29 下午2:38:53
	 * @param userId
	 * @return
	 */
	List<AcmsRole> queryListRolesOrCheck(Long userId);

	/**
	 * 校验角色名是否重复；如果是修改角色，需要排除本条角色信息进行校验
	 * @Description：
	 * @author: shaoyuehua
	 * @date: 2015-2-5 上午9:16:26
	 * @param roleId
	 * @param roleName
	 * @return true：与已有角色名重复; false:不重复
	 */
	Boolean isRoleNameExist(String roleId, String roleName);
}
