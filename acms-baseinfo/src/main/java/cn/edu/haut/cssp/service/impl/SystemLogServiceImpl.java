package cn.edu.haut.cssp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.edu.haut.cssp.dao.SystemLogDao;
import cn.edu.haut.cssp.entity.SystemLog;
import cn.edu.haut.cssp.service.ISystemLogService;
import cn.edu.haut.cssp.util.DateQueryBean;
import cn.edu.haut.cssp.util.LitePaging;
import cn.edu.haut.cssp.util.PagingConverter;

/**
 *  系统日志service实现类
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午4:17:06
 * @note
 */
@Component
public class SystemLogServiceImpl implements ISystemLogService {
	
	private SystemLogDao systemLogDao;
	
	@Override
	public LitePaging<SystemLog> querySystemLogs(SystemLog condition,Integer pageNo, Integer pageSize, String sortName, String sortType,DateQueryBean queryBean) {
		return PagingConverter.convert(systemLogDao.querySystemLogs(condition, pageNo, pageSize, sortName, sortType, queryBean));
	}

	@Override
	public void saveSystemLog(SystemLog log) {
		if (null != log) {
			//systemLogBusiness.saveSystemLog(log);
		} else {
			throw new IllegalArgumentException("保存日志，数据为空");
		}
		
	}

	@Override
	public void deleteSystemLog(Long systemLong) {
	   if(null != systemLong){
		   systemLogDao.deleteSystemLog(systemLong);
	   }else{
		   throw new RuntimeException("删除日志失败，请稍后重试");
	   }
	}
	
}
