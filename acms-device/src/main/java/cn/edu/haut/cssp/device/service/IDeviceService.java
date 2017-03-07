package cn.edu.haut.cssp.device.service;

import cn.edu.haut.cssp.device.entity.DeviceInfoBean;
import cn.edu.haut.cssp.device.entity.DeviceKeyInfo;
import cn.edu.haut.cssp.device.entity.DeviceQueryBean;

/**
 * 设备管理服务接口
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午5:05:31
 * @note
 */
public interface IDeviceService {

	/**
	 * 已执行
	 */
	public static final Integer IS_EXECUTE = 1;
	
	/**
	 * 未执行
	 */
	public static final Integer IS_NOT_EXECUTE = 0;
	
	/**
	 * 添加-0
	 */
	public static final Integer OPT_ADD = 0;
	
	/**
	 * 删除 -1
	 */
	public static final Integer OPT_DEL = 1;
	
	/**
	 * 
	 * 方法描述：查询设备基本信息
	 * @param queryBean 查询条件
	 * @return	设备基本信息
	 */
	DeviceInfoBean queryDevice(DeviceQueryBean queryBean);
	
	/**
	 * 
	 * 方法描述：查询指定SN的证书信息
	 * @author: 谢文超
	 * @date: 2015-10-22 下午3:33:26
	 * @param sn 证书SN 16进制字符串
	 * @return  base64编码证书字符串
	 */
	String queryCertBySn(String sn);
	
	/**
	 * 方法描述：查询设备外壳序列号
	 * @author: 谢文超
	 * @date: 2015-10-28 上午10:34:40
	 * @param cardNo
	 * @return
	 */
	String queryDeviceSnByCardNo(String cardNo);

	/**
	 * 
	 * 方法描述：更新解绑设备时密钥信息同步失败记录状态
	 * @author: 谢文超
	 * @date: 2015-10-30 上午11:23:15
	 * @param cardno	
	 * @param delStatus
	 * @return
	 */
	boolean updateDeviceKeyDelStatus(String cardno, Integer delStatus);

	/**
	 * 
	 * 方法描述：更新绑定设备时密钥信息同步失败记录状态
	 * @author: 谢文超
	 * @date: 2015-10-30 上午11:23:15
	 * @param cardno	
	 * @param delStatus
	 * @return
	 */
	boolean updateDeviceKeyAddStatus(String cardno, Integer addStatus);

	/**
	 * 
	 * 方法描述：保存绑定设备时密钥信息上传失败记录
	 * @author: 谢文超
	 * @date: 2015-10-30 上午11:23:15
	 * @param cardno	
	 * @param delStatus
	 * @return
	 */
	void saveBindDeviceKuep(DeviceKeyInfo deviceKeyInfo);
	
}
