package cn.edu.haut.cssp.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xdja.cssp.oms.system.entity.TUserRole;
import com.xdja.platform.datacenter.jpa.dao.BaseJpaDao;
import com.xdja.platform.datacenter.jpa.dao.helper.condition.Conditions;

/**
 * 
 * 
 * Project Name：pms-service-oms-system
 * ClassName：TOmsUserRoleDao
 * Description：用户-角色关联实体数据库操作类
 * @author 马德成
 * @date 2015-10-21
 * note:
 *
 *@author fy
 *@date 2015-04-28
 */
@Repository
public class TUserRoleDao extends BaseJpaDao<TUserRole, Long> {

	/**
	 * 
	 * @Description：根据roleId删除用户-角色关系
	 * @author 马德成
	 * @date 2015-10-21
	 * @param roleId
	 */
	public void removeUserRoleByRoleId(Long roleId) {
		this.deleteByCondition("t_user_role", Conditions.eq("n_role_id", roleId));
	}
	
	/**
	 * 
	 * @Description：根据userId删除用户-角色关系
	 * @author 马德成
	 * @date 2015-10-21
	 * @param userId
	 */
	public void removeUserRoleByUserId(Long userId){
		this.deleteByCondition("t_user_role", Conditions.eq("n_user_id", userId));
	}
	
	/**
	 * 根据用户id查找对应用户-角色信息
	 * @Description：
	 * @author 马德成
	 * @date 2015-10-21
	 * @param userId
	 * @return
	 */
	public List<TUserRole> queryUserRoleListByUserId(Long userId) {
		MapSqlParameterSource sqlParam = new MapSqlParameterSource();
		String sql = "SELECT n_role_id AS roleId FROM t_user_role WHERE n_user_id = :userId";
		sqlParam.addValue("userId", userId);
		
		return this.queryForList(sql, sqlParam, BeanPropertyRowMapper.newInstance(TUserRole.class));
	}

	/**
	 * 根据roleId得到使用该角色的用户数
	 * @Description：
	 * @author 马德成
	 * @date 2015-10-21
	 * @param roleId
	 * @return
	 */
	public Integer getUserCountByRoleId(Long roleId) {
		return this.countByCondition("t_user_role", Conditions.eq("n_role_id", roleId));
	}
}
