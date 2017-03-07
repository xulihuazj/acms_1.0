package cn.edu.haut.cssp.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.genericdao.search.Search;
import com.xdja.cssp.oms.system.business.IDicBusiness;
import com.xdja.cssp.oms.system.condition.DicCondition;
import com.xdja.cssp.oms.system.dao.TDicJdbcDao;
import com.xdja.cssp.oms.system.dao.TDicJpaDao;
import com.xdja.cssp.oms.system.entity.TDic;
import com.xdja.platform.rpc.proxy.PagingConverter;
import com.xdja.platform.rpc.proxy.page.LitePaging;

@Service
public class DicBusinessImpl implements IDicBusiness {

	@Autowired
	private TDicJdbcDao dicJdbcDao;

	@Autowired
	private TDicJpaDao dicJpaDao;

	@Override
	public LitePaging<TDic> queryAllTopDics(DicCondition condition, int page, int length, String orderColName,
			String orderDir) {
		return PagingConverter.convert(dicJdbcDao.queryAllTopDics(condition, page, length, orderColName, orderDir));
	}

	@Override
	public void saveDic(TDic dic) {
		dicJpaDao.save(dic);
	}

	@Override
	public TDic getDicById(Long dicId) {

		return dicJpaDao.find(dicId);
	}

	@Override
	public Boolean deleteDicById(DicCondition condition) {
		// 删除子集
		this.dicJdbcDao.deleteSubListByCode(condition.getCode());

		// 删除字典信息
		this.dicJpaDao.removeById(condition.getDicId());

		return true;
	}

	@Override
	public boolean isDicNameExist(String dicId, String name) {
		Search search = new Search(TDic.class);
		search.addFilterEqual("name", name);
		if (StringUtils.isNotBlank(dicId)) {
			search.addFilterNotEqual("id", dicId);
		}

		return this.dicJpaDao.count(search) > 0;
	}

	@Override
	public List<TDic> queryAllTopDicsOrderByparentCode() {
		Search search = new Search(TDic.class);
		search.addSortAsc("parentCode");
		search.addSortAsc("sort");
		return dicJpaDao.search(search);
	}

	@Override
	public boolean isDicCodeExist(String dicId, String name) {
		Search search = new Search(TDic.class);
		search.addFilterEqual("code", name);
		if (StringUtils.isNotBlank(dicId)) {
			search.addFilterEqual("parentCode", dicId);
		}

		return this.dicJpaDao.count(search) > 0;
	}

	@Override
	public boolean isDicCodeNameExist(String code, String name) {
		Search search = new Search(TDic.class);
		search.addFilterEqual("name", name);
		if (StringUtils.isNotBlank(code)) {
			search.addFilterEqual("parentCode", code);
		}

		return this.dicJpaDao.count(search) > 0;
	}

	@Override
	public boolean isDicNameExist(String parendCode, Long id, String name) {
		Search search = new Search(TDic.class);
		search.addFilterEqual("name", name);
		if (!StringUtils.isBlank(parendCode)) {
			search.addFilterEqual("parentCode", parendCode);
		}
		search.addFilterNotEqual("id", id);
		return this.dicJpaDao.count(search) > 0;
	}
}
