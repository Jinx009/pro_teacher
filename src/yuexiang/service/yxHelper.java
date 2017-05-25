package yuexiang.service;

import java.util.List;

import nbBase.helper.common.CommonHelper;
import yuexiang.database.models.YuexiangComment;

public class yxHelper {
	
	public static YuexiangComment getBestCommentByLikes(List<YuexiangComment> comments){
		if( CommonHelper.isListNullOrEmpty(comments) )
			return null;
		
		int mostLikesNumber = 0;
		YuexiangComment mostComment = null;
		int lessLikesNumber = 0;
		//YuexiangComment lessComment = null;
		
		for(YuexiangComment comment : comments){
			int likes = comment.getYuexiangLikes().size();
			if( mostLikesNumber < likes ){
				mostLikesNumber = likes;
				mostComment = comment;
			}
			if( lessLikesNumber < likes && comment.getId()!=mostComment.getId()){
				lessLikesNumber = likes;
				//lessComment = comment;
			}
		}
		
		if( mostLikesNumber == lessLikesNumber || mostLikesNumber == 0){
			return null;
		}
		
		return mostComment;
	}
	
	public static YuexiangComment getBestComment(YuexiangComment comment){
		if(comment == null)
			return null;
		YuexiangComment bestComment = comment.getBestComment();
		if( bestComment == null ){
			bestComment = yxHelper.getBestCommentByLikes(comment.getChildList());
		}
		return bestComment;
	}

}
