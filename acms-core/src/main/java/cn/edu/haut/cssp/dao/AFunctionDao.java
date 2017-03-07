package cn.edu.haut.cssp.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import com.googlecode.genericdao.search.Search;

import cn.edu.haut.cssp.entity.AFunction;
import cn.edu.haut.cssp.entity.AcmsUser;

/**
 * 菜单权限数据库操作类
 * @ClassName：FunctionDao
 *
 */
@Repository
public class AFunctionDao extends BaseJpaDao<Function, Serializable> {
	
	/**
	 * 查询系统管理员所有菜单权限
	 * @return
	 */
	public List<AFunction> queryAdminFunctions() {
		Search search = new Search(AFunction.class);
		search.addFilterEqual("status", AFunction.ENUM_FUNCTION_STATUS.normalStatus.value);
		search.addSortAsc("parentId");
		search.addSortAsc("orderNum");
		
		return this.search(search);
	}
	
	/**
	 * 
	 * @Description：查询普通的功能菜单
	 * @return
	 */
	public List<AFunction> queryAllFunctions() {
		StringBuilder sqlBuilder = new StringBuilder("SELECT n_id AS id, c_name AS name,n_parent_id AS parentId ")
			.append("FROM t_function WHERE n_type = :type AND n_status = :status");
		
		MapSqlParameterSource sqlParam = new MapSqlParameterSource();
		sqlParam.addValue("type", AFunction.ENUM_FUNCTION_TYPE.consumerFunction.value);
		sqlParam.addValue("status", AFunction.ENUM_FUNCTION_STATUS.normalStatus.value);
		
		return this.queryForList(sqlBuilder.toString(), sqlParam, BeanPropertyRowMapper.newInstance(Function.class));
	}
	
	/**
	 * 根据用户信息查询 用户拥有的菜单权限
	 * @Description：
	 * @param user 用户信息
	 */
	public List<AFunction> queryUserFunctions(AcmsUser user) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT function.n_id AS id, function.c_name AS name, function.n_parent_id AS parentId, ")
			.append("function.c_obj_name AS objName, function.c_icon AS icon, function.c_link AS link, function.n_order_num AS orderNum ")
			.append("FROM t_user user ")
			.append("INNER JOIN t_user_role userRole ON user.n_id = userRole.n_user_id ")
			.append("INNER JOIN t_role_function roleFunc ON userRole.n_role_id = roleFunc.n_role_id ")
			.append("INNER JOIN t_function function ON function.n_id = roleFunc.n_function_id ")
			.append("WHERE user.n_id = :uId ")
			.append("AND function.n_status = :status");
		
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		sqlParameterSource.addValue("uId", user.getId());
		sqlParameterSource.addValue("status", AcmsUser.ENUM_USER_STATUS.normalStatus.value);
		
		return this.queryForList(sqlBuilder.toString(), sqlParameterSource, BeanPropertyRowMapper.newInstance(Function.class));
	}
}
