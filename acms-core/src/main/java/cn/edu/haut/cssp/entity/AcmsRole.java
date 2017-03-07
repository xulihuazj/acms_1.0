package cn.edu.haut.cssp.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 系统角色实体映射类
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午3:43:25
 * @note
 */
@Entity
@Table(name = "acms_role")
public class AcmsRole implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 编号，主键，自增
	 */
	private Long id;
	
	/**
	 * 用户名
	 */
	private String name;
	
	/**
	 * 角色类型  1-系统角色；2-普通用户
	 */
	private Integer type;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 用户是否拥有该角色
	 */
	private boolean checkFlag = false;
	
	@Id
	@GeneratedValue
	@Column(name = "n_id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "c_name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "n_type", nullable = false)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "c_note", nullable = true)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Transient
	public boolean isCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(boolean checkFlag) {
		this.checkFlag = checkFlag;
	}

	/**
	 * 
	 * 
	 * Project Name：pms-service-oms-system-api
	 * ClassName：ENUM_ROLE_TYPE
	 * Description：角色类型定义
	 * @author 马德成
	 * @date 2015-10-21
	 * note:
	 *
	 */
	public enum ENUM_ROLE_TYPE {
		/**
		 * 1-系统角色
		 */
		systemRole(1),
		/**
		 * 2-普通角色
		 */
		consumerRole(2);

		public Integer value;
		
		private ENUM_ROLE_TYPE(Integer value) {
			this.value = value;
		}
	}
}