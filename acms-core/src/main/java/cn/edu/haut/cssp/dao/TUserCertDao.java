package cn.edu.haut.cssp.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xdja.cssp.oms.system.entity.TUserCert;
import com.xdja.platform.datacenter.jpa.dao.BaseJpaDao;
import com.xdja.platform.datacenter.jpa.dao.helper.condition.Conditions;
import com.xdja.platform.datacenter.jpa.page.Pagination;

/**
 * 
 * 
 * Project Name：pms-service-system
 * ClassName：TUserCertDao
 * Description：用户安全卡绑定数据库操作类
 * @author: shaoyuehua
 * @date: 2015-1-19 下午7:10:26
 * note:
 *
 */
@Repository
public class TUserCertDao extends BaseJpaDao<TUserCert, Long> {

	/**
	 * 根据卡id得到系统中绑定了此卡的人数
	 * @Description：
	 * @author: shaoyuehua
	 * @date: 2015-1-30 上午11:37:26
	 * @param cardId
	 * @return
	 */
	public Integer queryUserCertByCardId(String cardId) {
		return this.countByCondition("t_user_cert", Conditions.eq("c_card_id", cardId));
	}

	/**
	 * 根据用户id得到该用户的卡绑定信息列表
	 * @Description：
	 * @author: shaoyuehua
	 * @date: 2015-1-31 上午10:24:35
	 * @param userCert
	 * @param pageNo
	 * @param pageSize
	 * @param sortName
	 * @param sortType
	 * @return
	 */
	public Pagination<TUserCert> queryAllUserCert(Long userId, Integer pageNo,
			Integer pageSize, String sortName, String sortType) {
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		
		StringBuilder sqlBuilder = new StringBuilder("SELECT n_id AS id, n_user_id AS userId, ")
			.append("c_note AS note, c_card_id AS cardId FROM t_user_cert WHERE ")
			.append("n_user_id = :userId ");
		sqlParameterSource.addValue("userId", userId);
		
		//排序
		if(StringUtils.isNotEmpty(sortName) && StringUtils.isNotEmpty(sortType)) {
			sqlBuilder.append("ORDER BY " + sortName + " " + sortType);
		} else {
			sqlBuilder.append("ORDER BY n_id DESC ");
		}
			
		return this.queryForPage(sqlBuilder.toString(), pageSize, pageNo, sqlParameterSource, 
			BeanPropertyRowMapper.newInstance(TUserCert.class));
	}
	
	/**
	 * 根据user_id删除用户-卡绑定信息
	 * @Description：
	 * @author: shaoyuehua
	 * @date: 2015-2-3 上午10:39:51
	 * @param userId
	 */
	public void removeUserCertByUserId(Long userId) {
		this.deleteByCondition("t_user_cert", Conditions.eq("n_user_id", userId));
	}
}
