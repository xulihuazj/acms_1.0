package cn.edu.haut.cssp.entity;

import java.io.Serializable;

import javax.persistence.Table;

import javax.persistence.Entity;

/**
 * 班级实体类
 * Description:
 * @author: xulihua
 * @date: 2017年1月5日下午2:30:50
 */
@Entity
@Table(name = "acms_sch_class")
public class AcmsSchClass implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long classId;
	
	private String className;

	/**
	 * @return the classId
	 */
	public Long getClassId() {
		return classId;
	}

	/**
	 * @param classId the classId to set
	 */
	public void setClassId(Long classId) {
		this.classId = classId;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
}
