package cn.edu.haut.cssp.service;


import cn.edu.haut.cssp.entity.SystemLog;
import cn.edu.haut.cssp.util.DateQueryBean;
import cn.edu.haut.cssp.util.LitePaging;

/**
 * 日志service接口类
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午4:16:28
 * @note
 */
public interface ISystemLogService {
	
	
	LitePaging<SystemLog> querySystemLogs(SystemLog condition,Integer pageNo, Integer pageSize, String sortName, String sortType,DateQueryBean queryBean);
	
	/**
	 * 保存日志
	 * @Description：
	 * @param log
	 */
	void saveSystemLog(SystemLog log);
	
	/**
	 * 
	 * @author: xulihua
	 * @date: 2017年1月21日下午4:49:47
	 * @return: void
	 */
	void deleteSystemLog(Long systemLong);
}
