package cn.edu.haut.cssp.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import cn.edu.haut.cssp.entity.AcmsUser;

/**
 * 用户数据库操作类
 * Description:
 * @author: xulihua
 * @date: 2017年1月19日下午4:42:19
 * @note
 */
@Repository
public class AUserDao extends BaseJpaDao<AcmsUser, Long> {

	/**
	 * 
	 * @Description：检验系统是否已初始化。返回true,系统已初始化；反之，系统未初始化
	 */
	public Integer isSysInit() {
		MapSqlParameterSource sqlParam = new MapSqlParameterSource();
		String sql = "SELECT COUNT(n_id) FROM t_user WHERE n_type = :type";
		
		sqlParam.addValue("type", AcmsUser.ENUM_USER_TYPE.systemUser.value);
		return this.queryForInt(sql, sqlParam);
	}
	
	/**
	 * 
	 * @Description：根据权限查询所有用户信息
	 * @author 马德成
	 * @date 2015-10-21
	 * @param user  查询条件
	 * @param pageNo 页码
	 * @param pageSize 每页显示条数
	 * @param sortName 排序名
	 * @param sortType 排序类型  默认DESC
	 * @param currUserName 
	 * @return
	 */
	public Pagination<AcmsUser> queryAllUsers(AcmsUser user, Integer pageNo, Integer pageSize, 
		String sortName, String sortType) {
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		StringBuilder sqlBuilder = new StringBuilder("SELECT n_id AS id, c_user_name AS userName, c_id_number AS idNumber, ")
			.append("c_name AS name, c_mobile AS mobile, n_status AS status, n_time AS createTime FROM t_user WHERE 1=1 ");
		
		if (null != user) {
			if (StringUtils.isNotBlank(user.getName())) {
				String name = user.getName();
				sqlBuilder.append("AND c_name like :name ESCAPE '/' ");
				name = name.replaceAll("%", "/%");
				name = name.replaceAll("_", "/_");
				sqlParameterSource.addValue("name", "%" + name + "%");
			}
			if (StringUtils.isNotBlank(user.geAcmsUserName())) {
				String userName = user.geAcmsUserName();
				sqlBuilder.append("AND c_user_name like :userName ESCAPE '/' ");
				userName = userName.replaceAll("%", "/%");
				userName = userName.replaceAll("_", "/_");
				sqlParameterSource.addValue("userName", "%" + userName + "%");
			}
		}
		sqlBuilder.append("AND n_delete_flag = :deleteFlag ");
		sqlParameterSource.addValue("deleteFlag", AcmsUser.ENUM_DELETE_FLAG.normalFlag.value);
		sqlBuilder.append("AND n_type = :type ");
		sqlParameterSource.addValue("type", AcmsUser.ENUM_USER_TYPE.consumerUser.value);
		
		//排序
		if(StringUtils.isNotEmpty(sortName) && StringUtils.isNotEmpty(sortType)) {
			sqlBuilder.append("ORDER BY " + sortName + " " + sortType);
		} else {
			sqlBuilder.append("ORDER BY n_time DESC ");
		}
		return this.queryForPage(sqlBuilder.toString(), pageSize, pageNo, sqlParameterSource, 
			BeanPropertyRowMapper.newInstance(AcmsUser.class));
	}

}
