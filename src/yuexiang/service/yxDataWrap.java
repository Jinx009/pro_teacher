package yuexiang.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nbBase.database.models.ZaAdminUser;
import nbBase.database.models.ZaFrontUserWx;
import nbBase.helper.common.nbStringUtil;
import yuexiang.database.models.YuexiangBook;
import yuexiang.database.models.YuexiangComment;
import yuexiang.database.models.YuexiangCommentImage;
import yuexiang.database.models.YuexiangLike;
import yuexiang.database.models.YuexiangTag;

public class yxDataWrap {

	public static Map<String, Object> getBooksForListView( List<YuexiangBook> yxBooksWarehouseList, Long totalNumber){
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Map<String, Object>> books = new ArrayList<Map<String, Object>>();
		
		for(YuexiangBook yxBook : yxBooksWarehouseList){
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("id", yxBook.getId());
			retMap.put("time", nbStringUtil.DateTime2StrinF01(yxBook.getCreateDate()));
			retMap.put("imgUrl", yxBook.getBookCoverImg());
			retMap.put("thumUrl", yxBook.getBookCoverThumImg());
			retMap.put("introduce", yxBook.getBookDescription());
			retMap.put("title", yxBook.getBookName());
			retMap.put("verbalUrl", yxBook.getVerbalUrl());
			retMap.put("isRecom", false);
			retMap.put("tags", yxDataWrap.getTagsForViewOnly(yxBook.getTags()));
			retMap.put("isWarehouse", true);
			books.add(retMap);
		}
		
		ret.put("books", books);
		ret.put("totalNumber", totalNumber);
			
		return ret;
		
	}
	
