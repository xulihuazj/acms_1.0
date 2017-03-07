package cn.edu.haut.cssp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 学院实体类
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午3:25:43
 */
@Entity
@Table(name = "acms_sch_academy")
public class AcmsSchAcademy implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long acadId;
	
	private String acadName;

	/**
	 * @return the acadId
	 */
	public Long getAcadId() {
		return acadId;
	}

	/**
	 * @param acadId the acadId to set
	 */
	public void setAcadId(Long acadId) {
		this.acadId = acadId;
	}

	/**
	 * @return the acadName
	 */
	public String getAcadName() {
		return acadName;
	}

	/**
	 * @param acadName the acadName to set
	 */
	public void setAcadName(String acadName) {
		this.acadName = acadName;
	}
	
}
