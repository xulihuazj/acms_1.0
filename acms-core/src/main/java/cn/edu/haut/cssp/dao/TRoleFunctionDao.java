package cn.edu.haut.cssp.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xdja.cssp.oms.system.entity.TRoleFunction;
import com.xdja.platform.datacenter.jpa.dao.BaseJpaDao;
import com.xdja.platform.datacenter.jpa.dao.helper.condition.Conditions;

/**
 * 
 * 
 * Project Name：pms-service-oms-system
 * ClassName：TOmsRoleFunctionDao
 * Description：角色-功能关联实体数据库操作类
 * @author 马德成
 * @date 2015-10-21
 * note:
 *
 */
@Repository
public class TRoleFunctionDao extends BaseJpaDao<TRoleFunction, Long> {

	/**
	 * 
	 * @Description：根据roleId删除对应的角色-功能对应关系
	 * @author 马德成
	 * @date 2015-10-21
	 * @param roleId
	 */
	public void removeRoleFuncById(Long roleId) {
		this.deleteByCondition("t_role_function", Conditions.eq("n_role_id", roleId));
	}

	/**
	 * 
	 * @Description：根据roleId得到与该角色关联的功能列表
	 * @author 马德成
	 * @date 2015-10-21
	 * @param roleId
	 * @return
	 */
	public List<TRoleFunction> queryRoleFunctionByRoleId(Long roleId) {
		MapSqlParameterSource sqlParam = new MapSqlParameterSource();
		String sql = "SELECT n_id AS id, n_function_id AS functionId FROM t_role_function " +
			"WHERE n_role_id = :roleId";
		sqlParam.addValue("roleId", roleId);
		
		return this.queryForList(sql, sqlParam, BeanPropertyRowMapper.newInstance(TRoleFunction.class));
	}
}
