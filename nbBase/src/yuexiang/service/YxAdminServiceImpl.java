package yuexiang.service;


import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nbBase.dao.ZaAdminUserDao;
import nbBase.dao.ZaUserWxDao;
import nbBase.database.common.OrderFilter.OrderType;
import nbBase.database.common.QueryParam;
import nbBase.database.models.ZaAdminUser;
import nbBase.database.models.ZaFrontUserWx;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbReturn.ReturnCode;
import nbBase.service.common.WxCommonService;
import yuexiang.dao.YxBookDao;
import yuexiang.dao.YxCommentImageDao;
import yuexiang.dao.YxCommentsDao;
import yuexiang.dao.YxLikesDao;
import yuexiang.dao.YxTagsDao;
import yuexiang.dao.YxTopicDao;
import yuexiang.database.models.YuexiangBook;
import yuexiang.database.models.YuexiangComment;

@Service("yxAdminService")
public class YxAdminServiceImpl implements YxAdminService {

	
	@Autowired
	YxBookDao yxBookDao;
	
	@Autowired
	YxCommentImageDao yxCommentImageDao;
	
	@Autowired
	YxCommentsDao yxCommentsDao;
	
	@Autowired
	YxTagsDao yxTagsDao;
	
	@Autowired
	YxLikesDao yxLikesDao;
	
	@Autowired
	YxTopicDao yxTopicDao;
	
	@Autowired
	ZaUserWxDao userWxDao;
	
	@Autowired
	ZaAdminUserDao adminUserDao;
	
	@Autowired
	WxCommonService wxCommonService;

	@Override
	public nbReturn getBookListForAdmin(Integer startIndex, Integer pageSize, Boolean isWarehouse, Boolean isVerbalOnly) {
		nbReturn nbRet = new nbReturn();
			QueryParam param = new QueryParam();
			if( isVerbalOnly == null || !isVerbalOnly){//isVerbalOnly == true 也就是只显示官方出版的有声读物的时候，不check isWarehouse
				param.addParam("isWarehouse", isWarehouse);
			}
			if( isVerbalOnly != null){
				param.addParam("isVerbalOnly", isVerbalOnly);
			}		
//				param.addParam("verbalUrl", Operators.NOTEQ, SearchFilter.NULL);
			param.addOrder(OrderType.DESC, "id");
			List<YuexiangBook> yxBookList = this.yxBookDao.findByCriteria(param, startIndex, pageSize);
			int totalNumber = this.yxBookDao.countByCriteria(param);
			nbRet.setObject(yxDataWrap.getBookListForAdmin(yxBookList, totalNumber));
			
		return nbRet;
	}

	@Override
	public nbReturn getApprovalStatus(Integer bookId) {
		nbReturn nbRet = new nbReturn();
		
		YuexiangBook yxBook = this.yxBookDao.find(bookId);
		nbRet.setObject(yxDataWrap.getApprovalStatus(yxBook));
		
		return nbRet;
	}

	@Override
	public nbReturn approve(Integer bookId, Integer approvelIndex, Boolean isUnApprove, ZaAdminUser adminUser) {
		nbReturn nbRet = new nbReturn();
		
		YuexiangBook yxBook = this.yxBookDao.find(bookId);

		if( isUnApprove ){
			ZaAdminUser oldApprover = (approvelIndex == 1 ? yxBook.getApprover01() : yxBook.getApprover02());
			if( oldApprover != null && oldApprover.getId() == adminUser.getId() ){
				switch(approvelIndex){
				case 1:
					yxBook.setApproval01(false);
					yxBook.setApprover01(null);
					yxBook.setApprove01Time(null);
					yxBook = this.yxBookDao.update(yxBook);
					break;
				case 2:
					yxBook.setApproval02(false);
					yxBook.setApprover02(null);
					yxBook.setApprove02Time(null);
					yxBook = this.yxBookDao.update(yxBook);
					break;
				}
			}else{
				nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
				return nbRet;
			}
		}else{
			switch(approvelIndex){
			case 1:
				if( yxBook.getApproval01() )
					break;
				yxBook.setApproval01(true);
				yxBook.setApprover01(adminUser);
				yxBook.setApprove01Time(Calendar.getInstance().getTime());
				yxBook = this.yxBookDao.update(yxBook);
				break;
			case 2:
				if( yxBook.getApproval02() )
					break;
				yxBook.setApproval02(true);
				yxBook.setApprover02(adminUser);
				yxBook.setApprove02Time(Calendar.getInstance().getTime());
				yxBook = this.yxBookDao.update(yxBook);
				break;
			}
		}
		
		if( yxBook.getApproval01() && yxBook.getApproval02()){
			yxBook.setIsWarehouse(true);
			//nbRet = copyUserFeedToWarehouse(yxBook.getId(), false);
		}else{
			yxBook.setIsWarehouse(false);
		}
		
		yxBook = this.yxBookDao.update(yxBook);
		nbRet.setObject(yxDataWrap.getApprovalStatus(yxBook));
		
		return nbRet;
	}

