package cn.edu.haut.cssp.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xdja.cssp.oms.system.entity.TRole;
import com.xdja.platform.datacenter.jpa.dao.BaseJpaDao;
import com.xdja.platform.datacenter.jpa.page.Pagination;

/**
 * 
 * 
 * Project Name：pms-service-oms-system
 * ClassName：TRoleDao
 * Description：角色管理数据库操作类
 * @author 马德成
 * @date 2015-10-21
 * note:
 *
 *@author fy
 *@date 2015-4-28
 */
@Repository
public class TRoleDao extends BaseJpaDao<TRole, Long> {

	/**
	 * 
	 * @Description：分页查询角色列表
	 * @author 马德成
	 * @date 2015-10-21
	 * @param roleCondition
	 * @param pageNo
	 * @param pageSize
	 * @param sortName
	 * @param sortType
	 * @return
	 */
	public Pagination<TRole> queryAllRoles(TRole roleCondition, 
			Integer pageNo, Integer pageSize, String sortName, String sortType) {
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		StringBuilder sqlBuilder = new StringBuilder("SELECT n_id AS id, c_name AS name, ")
			.append("n_type AS type, c_note AS note FROM t_role WHERE 1 = 1 ");
		
		// 根据查询条件封装sql
		if (null != roleCondition && StringUtils.isNotBlank(roleCondition.getName())) {
			String name = roleCondition.getName();
			sqlBuilder.append("AND c_name like :name ESCAPE '/' ");
			name = name.replaceAll("%", "/%");
			name = name.replaceAll("_", "/_");
			sqlParameterSource.addValue("name", "%" + name + "%");
		}
		
		// 将排序条件、排序方式
		if (StringUtils.isNotEmpty(sortName) && StringUtils.isNotEmpty(sortType)) {
			sqlBuilder.append("ORDER BY " + sortName + " " + sortType);
		} else {
			sqlBuilder.append("ORDER BY n_id DESC ");
		}
		return this.queryForPage(sqlBuilder.toString(), pageSize, pageNo, sqlParameterSource, 
			BeanPropertyRowMapper.newInstance(TRole.class));
	}
}
