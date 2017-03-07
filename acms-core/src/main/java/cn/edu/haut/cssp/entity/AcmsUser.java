package cn.edu.haut.cssp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 管理员实体类
 * Description:
 * @author: xulihua
 * @date: 2017年1月5日下午2:11:47
 */
@Entity
@Table(name = "acms_sys_user")
public class AcmsUser implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 默认密码
	 */
	public static final String DEFAUTPASSWORD = "111111";

	/**
	 * 资源id，主键，自增
	 */
	private Long id;

	/**
	 * 用户名，不可为空
	 */
	private String userName;

	/**
	 * 密码值SHA1后的小写16进制字符串
	 */
	private String password;

	/**
	 * 关联t_emm_ec_info中的c_code
	 */
	private String ecCode;

	/**
	 * 1-超级管理员；2-普通管理员
	 */
	private Integer type;

	/**
	 * 用户姓名
	 */
	private String name;
	
	/**
	 * 角色id
	 */
	private Long roleId;
	
	/**
	 * 功能ids
	 */
	private String functionIds;
	
	/**
	 * 角色ids
	 */
	private String roleIds;
	
	/**
	 * 角色名字
	 */
	private String roleName;
	
	/**
	 * 1-正常；2-停用 ；-1 - 删除； 默认 ：1
	 */
	private Integer status;
	
	/**
	 * 
	 */
	private Long time;
	
	/** 指定角色名称做查询 */
	private Integer[] roleArray;
	
	private String[] cardInfoLists;
	

	@Id
	@GeneratedValue
	@Column(name = "n_id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Transient
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Column(name = "c_user_name", nullable = false)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "c_password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "c_ec_code", nullable = false)
	public String getEcCode() {
		return ecCode;
	}

	public void setEcCode(String ecCode) {
		this.ecCode = ecCode;
	}

	@Column(name = "n_type", nullable = false)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "c_name", nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	@Column(name = "n_status", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "n_time", nullable = false)
	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	

	@Transient
	public String getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	@Transient
	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	@Transient
	public Integer[] getRoleArray() {
		return roleArray;
	}

	public void setRoleArray(Integer[] roleArray) {
		this.roleArray = roleArray;
	}

	@Transient
	public String[] getCardInfoLists() {
		return cardInfoLists;
	}

	public void setCardInfoLists(String[] cardInfoLists) {
		this.cardInfoLists = cardInfoLists;
	}
	/**
	 * 管理员类型
	 * Description:
	 * @author: xulihua
	 * @date: 2017年1月18日下午2:41:40
	 */
	public enum ENUM_USER_TYPE {
		/**
		 * 1-超级管理员
		 */
		sysType(1),
		/**
		 * 2-普通管理员
		 */
		normalType(2);
		
		public Integer value;
		
		private ENUM_USER_TYPE(Integer value) {
			this.value = value;
		}
	}
	/**
	 * 用户账号状态
	 * Description:
	 * @author: xulihua
	 * @date: 2017年1月18日下午2:42:02
	 */
	public enum ENUM_USER_STATUS {
		/**
		 * 1-正常
		 */
		normalStatus(1),
		/**
		 * 2-停用
		 */
		stopStatus(2),
		/**
		 * -1-删除
		 * */
		deletedStatus(-1);
		
		public Integer value;
		
		private ENUM_USER_STATUS(Integer value) {
			this.value = value;
		}
	}
	
	public enum ENUM_USER_UPDATE_TYPE {
		/**
		 * 1-添加管理员
		 */
		addType(1),
		/**
		 * 2-修改管理员及相关数据
		 */
		editType(2),
		/**
		 * 3-删除管理员
		 */
		deleteType(3);
		
		public Integer value;
		
		private ENUM_USER_UPDATE_TYPE(Integer value) {
			this.value = value;
		}
	}

}
