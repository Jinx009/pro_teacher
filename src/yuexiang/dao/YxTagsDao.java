package yuexiang.dao;

import java.util.List;

import nbBase.database.common.BaseDao;
import yuexiang.database.models.YuexiangTag;

public interface YxTagsDao extends BaseDao<YuexiangTag>{

	List<String> findCatalogList();

	List<YuexiangTag> findByIds(List<Integer> tagFilterList);

	List<YuexiangTag> findIn(List<YuexiangTag> tags);
	
	List<YuexiangTag> findInIds(List<Integer> tags);

	YuexiangTag findByName(String tagName);

	
}
