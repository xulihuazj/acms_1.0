package cn.edu.haut.cssp.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户-角色关联实体映射类
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午3:41:05
 * @note
 */
@Entity
@Table(name = "acms_user_role")
public class AcmsUserRole implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 编号，主键，自增
	 */
	private Long id;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 角色id
	 */
	private Long roleId;
	
	@Id
	@GeneratedValue
	@Column(name = "n_id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "n_user_id", nullable = false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Column(name = "n_role_id", nullable = false)
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}