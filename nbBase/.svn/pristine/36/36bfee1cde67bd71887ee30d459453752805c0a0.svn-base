package yuexiang.service;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nbBase.dao.ZaAdminUserDao;
import nbBase.dao.ZaUserWxDao;
import nbBase.database.common.OrderFilter.OrderType;
import nbBase.database.common.QueryParam;
import nbBase.database.common.SearchFilter;
import nbBase.database.models.ZaAdminUser;
import nbBase.database.models.ZaFrontUserWx;
import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.HttpWebIOHelper;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbReturn.ReturnCode;
import nbBase.helper.common.nbStringUtil;
import nbBase.service.common.WxCommonService;
import nbBase.service.wechat.WechatConfigure;
import yuexiang.dao.YxBookDao;
import yuexiang.dao.YxCommentImageDao;
import yuexiang.dao.YxCommentsDao;
import yuexiang.dao.YxLikesDao;
import yuexiang.dao.YxTagsDao;
import yuexiang.dao.YxTopicDao;
import yuexiang.dao.YxVerbalArticleDao;
import yuexiang.database.models.YuexiangBook;
import yuexiang.database.models.YuexiangComment;
import yuexiang.database.models.YuexiangCommentImage;
import yuexiang.database.models.YuexiangLike;
import yuexiang.database.models.YuexiangTag;
import yuexiang.database.models.YuexiangTopic;

@Service("yxApplicationService")
public class YxApplicationServiceImpl implements YxApplicationService {
	
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
	
	@Autowired
	YxVerbalArticleDao yxVerbalArticleDao;

	@Override
	public nbReturn loadYuexiangWxConfig() {
		nbReturn nbRet = new nbReturn();
		
		String appId = CommonHelper.loadAppSpecifiedConfig("wxAppId");
		if( appId == null ){
			nbRet.setError(ReturnCode.APP_CONFIG_KEY_NOT_FOUND);
			return nbRet;
		}
		
		WechatConfigure wxCon = 
				wxCommonService.loadWxConfigureByAppId(appId);
		
		if( wxCon == null ){
			nbRet.setError(ReturnCode.WECHAT_CONFIG_LOAD_ERROR);
			return nbRet;
		}
		
		nbRet.setObject(wxCon);
		
		return nbRet;
	}

	@Override
	public nbReturn getAllTags(Boolean isIncludeAdminTypeTags, List<Integer> selected) {
		nbReturn nbRet = new nbReturn();
		
		QueryParam param = new QueryParam();
		if( isIncludeAdminTypeTags != null && isIncludeAdminTypeTags == false)
			param.addParam("isAdminOnly", false);
		
		nbRet.setObject(
			yxDataWrap.getAllTags(
					this.yxTagsDao.findCatalogList(), 
					this.yxTagsDao.findByCriteria(param), 
					selected));
		
		return nbRet;
	}

