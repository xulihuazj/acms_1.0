package cn.edu.haut.cssp.service.impl;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.xdja.cssp.oms.system.business.IInitBusiness;
import com.xdja.cssp.oms.system.entity.TUser;
import com.xdja.cssp.oms.system.entity.TUserCert;
import com.xdja.cssp.oms.system.service.IInitService;

/**
 * 
 * 
 * Project Name：pms-service-system
 * ClassName：InitServiceImpl
 * Description：系统初始化服务实现类
 * @author: shaoyuehua
 * @date: 2015-1-19 下午3:41:40
 * note:
 *
 */
@Component
public class InitServiceImpl implements IInitService {

	@Resource
	private IInitBusiness initBusiness;

	@Override
	public boolean isSysInit() {
		return initBusiness.isSysInit();
	}

	@Override
	public void saveInitUser(TUser user, TUserCert userCert) {
		if(null != user){
			if(null != userCert){
				initBusiness.saveInitUser(user, userCert);
			}else{
				throw new IllegalArgumentException("卡绑定信息不存在");
			}
		}else{
			throw new IllegalArgumentException("用户信息不存在");
		}
	}
}