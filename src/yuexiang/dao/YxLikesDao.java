package yuexiang.dao;

import nbBase.database.common.BaseDao;
import nbBase.database.models.ZaFrontUserWx;
import yuexiang.database.models.YuexiangComment;
import yuexiang.database.models.YuexiangLike;

public interface YxLikesDao extends BaseDao<YuexiangLike>{

	YuexiangLike findByCommentAndWxUser(YuexiangComment yxComment, ZaFrontUserWx wxUser);

	
}
