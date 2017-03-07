package cn.edu.haut.cssp.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import cn.edu.haut.cssp.entity.SystemLog;

/**
 * 系统日志数据层
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午4:19:59
 * @note
 */
@Repository
public class SystemLogDao extends BaseJpaDao<SystemLog, Long> {

	/**
	 * 
	 * 方法描述：修改表名
	 * @param syslog
	 * @param pageNo
	 * @param pageSize
	 * @param sortName
	 * @param sortType
	 * @param queryBean
	 * @return
	 */
	public Pagination<SystemLog> querySystemLogs(SystemLog syslog,
			Integer pageNo, Integer pageSize, String sortName, String sortType,
			DateQueryBean queryBean) {

		StringBuilder sqlBuilder = new StringBuilder(
				"SELECT log.n_id as id ,log.n_type as logType, ")
				.append("log.n_time  as logTime ,user.c_name as userName , log.c_content as logContent FROM t_log ")
				.append("log  LEFT JOIN  t_user user  ON  log.n_user_id = user.n_id  where 1=1 ");

		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

		if (null != syslog) {
			if (null != syslog.getLogType()) {
				sqlBuilder.append("AND  log.n_type = :type ");
				sqlParameterSource.addValue("type", syslog.getLogType());
			}
			if (null != syslog.getModelType()) {
				sqlBuilder.append("AND log.n_model_type = :modelType ");
				sqlParameterSource.addValue("modelType", syslog.getModelType());
			}
		}

		if (null != queryBean) {
			if (null != queryBean.getStart()) {
				sqlBuilder.append("AND log.n_time >= :startTime ");
				sqlParameterSource.addValue("startTime", queryBean.getStart());

				System.out.println(queryBean.getStart());
			}
			if (null != queryBean.getEnd()) {
				sqlBuilder.append("AND log.n_time <= :endTime ");
				sqlParameterSource.addValue("endTime", queryBean.getEnd());
			}
		}
		if (StringUtils.isNotEmpty(sortName) && StringUtils.isNotEmpty(sortType) ) {
			sqlBuilder.append("ORDER BY " + sortName + " " + sortType);
		} else {
			sqlBuilder.append("ORDER BY log.n_time DESC ");
		}
		return this.queryForPage(sqlBuilder.toString(), pageSize, pageNo,
				sqlParameterSource,
				BeanPropertyRowMapper.newInstance(SystemLog.class));
	}

	/**
	 * 删除当前日志
	 * @author: xulihua
	 * @date: 2017年1月21日下午4:58:20
	 */
	public void deleteSystemLog(Long systemLong) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete acms_system_log asl ")
	}
	
}
