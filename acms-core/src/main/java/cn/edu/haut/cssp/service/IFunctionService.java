package cn.edu.haut.cssp.service;

import java.util.Map;
import cn.edu.haut.cssp.bean.Menu;
import cn.edu.haut.cssp.entity.AcmsUser;
/**
 * 菜单权限服务接口类
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午3:47:59
 * @note
 */
public interface IFunctionService {

	/**
	 * 查询用户拥有的所有菜单信息
	 * @author: xulihua
	 * @date: 2017年1月18日下午3:48:24
	 * @return: Map<String,Menu>
	 */
	Map<String, Menu> queryAllFunctions(AcmsUser user);
}