	@Override
	public nbReturn setBestComment(Integer bookId, Integer parentCommentId, Integer commentId, Boolean isIncludeChild) {
		nbReturn nbRet = new nbReturn();
		
		YuexiangBook yxBook = this.yxBookDao.find(bookId);
		YuexiangComment parentComment = this.yxCommentsDao.find(parentCommentId);
		YuexiangComment comment = this.yxCommentsDao.find(commentId);
		
		if( parentComment == null ){
			yxBook.setRecomComment(comment);
			yxBook = this.yxBookDao.update(yxBook);
		}else{
			parentComment.setBestComment(comment);
			parentComment = this.yxCommentsDao.update(parentComment);
		}
		return nbRet;
	}

	@Override
	public nbReturn addVerbalToBook(Integer bookId, String verbalUrl) {
		nbReturn nbRet = new nbReturn();
		
		YuexiangBook yxBook = this.yxBookDao.find(bookId);
		
		if( yxBook == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		if( verbalUrl != null && verbalUrl.length() < 2 )
			verbalUrl = null;
		yxBook.setVerbalUrl(verbalUrl);
		if( verbalUrl != null )
			yxBook.addTag(this.yxTagsDao.findByName("有声"));
		else
			yxBook.removeTag(this.yxTagsDao.findByName("有声"));
		yxBook = this.yxBookDao.update(yxBook);
		
		nbRet.setObject(yxDataWrap.getVerbalStatus(yxBook));
		
		return nbRet;
	}

	@Override
	public nbReturn getVerbalStatus(Integer bookId) {
		nbReturn nbRet = new nbReturn();
		
		YuexiangBook yxBook = this.yxBookDao.find(bookId);
		if( yxBook  == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		nbRet.setObject(yxDataWrap.getVerbalStatus(yxBook));
		
		return nbRet;
	}

	@Override
	public nbReturn setWxUserExpert(Integer wxUserId, ZaAdminUser adminUser, Boolean isExpert) {
		nbReturn nbRet = new nbReturn();
		
		ZaFrontUserWx wxUser = this.userWxDao.find(wxUserId);
		if( wxUser == null){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		if(adminUser == null){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			return nbRet;
		}
		
		wxUser.setAttrIsExpert(isExpert);
		wxUser = this.userWxDao.update(wxUser);
		
		nbRet.setObject(wxUser);
		
		return nbRet;
	}
	
//	@Override
//	public nbReturn copyUserFeedToWarehouse(Integer bookUserFeedId, Boolean isOverwriteReferredItem) {
//		nbReturn nbRet = new nbReturn();
//		
//		YuexiangBook yxBookUF= this.yxBookDao.find(bookUserFeedId);
//		
//		if( yxBookUF == null ){
//			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
//			return nbRet;
//		}
//		
//		YuexiangBook masterBook = yxBookUF.getMasterBook();
//		
//		if( masterBook != null && !isOverwriteReferredItem ){
//			nbRet.setError(ReturnCode.TARGET_ALREADY_EXIST);
//			return nbRet;
//		}
//		
//		if( masterBook == null ){
//			masterBook = new YuexiangBook();
//			masterBook.setIsDeleted(false);
//			masterBook.setCreateDate(Calendar.getInstance().getTime());
//			masterBook.setIsWarehouse(true);
//		}
//		
//		
//		masterBook.setRecomComment(null);
//		masterBook.setBookCode(yxBookUF.getBookCode());
//		masterBook.setBookCoverImg(yxBookUF.getBookCoverImg());
//		masterBook.setBookCoverThumImg(yxBookUF.getBookCoverThumImg());
//		masterBook.setBookDescription(yxBookUF.getBookDescription());
//		masterBook.setBookName(yxBookUF.getBookName());
//		masterBook.setBookWriter(yxBookUF.getBookWriter());
//		masterBook.setBookPublisher(yxBookUF.getBookPublisher());
//		masterBook.setTags(this.yxTagsDao.findIn(yxBookUF.getTags()));
//		
//		masterBook.addComment(this.yxCommentsDao.clone(YuexiangComment.class, yxBookUF.getComments()));
//		masterBook.setAdminUser(yxBookUF.getAdminUser());
//		masterBook.setCreaterWxUser(yxBookUF.getCreaterWxUser());
//		masterBook.setVerbalUrl(yxBookUF.getVerbalUrl());
//		masterBook.setApproval01(false);
//		masterBook.setApproval02(false);
//		
//		if( masterBook.getId() == 0 )
//			masterBook = this.yxBookDao.save(masterBook);
//		else
//			masterBook = this.yxBookDao.update(masterBook);
//		
//		yxBookUF.setMasterBook(masterBook);
//		
//		yxBookUF = this.yxBookDao.update(yxBookUF);
//		
//		nbRet.setObject(masterBook);
//		
//		return nbRet;
//	}

}
