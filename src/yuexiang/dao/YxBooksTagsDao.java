package yuexiang.dao;

import java.util.List;

import nbBase.database.common.BaseDao;
import yuexiang.database.models.YuexiangBooksTag;

public interface YxBooksTagsDao extends BaseDao<YuexiangBooksTag>{

	List<Integer> getBookIdByTagIdList(List<Integer> tagFilterList);

	
}
