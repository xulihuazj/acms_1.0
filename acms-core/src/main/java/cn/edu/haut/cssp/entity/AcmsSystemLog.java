package cn.edu.haut.cssp.entity;

import java.io.Serializable;

import javax.persistence.Table;

import javax.persistence.Entity;

/**
 * 系统日志实体类
 * Description:
 * @author: xulihua
 * @date: 2017年1月5日下午2:43:16
 */
@Entity
@Table(name = "acms_sys_log")
public class AcmsSystemLog implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private String userName;
	private String logContent;
	private String logLevel;
	private String methodInfo;
	private Long logTime;
	private Integer logType;
	
	
}
