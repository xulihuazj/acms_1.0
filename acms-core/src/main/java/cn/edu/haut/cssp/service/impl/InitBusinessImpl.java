package cn.edu.haut.cssp.service.impl;
import java.security.cert.X509Certificate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdja.cssp.oms.system.business.IInitBusiness;
import com.xdja.cssp.oms.system.dao.TUserCertDao;
import com.xdja.cssp.oms.system.dao.TUserDao;
import com.xdja.cssp.oms.system.entity.TUser;
import com.xdja.cssp.oms.system.entity.TUser.ENUM_DELETE_FLAG;
import com.xdja.cssp.oms.system.entity.TUser.ENUM_USER_STATUS;
import com.xdja.cssp.oms.system.entity.TUser.ENUM_USER_TYPE;
import com.xdja.cssp.oms.system.entity.TUserCert;
import com.xdja.cssp.oms.system.util.CertUtil;
import com.xdja.platform.datacenter.jpa.business.BaseBusiness;
import com.xdja.platform.security.utils.PasswordUtils;

/**
 * 
 * 
 * Project Name：pms-service-system
 * ClassName：InitBusinessImpl
 * Description：系统初始化实现类
 * @author: shaoyuehua
 * @date: 2015-1-19 下午3:42:20
 * note:
 *
 *修改：更改实体名称
 *@author fy
 *@date 2015-04-28
 */
@Service
public class InitBusinessImpl extends BaseBusiness implements IInitBusiness {

	@Autowired
	private AUserDao userDao;

	@Autowired
	private TUserCertDao userCertDao;

	@Override
	public boolean isSysInit() {
		if(userDao.isSysInit() > 0){
			return true;
		}
		return false;
	}

	@Override
	public void saveInitUser(TUser user, TUserCert userCert) {
		//保存用户信息
		user.setType(ENUM_USER_TYPE.systemUser.value);
		user.setStatus(ENUM_USER_STATUS.normalStatus.value);
		user.setCreateTime(System.currentTimeMillis());
		user.setPassword(PasswordUtils.encodePasswordSHA1(user.getPassword()));
		user.setDeleteFlag(ENUM_DELETE_FLAG.normalFlag.value);
		userDao.save(user);
		
		//保存卡绑定信息
		X509Certificate clientCert = CertUtil.getCertFromStr16(userCert.getSn()); 
		String sn = clientCert.getSerialNumber().toString(16).toLowerCase(); 
		userCert.setSn(sn);
		userCert.setUserId(user.getId());
		userCertDao.save(userCert);	
	}
}
