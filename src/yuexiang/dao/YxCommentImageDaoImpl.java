package yuexiang.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import yuexiang.database.models.YuexiangComment;
import yuexiang.database.models.YuexiangCommentImage;


@Transactional
@Repository("yxCommentImageDao")
public class YxCommentImageDaoImpl extends BaseDaoImpl<YuexiangCommentImage> implements YxCommentImageDao{

	@Override
	public List<YuexiangCommentImage> save(List<Map<String, Object>> picList, YuexiangComment parent) {
		
		List<YuexiangCommentImage> ret = new ArrayList<YuexiangCommentImage>();
		
		for( Map<String, Object> pic : picList){
			YuexiangCommentImage commentImage = new YuexiangCommentImage();
			commentImage.setParentComment(parent);
			commentImage.setImgUrl(pic.get("imgUrl").toString());
			commentImage.setThumUrl(pic.get("thumUrl").toString());
			ret.add(commentImage);
		}
		
		this.save(ret);
		return ret;
	}

	@Override
	public List<YuexiangCommentImage> clone(List<YuexiangCommentImage> imageList, YuexiangComment newComment) {
		List<YuexiangCommentImage> newImgList = imageList;
		
		for( YuexiangCommentImage img : newImgList){
			img.setId(0);
			img.setParentComment(newComment);
		}
		
		this.save(newImgList);
		return newImgList;
	}

}
