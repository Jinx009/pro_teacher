package yuexiang.dao;

import java.util.List;

import nbBase.database.common.BaseDao;
import yuexiang.database.models.YuexiangBook;

public interface YxBookDao extends BaseDao<YuexiangBook>{

	Object findByCertainConditions(
			Integer startIndex, 
			Integer pageSize,
			List<Integer> tagFilterList, 
			String searchKeyWord, 
			Boolean isWarehouse, Boolean isVerbalOnly,Boolean queryCount);


	
}
