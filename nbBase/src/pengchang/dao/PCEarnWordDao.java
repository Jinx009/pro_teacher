package pengchang.dao;

import java.util.List;

import nbBase.database.common.BaseDao;
import pengchang.database.models.PCEarnWord;




public interface PCEarnWordDao extends BaseDao<PCEarnWord>{

	int countAll();

	List<PCEarnWord> findByIdArray(Integer[] indexArray);

	
}