	@Override
	public nbReturn saveNewBookShare(Map<String, Object> bookInfo, ZaFrontUserWx wxUser, ZaAdminUser adminUser, WechatConfigure wxCon) {
		
		nbReturn nbRet = new nbReturn();
		Integer bookId = HttpWebIOHelper.getJSONInteger(bookInfo,"bookId", null);
		String bookCode = HttpWebIOHelper.getJSONString(bookInfo, "bookCode", "N/A");
		Integer referBookId = HttpWebIOHelper.getJSONInteger(bookInfo,"referBookId", null);
		String coverImgUrl = HttpWebIOHelper.getJSONString(bookInfo, "coverImgUrl", "");
		String bookName = HttpWebIOHelper.getJSONString(bookInfo, "bookName", "??");
		String writer = HttpWebIOHelper.getJSONString(bookInfo, "writer", "未知");
		String publisher = HttpWebIOHelper.getJSONString(bookInfo, "publisher", "未知");
		String verbalUrl = HttpWebIOHelper.getJSONString(bookInfo, "verbalUrl", null);
		@SuppressWarnings("unchecked")
		List<Integer> tagsId = (List<Integer>) HttpWebIOHelper.getJSONList(bookInfo, "tagsId", new ArrayList<Integer>());
		String bookIntroduce = HttpWebIOHelper.getJSONString(bookInfo, "bookIntroduce", "无"); 
		String myComText = HttpWebIOHelper.getJSONString(bookInfo, "myComText", "");
		@SuppressWarnings("unchecked")
		List<String> myComPic = (List<String>) HttpWebIOHelper.getJSONList(bookInfo, "myComPic", new ArrayList<String>());
		Boolean isVerbalOnly = HttpWebIOHelper.getJSONBoolean(bookInfo, "isVerbalOnly", false);
		
		String[] coverImgUrlSet = null;
		
		if( coverImgUrl.toLowerCase().startsWith("http") ){
			coverImgUrlSet = new String[2];
			int i = coverImgUrl.lastIndexOf(".");
			coverImgUrlSet[1] = coverImgUrl;
			coverImgUrlSet[0] = coverImgUrl.substring(0, i) + "_thum" + coverImgUrl.substring(i, coverImgUrl.length());
		}else{
			//把图片文件从微信上拉去到本地服务器上
			try {
				coverImgUrlSet = 
						this.wxCommonService.saveWxImgToLocalServer(coverImgUrl, wxCon);
			} catch (Exception e) {
				e.printStackTrace();
				nbRet.setError(ReturnCode.GET_FILE_FROM_WX_ERROR);
				return nbRet;
			}
		}
		
		//把tags释放出来
		List<YuexiangTag> yxTags = new ArrayList<YuexiangTag>();
		for(Integer tagId : tagsId){
			YuexiangTag oneTag = this.yxTagsDao.find(tagId);
			if( oneTag != null){
				yxTags.add(oneTag);
			}
		}
		
		YuexiangBook yxBook = null;
		yxBook = this.yxBookDao.find(bookId);
		if( yxBook == null ){
			yxBook = new YuexiangBook();
			yxBook.setCreateDate(Calendar.getInstance().getTime());
			yxBook.setCreaterWxUser(wxUser);
			yxBook.setAdminUser(adminUser);
			yxBook.setTags(yxTags);
			if( isVerbalOnly && 
				!nbStringUtil.isNullEmpty(verbalUrl) )
			{
				yxBook.addTag(this.yxTagsDao.findByName("有声"));
			}
			yxBook.setApproval01(false);
			yxBook.setApproval02(false);
			yxBook.setIsWarehouse( (isVerbalOnly != null && isVerbalOnly) );
		}
		yxBook.setVerbalOnly(isVerbalOnly);
		yxBook.setBookCode(bookCode);
		
		if( coverImgUrlSet != null ){
			yxBook.setBookCoverImg(coverImgUrlSet[1]);
			yxBook.setBookCoverThumImg(coverImgUrlSet[0]);
		}
		
		yxBook.setBookDescription(bookIntroduce);
		yxBook.setBookName(bookName);
		yxBook.setBookPublisher(publisher);
		yxBook.setBookWriter(writer);
		yxBook.setMasterBook(this.yxBookDao.find(referBookId));
		yxBook.setVerbalUrl(verbalUrl);
		
		if( yxBook.getId() > 0 ){//如果是update的话，只update主要信息部分，comment相关部分不参与update。
			yxBook = this.yxBookDao.update(yxBook);
			nbRet.setObject(yxBook);
			return nbRet;
		}
		
		{
			yxBook.setApproval01(true);
			yxBook.setApproval02(true);
			yxBook.setApprove01Time(Calendar.getInstance().getTime());
			yxBook.setApprove02Time(Calendar.getInstance().getTime());
			yxBook.setApprover01(this.adminUserDao.find(1));
			yxBook.setApprover02(this.adminUserDao.find(1));
			yxBook.setIsWarehouse(true);
		}
		
		yxBook = this.yxBookDao.save(yxBook);	

		YuexiangComment yxComment = new YuexiangComment();
		yxComment.setBestComment(null);
		yxComment.setChildList(null);
		yxComment.setComment(myComText);
		yxComment.setCommentDate(Calendar.getInstance().getTime());
		yxComment.setCreaterOperator(null);
		yxComment.setCreaterUserWx(wxUser);
		yxComment.setImageList(null);
		yxComment.setIsDeleted(false);
		yxComment.setYuexiangLikes(null);
		yxComment.setParent(null);
		yxComment.setBook(yxBook);
		
		yxComment = this.yxCommentsDao.save(yxComment);
		List<YuexiangComment> comments = new ArrayList<YuexiangComment>();
		comments.add(yxComment);
		
		
		List<YuexiangCommentImage> yxComImgList = new ArrayList<YuexiangCommentImage>();
		
		//把图片文件从微信上拉去到本地服务器上
		try {
			for( String comPic : myComPic ){
				String[] ret = this.wxCommonService.saveWxImgToLocalServer(comPic, wxCon);
				YuexiangCommentImage yxComImg = new YuexiangCommentImage();
				yxComImg.setThumUrl(ret[0]);
				yxComImg.setImgUrl(ret[1]);
				yxComImg.setParentComment(yxComment);
				yxComImgList.add(yxComImg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			nbRet.setError(ReturnCode.GET_FILE_FROM_WX_ERROR);
			return nbRet;
		}
		
		this.yxCommentImageDao.save(yxComImgList);
		
		//yxBook.setRecomComment(yxComment);
		yxBook.setComments(comments);
		yxBook = this.yxBookDao.update(yxBook);
		
		nbRet.setObject(yxBook);
		
		return nbRet;
	}

	@Override
	public nbReturn getBooksForListView(Integer startIndex, Integer pageSize, List<Integer> tagFilterList,
			String searchKeyWord, Boolean isVerbalOnly, Boolean forceVerbalTag) {
		nbReturn nbRet = new nbReturn();
		
		if( startIndex == null ) startIndex = 0;
		if( pageSize == null ) pageSize = 120;
		
		//List<YuexiangBooksWarehouse> yxBooksWarehouseList = this.yxBooksWarehouseDao.findByCriteria(param, startIndex, pageSize);
		if( (isVerbalOnly != null && isVerbalOnly) || forceVerbalTag){
			//这里开始，保证在tag的filter中有"有声【读物】"的标签，由此来帮助过滤
			YuexiangTag verbalTag = this.yxTagsDao.findByName("有声");
			if( tagFilterList == null ) tagFilterList = new ArrayList<Integer>();
			for( Integer id : tagFilterList ){
				if( id == verbalTag.getId()){
					verbalTag = null;
					break;
				}
			}
			if( verbalTag != null ){
				tagFilterList.add(verbalTag.getId());
			}
			isVerbalOnly = null;
		}
		Long totalNumber = 0l;
		@SuppressWarnings("unchecked")
		List<YuexiangBook> yxBookList = (List<YuexiangBook>)
				this.yxBookDao.findByCertainConditions(startIndex, pageSize, tagFilterList, searchKeyWord, true, isVerbalOnly, false);
		totalNumber = (Long)
				this.yxBookDao.findByCertainConditions(startIndex, pageSize, tagFilterList, searchKeyWord, true, isVerbalOnly, true);
		
				//this.yxBookDao.findByCertainConditions(startIndex, pageSize, tagFilterList, searchKeyWord, true, isVerbalOnly);
		
		nbRet.setObject(
				yxDataWrap.getBooksForListView(yxBookList, totalNumber) );
		
		return nbRet;
	}


	@Override
	public nbReturn createNewComment(Integer bookId, Integer topicId, Integer parentCommentId, List<Map<String, Object>> picList,
			String commentText, ZaFrontUserWx wxUser, ZaAdminUser operatorUser) {
		
		nbReturn nbRet = new nbReturn();
		
		YuexiangBook yxBook = this.yxBookDao.find(bookId);
		YuexiangTopic yxTopic = this.yxTopicDao.find(topicId);
		YuexiangComment parentComment = this.yxCommentsDao.find(parentCommentId);
		if( parentComment != null ){
			yxBook = null;
			yxTopic = null;
		}
		YuexiangComment newComment = new YuexiangComment();
		newComment.setBestComment(null);
		newComment.setBook(yxBook);
		newComment.setTopic(yxTopic);
		newComment.setChildList(null);
		newComment.setComment(commentText);
		newComment.setCommentDate(Calendar.getInstance().getTime());
		newComment.setCreaterUserWx(wxUser);
		newComment.setCreaterOperator(operatorUser);
		newComment.setIsDeleted(false);
		newComment.setYuexiangLikes(null);
		newComment.setParent(parentComment);
		newComment.setImageList(null);
		newComment = this.yxCommentsDao.save(newComment);
		
		List<YuexiangCommentImage> commentImages = this.yxCommentImageDao.save(picList, newComment);
		
		newComment.setImageList(commentImages);
		newComment = this.yxCommentsDao.update(newComment);
		
		nbRet.setObject(yxDataWrap.getBooksComment(newComment, wxUser , true, null));
		
		return nbRet;
	}

	@Override
	public nbReturn likeAComment(Integer commentId, Boolean isUnlike, ZaFrontUserWx wxUser) {
		nbReturn nbRet = new nbReturn();
		YuexiangComment yxComment = this.yxCommentsDao.find(commentId);
		if( yxComment == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		if( wxUser == null ){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			return nbRet;
		}
		
		boolean changed = false;
		if(!isUnlike){
			YuexiangLike yxLike = this.yxLikesDao.findByCommentAndWxUser(yxComment, wxUser);
			if( yxLike == null){
				yxLike = new YuexiangLike();
				yxLike.setWxUser(wxUser);
				yxLike.setYuexiangComment(yxComment);
				yxLike = this.yxLikesDao.save(yxLike);
				//yxComment.addYuexiangLike(yxLike);
				//yxComment = this.yxCommentsDao.update(yxComment);
				changed = true;
			}
		}else{
			YuexiangLike yxLike = this.yxLikesDao.findByCommentAndWxUser(yxComment, wxUser);
			if(yxLike != null){
				this.yxLikesDao.delete(yxLike.getId());
				yxComment.removeYuexiangLike(yxLike);
				changed = true;
			}

		}
		
		int likes = yxComment.getYuexiangLikes().size();
		Map<String, Object> retData = new HashMap<String, Object>();
		retData.put("commentId", yxComment.getId());
		retData.put("likes", likes);
		retData.put("currentUserLiked", !isUnlike);
		retData.put("newRecomComment", yxDataWrap.getBooksComment(yxComment.getParent() == null ? null : yxHelper.getBestComment(yxComment.getParent()), wxUser, false, null));
		retData.put("newBookComment", yxDataWrap.getBooksComment(yxComment.getBook() == null ? null : yxHelper.getBestCommentByLikes(yxComment.getBook().getComments()), wxUser, false, null));
		nbRet.setObject(retData);
		return nbRet;
	}

	@Override
	public nbReturn getBookInfo(Integer bookId) {
		nbReturn nbRet = new nbReturn();
		
		YuexiangBook yxBook = this.yxBookDao.find(bookId);
		if( yxBook == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		nbRet.setObject(yxDataWrap.getBookInfo(yxBook,
				yxBook.getRecomComment() == null ? yxHelper.getBestCommentByLikes(yxBook.getComments()) : null ));
		
		return nbRet;
	}

	@Override
	public nbReturn getBooksComments(Integer bookId, Integer startIndex, Integer pageSize, ZaFrontUserWx wxUser , Boolean isIncludeChild) {
		nbReturn nbRet = new nbReturn();
		
		if( startIndex == null ) startIndex = 0;
		if( pageSize == null ) pageSize = 50;
		
		List<YuexiangComment> commentList = this.yxBookDao.find(bookId).getComments();
		//this.yxCommentsDao.findByBookId(bookId, startIndex, pageSize);
		
		nbRet.setObject(yxDataWrap.getBooksComments(commentList, wxUser, isIncludeChild));
		
		return nbRet;
	}

	@Override
	public nbReturn getBookCommentInfo(Integer commentId, ZaFrontUserWx wxUser, Boolean isIncludeChild) {
		nbReturn nbRet = new nbReturn();
		
		YuexiangComment comment = this.yxCommentsDao.find(commentId);
		
		nbRet.setObject(yxDataWrap.getBooksComment(comment,wxUser, isIncludeChild, yxHelper.getBestComment(comment)));
		
		return nbRet;
	}
	

	@Override
	public nbReturn deleteComment(Integer bookId, Integer topicId, Integer parentCommentId, Integer commentId, ZaFrontUserWx wxUser, ZaAdminUser adminUser) {
		nbReturn nbRet = new nbReturn();
		
		YuexiangComment comment = this.yxCommentsDao.find(commentId);
//		YuexiangBook yxBook = this.yxBookDao.find(bookId);
//		YuexiangComment parentComment = this.yxCommentsDao.find(parentCommentId);
		comment.setIsDeleted(true);
		comment = this.yxCommentsDao.update(comment);
		
		YuexiangBook yxBook = this.yxBookDao.find(bookId);
		YuexiangTopic yxTopic = this.yxTopicDao.find(topicId);
		
		if( yxBook != null ){
			if(yxBook.getRecomComment() != null && yxBook.getRecomComment().getId() == comment.getId() ){
				yxBook.setRecomComment(null);
				yxBook = this.yxBookDao.update(yxBook);
			}
			nbRet.setObject(yxDataWrap.getBooksComments(yxBook.getComments(), wxUser, false));
			return nbRet;
		}
		
		YuexiangComment parentComment = this.yxCommentsDao.find(parentCommentId);
		if( parentComment != null){
			if( parentComment.getBestComment() != null && parentComment.getBestComment().getId() == comment.getId() ){
				parentComment.setBestComment(null);
				parentComment = this.yxCommentsDao.update(parentComment);
			}
			nbRet.setObject(yxDataWrap.getBooksComment(parentComment, wxUser, true, null));
			return nbRet;
		}
		
		
		
//		
//		if( (wxUser != null && comment.getCreaterUserWx().getId() == wxUser.getId() ) ||
//			(adminUser != null)
//			){
//			
//			if( yxBook != null ){
//				yxBook.getComments().remove(comment);
//				if( yxBook.getRecomComment().getId() == comment.getId()){
//					yxBook.setRecomComment(null);
//				}
//				yxBook = this.yxBookDao.update(yxBook);
//			}
//			if( parentComment != null){
//				parentComment.getChildList().remove(comment);
//				if( parentComment.getBestComment().getId() == comment.getId() ){
//					parentComment.setBestComment(null);
//				}
//				parentComment = this.yxCommentsDao.update(parentComment);
//			}
//			this.yxCommentsDao.delete(comment);
//			
//		}
		//nbRet.setObject(yxDataWrap.getBooksComment(comment,wxUser, isIncludeChild));
		
		return nbRet;
	}

	@Override
	public nbReturn getPromptKeywords(String inputedKeywords, Integer maxNumber, Boolean isWarehouse, Boolean isVerbalOnly, Boolean forceVerbalTag) {
		nbReturn nbRet = new nbReturn();
		
		if( inputedKeywords == null){
			return nbRet;
		}
		
		QueryParam qp = new QueryParam();
		if( forceVerbalTag!=null )
			qp.addParam("verbalUrl", SearchFilter.Operators.NOTEQ, SearchFilter.NULL);
		if( isWarehouse != null){
			qp.addParam("isWarehouse", isWarehouse);
		}
		if( isVerbalOnly != null ){
			qp.addParam("isVerbalOnly", isVerbalOnly);
		}
		qp.addParam("bookName", SearchFilter.Operators.LIKE, inputedKeywords);
		qp.addParam("isWarehouse", true);
		qp.addOrder(OrderType.DESC, "id");
		List<YuexiangBook> yxBooks = this.yxBookDao.findByCriteria(qp);
//		List<YuexiangBook> yxBooks = this.yxBookDao.findByMyCriteria(inputedKeywords,isWarehouse, isVerbalOnly, forceVerbalTag);
		
		nbRet.setObject(yxDataWrap.getPromptKeywords(yxBooks, maxNumber));
		return nbRet;
	}

	@Override
	public nbReturn getTopicComments(Integer topicId, Integer startIndex, Integer pageSize, ZaFrontUserWx wxUser,
			Boolean isIncludeChild, Boolean isPublic) {
		nbReturn nbRet = new nbReturn();
		
		YuexiangTopic topic = this.yxTopicDao.find(topicId);
		if( !isPublic && wxUser == null ){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			return nbRet;
		}
		
		List<YuexiangComment> commentList = this.yxCommentsDao.findByMyCriteria01(topic, startIndex, pageSize, wxUser, isPublic);
		
		nbRet.setObject(yxDataWrap.getBooksComments(commentList, wxUser, isIncludeChild));
		
		return nbRet;
	}

	@Override
	public nbReturn getUserPointsInfo(ZaFrontUserWx wxUser) {
		nbReturn nbRet = new nbReturn();
		Map<String, Object> ret = new HashMap<String, Object>();
		//推荐分享一本书10分，推荐分享闲置书籍15分
		QueryParam qpBook = new QueryParam();
		qpBook.addParam("isWarehouse", true);
		qpBook.addParam("isVerbalOnly", false);
		qpBook.addParam("createrWxUser", wxUser);
//		qpBook.addParam("book", SearchFilter.Operators.NOTEQ, SearchFilter.NULL);
//		qpBook.addParam("book", SearchFilter.Operators.EQ, SearchFilter.NULL);
		List<YuexiangBook> sharedBookList = this.yxBookDao.findByCriteria(qpBook);
		
		int sharedPoints = 0;
		int sharedWithSecondHandPoints = 0;
		YuexiangTag secondHandTag = this.yxTagsDao.findByName("二手");
		
		for( YuexiangBook book : sharedBookList ){
			if( book.getTags().indexOf(secondHandTag) >= 0 ){
				sharedWithSecondHandPoints += 15; //推荐分享闲置书籍15分
			}else{
				sharedPoints += 10; //推荐分享一本书10分
			}
		}
		
		//评论一条2分
		QueryParam qpComment = new QueryParam();
		qpComment.addParam("isDeleted", false);
		qpComment.addParam("parent.isDeleted", false);
//		qpComment.addParam("book", SearchFilter.Operators.NOTEQ, SearchFilter.NULL);
//		qpComment.addParam("topic", SearchFilter.NULL);
		qpComment.addParam("createrUserWx", wxUser);
		int commentedPoints = this.yxCommentsDao.countByCriteria(qpComment) * 2;
		
		//获一个赞1分
		int gotLikesPoints = this.yxCommentsDao.countGotLikes(wxUser)*1;
		
		ret.put("sharedWithSecondHandPoints", sharedWithSecondHandPoints);
		ret.put("sharedPoints", sharedPoints);
		ret.put("commentedPoints", commentedPoints);
		ret.put("gotLikesPoints", gotLikesPoints);
		ret.put("points", sharedWithSecondHandPoints+sharedPoints+commentedPoints+gotLikesPoints);
		
		nbRet.setObject(ret);
		return nbRet;
	}

	@Override
	public nbReturn getVerbalAritcles() {
		nbReturn nbRet = new nbReturn();
		
		QueryParam qp = new QueryParam();
		qp.addOrder(OrderType.DESC, "id");
		nbRet.setObject(this.yxVerbalArticleDao.findByCriteria(qp));
		
		return nbRet;
	}

	@Override
	public nbReturn batchUpdateYuexiangBookData(List<List<String>> dataMatrix) {
		nbReturn nbRet = new nbReturn();
		
		WechatConfigure wxCon = (WechatConfigure) this.loadYuexiangWxConfig().getObject();
		
		for( List<String> row : dataMatrix){
			
			if( row.get(0) != null && !StringUtils.isNumeric(row.get(0)) ){
				continue;
			}
			
			Integer id = row.get(0) == null ? null : Integer.valueOf(row.get(0));
			String bookName = row.get(1);
			String writer = row.get(2);
			String publisher = row.get(3);
			String code = row.get(4);
			String introduce = row.get(5);
			String verbalUrl = row.get(6);
			Boolean isVerbalOnly = row.get(7) == null ? false : (Integer.valueOf(row.get(7)) == 1 ? true : false);
			String[] tags = row.get(8) == null ? null : row.get(8).split(";");
			
			
			List<Integer> tagsIds = new ArrayList<Integer>();
			if( tags != null ){
				for( int i = 0 ; i < tags.length ; i++){
					tagsIds.add(Integer.valueOf(tags[i]));
				}
			}
			
			YuexiangBook book = new YuexiangBook();
			
			if( id != null && id != 0  ){
				book = this.yxBookDao.find(id);
			}else{
				book.setAdminUser(this.adminUserDao.find(1));
				book.setApproval01(true);
				book.setApproval02(true);
				book.setApprove01Time(Calendar.getInstance().getTime());
				book.setApprove02Time(Calendar.getInstance().getTime());
				book.setApprover01(this.adminUserDao.find(1));
				book.setApprover02(this.adminUserDao.find(1));
				book.setCreateDate(Calendar.getInstance().getTime());
				book.setCreaterWxUser(this.userWxDao.find(1));
				book.setIsDeleted(false);
				book.setIsWarehouse(true);
				book.setMasterBook(null);
				book.setRecomComment(null);
			}
			
			book.setBookName(bookName);
			book.setBookWriter(writer);
			book.setBookPublisher(publisher);
			String coverImgName = bookName;
			
			try {
				coverImgName = URLEncoder.encode(bookName, "utf8").replace("+", "%20");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			book.setBookCoverImg(wxCon.resourceBrowsPath + "/yuexiang_cover_img/"+ coverImgName + ".jpg");
			book.setBookCoverThumImg(wxCon.resourceBrowsPath + "/yuexiang_cover_img/"+ coverImgName + ".jpg");
			
			if( CommonHelper.resizeImageFromBytes(
					wxCon.resourcePath+"/yuexiang_cover_img_thum/"+bookName+".jpg", 
					"jpeg", 
					CommonHelper.loadBytesFromFile(wxCon.resourcePath+"/yuexiang_cover_img/" + bookName + ".jpg"),
					320,
					320) ){
				book.setBookCoverThumImg(wxCon.resourceBrowsPath+"/yuexiang_cover_img_thum/"+coverImgName+".jpg");
			}
			book.setBookCode(code==null?"n/a":code);
			book.setBookDescription(introduce);
			book.setVerbalUrl(verbalUrl);
			book.setVerbalOnly(isVerbalOnly);
			
			List<YuexiangTag> yxTags = this.yxTagsDao.findInIds(tagsIds);
			book.setTags(yxTags);
				
			
			if( book.getId() != 0)
				book = this.yxBookDao.update(book);
			else
				book = this.yxBookDao.save(book);
			
		}
		
		return nbRet;
	}

	@Override
	public nbReturn getYuexiangBookData() {
		nbReturn nbRet = new nbReturn();
		
		List<YuexiangBook> books = this.yxBookDao.findAll();
		
		List<List<String>> dataMatrix = new ArrayList<List<String>>();
		
		List<String> title = new ArrayList<String>();
		title.add("id(顺序)");
		title.add("书名");
		title.add("作者");
		title.add("出版社");
		title.add("图书编码");
		title.add("图书的内容概要");
		title.add("有声图书的话，有声内容的URL");
		title.add("是否阅享提供的有声图书，是为1，不是为0");
		title.add("标签");
		dataMatrix.add(title);

		
		for( YuexiangBook book : books ){
			List<String> row = new ArrayList<String>();
			row.add(String.valueOf(book.getId()));
			row.add(book.getBookName());
			row.add(book.getBookWriter());
			row.add(book.getBookPublisher());
			row.add(book.getBookCode());
			row.add(book.getBookDescription());
			row.add(book.getVerbalUrl());
			row.add(book.isVerbalOnly() ? "1" : "0");
			List<YuexiangTag> tags = book.getTags();
			String tagString = "";
			for( YuexiangTag tag : tags){
				tagString = tagString +  tag.getId() + ";";
			}
			if( tagString.endsWith(";") ){
				tagString = tagString.substring(0, tagString.length()-1);
			}
			row.add(tagString);
			
			dataMatrix.add(row);
		}
		
		nbRet.setObject(dataMatrix);
		
		return nbRet;
		
	}
}
