package cn.edu.haut.cssp.service;

import cn.edu.haut.cssp.entity.AcmsUser;
/**
 * 系统初始化服务接口
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午3:47:08
 * @note
 */
public interface IInitService {
	/**
	 * 检验acms系统是否已初始化，返回true,系统已初始化；反之，系统未初始化
	 * @author: xulihua
	 * @date: 2017年1月18日下午3:47:25
	 * @return: boolean
	 */
	boolean isSysInit();
	
	/**
	 * 系统初始化，添加系统管理员
	 * @author: xulihua
	 * @date: 2017年1月18日下午3:47:40
	 * @return: void
	 */
	void saveInitUser(AcmsUser user);
}
