package cn.edu.haut.cssp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 专业实体类
 * Description:
 * @author: xulihua
 * @date: 2017年1月5日下午2:26:59
 */
@Entity
@Table(name = "acms_sch_major")
public class AcmsSchMajor implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long majorId;
	
	private String majorName;

	/**
	 * @return the majorId
	 */
	public Long getMajorId() {
		return majorId;
	}

	/**
	 * @param majorId the majorId to set
	 */
	public void setMajorId(Long majorId) {
		this.majorId = majorId;
	}

	/**
	 * @return the majorName
	 */
	public String getMajorName() {
		return majorName;
	}

	/**
	 * @param majorName the majorName to set
	 */
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	
}
