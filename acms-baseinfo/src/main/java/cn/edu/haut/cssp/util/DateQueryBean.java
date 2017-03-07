package cn.edu.haut.cssp.util;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 日志数据包装
 * @ProjectName：oms-common
 * @ClassName：DateQueryBean
 * @Description：查询bean
 * @author 马德成
 * @date 2015-10-21
 * 
 */
public class DateQueryBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 开始时间字符串
	 */
	private String startTime;
	/**
	 * 结束时间字符串
	 */
	private String endTime;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * 
	 * @Description：获取开始时间
	 * @author 马德成
	 * @date 2015-10-21
	 * @return 开始时间
	 */
	public Long getStart() {
		if (StringUtils.isNotBlank(startTime)) {
			return DateTimeUtil.getStartTime(startTime);
		}
		return null;
	}

	/**
	 * 
	 * @Description：获取结束时间
	 * @author 马德成
	 * @date 2015-10-21
	 * @return 结束时间
	 */
	public Long getEnd() {
		if (StringUtils.isNotBlank(endTime)) {
			return DateTimeUtil.getEndTime(endTime);
		}
		return null;
	}
}
