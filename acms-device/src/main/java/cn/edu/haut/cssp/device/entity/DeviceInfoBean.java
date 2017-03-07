package cn.edu.haut.cssp.device.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 设备信息
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午5:06:52
 * @note
 */
@Entity
@Table(name = "")
public class DeviceInfoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String assetIdentify;
	
	//设备序列号
	private String serialCode;
	
	// 设备SN
	private String deviceSn;
    
    //设备串号
    private String imei;
    
	//设备型号	
    private String model;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssetIdentify() {
		return assetIdentify;
	}

	public void setAssetIdentify(String assetIdentify) {
		this.assetIdentify = assetIdentify;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the deviceSn
	 */
	public String getDeviceSn() {
		return deviceSn;
	}

	/**
	 * @param deviceSn the deviceSn to set
	 */
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
}
