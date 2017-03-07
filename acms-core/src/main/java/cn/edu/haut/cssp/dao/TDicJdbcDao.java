package cn.edu.haut.cssp.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xdja.cssp.oms.system.condition.DicCondition;
import com.xdja.cssp.oms.system.entity.TDic;
import com.xdja.platform.datacenter.jpa.dao.BaseJdbcDao;
import com.xdja.platform.datacenter.jpa.page.Pagination;

/**
 * 
 * 字典信息管理JDBC DAO层 Project Name：ams-service-system ClassName：TDicJdbcDao
 * 
 * @author: fy
 * @date: 2015-4-28 下午7:17:28 note:
 * 
 */
@Repository
public class TDicJdbcDao extends BaseJdbcDao {

	/**
	 * 
	 * 方法描述：获取字典分页数据
	 * 
	 * @author: fy
	 * @date: 2015-4-28 下午7:18:35
	 * @param name
	 * @param page
	 * @param length
	 * @param orderColName
	 * @param orderDir
	 * @return
	 */
	public Pagination<TDic> queryAllTopDics(DicCondition condition, int page, int length, String orderColName,
			String orderDir) {
		StringBuffer sqlBuffer = new StringBuffer(
				" SELECT t.n_id AS id,t.c_code AS code,t.c_name AS name,t.c_parent_code AS parentCode, ").append(
				" t.c_root_code AS rootCode,t.n_multi_level AS multiLevel,t.c_extend AS extend,t.c_note AS note ")
				.append(" FROM t_dic t WHERE 1=1");

		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

		if (condition != null && StringUtils.isNotBlank(condition.getName())) {
			sqlBuffer.append(" AND t.c_name LIKE :name ESCAPE '/' ");
			String name = condition.getName();
			name = name.replaceAll("%", "/%");
			name = name.replaceAll("_", "/_");
			sqlParameterSource.addValue("name", "%" + name + "%");
		}

		if (condition != null && StringUtils.isNotBlank(condition.getParentCode())) {
			sqlBuffer.append(" AND t.c_parent_code = :parentCode ");
			sqlParameterSource.addValue("parentCode", condition.getParentCode());
		}
		sqlBuffer.append(" ORDER BY n_sort DESC");
		return this.queryForPage(sqlBuffer.toString(), length, page, sqlParameterSource,
				new BeanPropertyRowMapper<TDic>(TDic.class));
	}

	/**
	 * 
	 * 方法描述：通过根编码查询字典信息
	 * 
	 * @author: fy
	 * @date: 2015-4-28 下午7:56:56
	 * @param code
	 */
	public int deleteSubListByCode(String code) {
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

		sqlParameterSource.addValue("parentCode", code);
		return this.deleteBySql("DELETE FROM t_dic WHERE c_parent_code =:parentCode", sqlParameterSource);
	};
}