	public static List<Map<String, Object>> getAllTags(List<String> catalogNames, List<YuexiangTag> yxTags, List<Integer> selected){
		
		List<Map<String, Object>> tags = new ArrayList<Map<String,Object>>();
		
		for( String catalog : catalogNames ){
			
			Map<String, Object> oneCatalog = new HashMap<String, Object>();
			oneCatalog.put("catalog", catalog);
			List<Map<String, Object>> tagList = new ArrayList<Map<String, Object>>();
			
			for( YuexiangTag tag : yxTags){
				
				if( !catalog.equals(tag.getCatalog()) )
						continue;
				if( selected != null && !selected.contains(Integer.valueOf(tag.getId())) ){
					continue;
				}
				
				oneCatalog.put("isSingleCheck", !tag.getIsMuiltySelect() );
				
				Map<String, Object> oneTag = new HashMap<String, Object>();
				oneTag.put("id", tag.getId());
				
				if( "适合年龄".equals(catalog) ){
					if( Integer.valueOf(tag.getTagMax()) <= 12 && Integer.valueOf(tag.getTagMax()) != 0){
						oneTag.put("text", (Integer.valueOf(tag.getTag())>6?"半":"0") +"~"+ (Integer.valueOf(tag.getTagMax())<=6?"半":"1") +"岁");
					}
					else {
						if( Integer.valueOf(tag.getTagMax()) != 0 ){
						oneTag.put("text", Integer.valueOf(tag.getTag())/12+"~"+Integer.valueOf(tag.getTagMax())/12+"岁");
						}else{
							oneTag.put("text", Integer.valueOf(tag.getTag())/12+"岁以上");
						}
					}
				}else{
					oneTag.put("text", tag.getTag());
				}
				
				tagList.add(oneTag);
			}
			oneCatalog.put("tags", tagList);
			
			tags.add(oneCatalog);
				
		}
		
		return tags;
			
	}
		
	
	@SuppressWarnings("unchecked")
	public static List<String> getTagsForViewOnly(List<YuexiangTag> yxTags){
		List<String> ret = new ArrayList<String>(); 
		List<String> catalogNames = new ArrayList<String>();
		for( YuexiangTag tag : yxTags){
			if( catalogNames.indexOf( tag.getCatalog() ) < 0  ){
				catalogNames.add(tag.getCatalog());
			}
		}
		List<Map<String, Object>> allTags = yxDataWrap.getAllTags(catalogNames, yxTags, null);
		for( Map<String, Object> oneCata : allTags){
			for(Map<String, Object> tag: (List<Map<String, Object>>)oneCata.get("tags")){
				ret.add(tag.get("text").toString());
			}
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getTagsForViewOnlyType2(List<YuexiangTag> yxTags){
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>(); 
		List<String> catalogNames = new ArrayList<String>();
		for( YuexiangTag tag : yxTags){
			if( catalogNames.indexOf( tag.getCatalog() ) < 0  ){
				catalogNames.add(tag.getCatalog());
			}
		}
		List<Map<String, Object>> allTags = yxDataWrap.getAllTags(catalogNames, yxTags, null);
		for( Map<String, Object> oneCata : allTags){
			for(Map<String, Object> tag: (List<Map<String, Object>>)oneCata.get("tags")){
				Map<String, Object> toBeAdd = new HashMap<String, Object>();
				toBeAdd.put("id", tag.get("id"));
				toBeAdd.put("catalog", oneCata.get("catalog").toString());
				toBeAdd.put("tag", tag.get("text").toString());
				ret.add(toBeAdd);
			}
		}
		return ret;
	}
	
	
	public static Map<String, Object> getBookInfo(YuexiangBook yxBook, YuexiangComment bestComment){
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("id", yxBook.getId());
		ret.put("coverImgUrl", yxBook.getBookCoverImg());
		ret.put("coverThumUrl", yxBook.getBookCoverThumImg());
		ret.put("bookName", yxBook.getBookName());
		ret.put("writer", yxBook.getBookWriter());
		ret.put("publisher", yxBook.getBookPublisher());
		ret.put("bookCode", yxBook.getBookCode());
		ret.put("bookIntroduce", yxBook.getBookDescription());
		ret.put("verbalUrl", yxBook.getVerbalUrl());
		if( bestComment == null )
			bestComment = yxBook.getRecomComment();
		ret.put("recomm", getBooksComment(bestComment, null, false, null));
		ret.put("tags", getTagsForViewOnlyType2(yxBook.getTags()));
		
		ret.put("isWarehouse", true);
	
		return ret;
	}

	public static List<Map<String,Object>> getBooksComments(List<YuexiangComment> commentList, ZaFrontUserWx wxUser, boolean isWrapChild) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		
		for( YuexiangComment comment : commentList){
			ret.add(getBooksComment(comment, wxUser, isWrapChild, yxHelper.getBestComment(comment)));
		}
		return ret;
	}
		
	public static Map<String,Object> getBooksComment(YuexiangComment comment, ZaFrontUserWx wxUser, boolean isWrapChild, YuexiangComment bestComment) {
		
		if( comment == null )
			return null;
		
		Map<String, Object> aComm = new HashMap<String, Object>();
		aComm.put("id", comment.getId());
		aComm.put("headImgUrl", comment.getCreaterUserWx().getHeadImgUrl());
		aComm.put("nickname", comment.getCreaterUserWx().getWxNickname());
		aComm.put("text", comment.getComment());
		aComm.put("comTime", nbStringUtil.DateTime2StrinF02(comment.getCommentDate()));
		aComm.put("bookId", comment.getBook() == null ? null : comment.getBook().getId());
		aComm.put("topicId", comment.getTopic() == null ? null : comment.getTopic().getId());
		
		aComm.put("isCurrentUserLiked", false);
		aComm.put("likeId", 0);
		List<YuexiangLike> yxLikes = comment.getYuexiangLikes();
		for( YuexiangLike like : yxLikes){
			if( wxUser == null ){
				aComm.put("isCurrentUserLiked", false);
				aComm.put("likeId", 0 );
			}
			else if( like.getWxUser().getId() == wxUser.getId() ){
				aComm.put("isCurrentUserLiked", true);
				aComm.put("likeId", like.getId() );
			}
		}
		aComm.put("isCurrentUserCreator", (wxUser == null ? null : (comment.getCreaterUserWx().getId()==wxUser.getId())));
		aComm.put("likedNumber", comment.getYuexiangLikes().size());
		List<String> commentPicThum  = new ArrayList<String>();
		List<String> commentPic  = new ArrayList<String>();
		for( YuexiangCommentImage img :comment.getImageList() ){
			commentPicThum.add(img.getThumUrl());
			commentPic.add(img.getImgUrl());
		}
		aComm.put("commentPicThum", commentPicThum);
		aComm.put("commentPic", commentPic);
		aComm.put("recomComment", getBooksComment(bestComment, wxUser, false, null));
		aComm.put("replyNumber", comment.getChildList().size());
		if( isWrapChild ){
			List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
			for( YuexiangComment childComment : comment.getChildList() ){
				Map<String, Object> oneChild = getBooksComment(childComment, wxUser, isWrapChild, null);
				childList.add(oneChild);
			}
			aComm.put("child", childList);
		}
		return aComm;
	}
	
	public static Map<String, Object> getBookListForAdmin(List<YuexiangBook> yxBookList, int totalNumber) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		List<Map<String, Object>> books = new ArrayList<Map<String, Object>>();
		for( YuexiangBook yxBook : yxBookList){
			Map<String, Object> oneBook = new HashMap<String, Object>();
			
			oneBook.put("id", yxBook.getId());
			oneBook.put("uploaderHeadImgUrl", yxBook.getCreaterWxUser().getHeadImgUrl());
			oneBook.put("uploaderWxOpenId", yxBook.getCreaterWxUser().getWxOpenId());
			oneBook.put("coverImgUrl", yxBook.getBookCoverImg());
			oneBook.put("bookName", yxBook.getBookName());
			oneBook.put("bookWriter", yxBook.getBookWriter());
			oneBook.put("publisherName", yxBook.getBookPublisher());
			oneBook.put("bookCode", yxBook.getBookCode());
			oneBook.put("isAdminAllowed01", yxBook.getApproval01());
			oneBook.put("isAdminAllowed02", yxBook.getApproval02());
			oneBook.put("warehouseId", yxBook.getMasterBook() == null ? null : yxBook.getMasterBook().getId());
			oneBook.put("isWarehouse", yxBook.getIsWarehouse());
			books.add(oneBook);
		}
		
		ret.put("books", books);
		ret.put("totalNumber",totalNumber);
		
		
		return ret;
	}

	
	public static Map<String, Object> getOneApprovalStatus(boolean isApproved, ZaAdminUser approver, Date approvedTime){
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("isApproved", isApproved);
		ret.put("approverId", approver == null?null:approver.getId());
		ret.put("approverName", approver==null?null:approver.getRealname());
		ret.put("approvedTime", approvedTime == null ? null : nbStringUtil.DateTime2StrinF02(approvedTime));
		return ret;
	}
	
