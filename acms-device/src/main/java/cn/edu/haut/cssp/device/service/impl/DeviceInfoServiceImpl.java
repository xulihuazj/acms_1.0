package cn.edu.haut.cssp.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.haut.cssp.device.entity.DeviceInfoBean;
import cn.edu.haut.cssp.device.entity.DeviceKeyInfo;
import cn.edu.haut.cssp.device.entity.DeviceQueryBean;
import cn.edu.haut.cssp.device.service.IDeviceService;

/**
 * 设备信息管理业务接口实现
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午5:23:27
 * @note
 */
@Component
public class DeviceInfoServiceImpl implements IDeviceService {

	@Autowired
	private DeviceBusiness deviceBusiness;
	
	
	
	@Override
	public DeviceInfoBean queryDevice(DeviceQueryBean queryBean) {
		return deviceBusiness.queryDevice(queryBean);
	}

	@Override
	public String queryCertBySn(String sn) {
		return deviceBusiness.queryCertBySn(sn);
	}

	@Override
	public String queryDeviceSnByCardNo(String cardNo) {
		return deviceBusiness.queryDeviceSnByCardNo(cardNo);
	}
	
	@Override
	public void saveBindDeviceKuep(DeviceKeyInfo deviceKeyInfo) {
		DeviceKeyInfo keyInfo = this.deviceBusiness.getDeviceKeyByCardno(deviceKeyInfo.getCardno(), deviceKeyInfo.getOpt());
		
		if (keyInfo != null) {
			this.deviceBusiness.updateDeviceKey(deviceKeyInfo);
		} else {
			this.deviceBusiness.saveDeviceKey(deviceKeyInfo);
		}
	
	}

	@Override
	public boolean updateDeviceKeyAddStatus(String cardno, Integer addStatus) {
		DeviceKeyInfo keyInfo = this.deviceBusiness.getDeviceKeyByCardno(cardno, OPT_ADD);
		
		if (keyInfo != null) {
			keyInfo.setStatus(addStatus);
			this.deviceBusiness.updateDeviceKey(keyInfo);
		}
		return true;
	}

	@Override
	public boolean updateDeviceKeyDelStatus(String cardno, Integer delStatus) {
		DeviceKeyInfo keyInfo = this.deviceBusiness.getDeviceKeyByCardno(cardno, OPT_DEL);
		
		if (keyInfo != null) {
			keyInfo.setStatus(delStatus);
			this.deviceBusiness.updateDeviceKey(keyInfo);
		}
		return true;
	}

	@Override
	public String usn2UID(String usn) {
		return UnlockCodeWrap.usn2UID(usn);
	}

	@Override
	public String getEnSCode(String cardId, String lockCode) {
		return UnlockCodeWrap.getEnSCode(cardId, lockCode);
	}

	@Override
	public String genDCode(String usn, String lockCode) {
		return UnlockCodeWrap.genDCode(usn, lockCode);
	}

	@Override
	public Long queryCustomerId(String uid) {
		return deviceBusiness.queryCustomerId(uid);
	}

}
