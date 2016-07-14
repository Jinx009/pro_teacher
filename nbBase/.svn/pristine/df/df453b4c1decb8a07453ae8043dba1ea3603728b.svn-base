package yuexiang.dao;

import java.util.List;
import java.util.Map;

import nbBase.database.common.BaseDao;
import yuexiang.database.models.YuexiangComment;
import yuexiang.database.models.YuexiangCommentImage;


public interface YxCommentImageDao extends BaseDao<YuexiangCommentImage>{

	List<YuexiangCommentImage> save(List<Map<String, Object>> picList, YuexiangComment parent);

	List<YuexiangCommentImage> clone(List<YuexiangCommentImage> imageList, YuexiangComment newComment);

	
}
