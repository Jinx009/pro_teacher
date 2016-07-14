package yuexiang.dao;

import java.util.List;

import nbBase.database.common.BaseDao;
import nbBase.database.models.ZaFrontUserWx;
import yuexiang.database.models.YuexiangComment;
import yuexiang.database.models.YuexiangTopic;

public interface YxCommentsDao extends BaseDao<YuexiangComment>{

	List<YuexiangComment> findByBookId(Integer bookId, Integer startIndex, Integer pageSize, boolean isFromWarehouse);

	List<YuexiangComment> findByMyCriteria01(YuexiangTopic topic, Integer startIndex, Integer pageSize,
			ZaFrontUserWx wxUser, Boolean isPublic);

	int countGotLikes(ZaFrontUserWx wxUser);
	
}
