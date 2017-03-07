package cn.edu.haut.cssp.device.entity;

import java.io.Serializable;

/**
 * 设备查询bean
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午5:12:37
 * @note
 */
public class DeviceQueryBean implements Serializable {

	private static final long serialVersionUID = 6662682958356928803L;

	/**
	 * 搜索类型 1-外壳序列号
	 */
	public static final int SEARCH_TYPE_SERIAL_CODE = 1;
	
	/**
	 * 搜索类型 2-IMEI
	 */
	public static final int SEARCH_TYPE_IMEI = 3;
	
	/**
	 * 搜索类型 3-硬件编号
	 */
	public static final int SEARCH_TYPE_CARD_NO = 2;
	
	//搜索类型
	private int searchType;
	
	//搜索值
	private String searchValue;

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}


}