	public static List<Map<String, Object>> getApprovalStatus(YuexiangBook yxBook) {
		List<Map<String,Object>> ret = new ArrayList<Map<String, Object>>();
		
		ret.add(getOneApprovalStatus(yxBook.getApproval01(), yxBook.getApprover01(), yxBook.getApprove01Time()));
		ret.add(getOneApprovalStatus(yxBook.getApproval02(), yxBook.getApprover02(), yxBook.getApprove02Time()));
		
		return ret;
	}

	public static Map<String, Object> getVerbalStatus(YuexiangBook yxBook) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("verbalUrl", yxBook.getVerbalUrl());
		ret.put("isVerbalOnly", yxBook.isVerbalOnly());
		
		return ret;
	}

	public static Map<String, Object> getPromptKeywords(List<YuexiangBook> yxBooks, Integer maxNumber) {
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Map<String, Object>> recomList = new ArrayList<Map<String, Object>>();
		
		int count = 0;
		for(YuexiangBook yxBook : yxBooks){
			
			if( (count++) >= maxNumber )
				break;
			
			Map<String, Object> oneBook = yxDataWrap.getBookInfo(yxBook, null);
			recomList.add(oneBook);
		}
		ret.put("recomList", recomList);
		ret.put("sameNameBookNumber", yxBooks.size());
		return ret;
	}

	
}