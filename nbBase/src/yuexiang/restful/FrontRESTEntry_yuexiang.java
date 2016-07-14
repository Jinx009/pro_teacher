package yuexiang.restful;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import nbBase.database.models.ZaAdminUser;
import nbBase.database.models.ZaFrontUserWx;
import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.HttpWebIOHelper;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbReturn.ReturnCode;
import nbBase.helper.common.nbStringUtil;
import nbBase.service.common.OfficeService;
import nbBase.service.common.WxCommonService;
import nbBase.service.wechat.WechatConfigure;
import yuexiang.service.YxApplicationService;

@Controller
public class FrontRESTEntry_yuexiang {
	
	@Autowired
	WxCommonService wxCommonService;
	
	@Autowired
	YxApplicationService yxApplicationService;
	
	@Autowired
	OfficeService officeService;
	
	@RequestMapping(value = "/doParseYuexiangBook") 
	public void doParseYuexiangBook (HttpServletResponse response,HttpServletRequest request){
		nbReturn nbRet = new nbReturn();
		
		Map<String, String[]> params = request.getParameterMap();
		String filename = null;
		if( params != null ){
			if( params.get("filename") != null ){
				filename = params.get("filename")[0];
			}
		}
		
		
		if( filename == null){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
			
		}
		
		WechatConfigure wxCon = null;
		nbRet = 
				this.yxApplicationService.loadYuexiangWxConfig();
		if( nbRet.isSuccess() ){
			wxCon = (WechatConfigure) nbRet.getObject();
		}
		
		List<List<String>> dataMatrix = 
				officeService.getAllLines(officeService.loadSheet(officeService.loadWorkbook(wxCon.resourcePath+"/yuexiang_books/"+filename), 0));
		
		nbRet = 
		this.yxApplicationService.batchUpdateYuexiangBookData(dataMatrix);
				
		if( nbRet.isSuccess() ){
			CommonHelper.renameFile(wxCon.resourcePath+"/yuexiang_books/"+filename, wxCon.resourcePath+"/yuexiang_books/"+filename+".used");
		}
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	@Transactional
	@RequestMapping(value = "/doFetchYuexiangBook") 
	public void doFetchYuexiangBook (HttpServletResponse response,HttpServletRequest request){
		nbReturn nbRet = new nbReturn();
		WechatConfigure wxCon = null;
		nbRet = 
				this.yxApplicationService.loadYuexiangWxConfig();
		if( nbRet.isSuccess() ){
			wxCon = (WechatConfigure) nbRet.getObject();
		}
		
		nbRet = 
				this.yxApplicationService.getYuexiangBookData();
		
		List<List<String>> dataMatrix = (List<List<String>>) nbRet.getObject();
		
		nbRet = 
				this.officeService.writeDataToFile(dataMatrix, 
						wxCon.resourcePath+"/yuexiang_books/" + nbStringUtil.DateTime2String(Calendar.getInstance().getTime())+".xls");
		
		if( nbRet.isSuccess() ){
			String localPath = (String) nbRet.getObject();
			Map<String, String> ret = new HashMap<String, String>();
	        ret.put("localPath", localPath);
	        String[] tmp = localPath.replace("\\", "/").split("/");
	        String fileName = tmp[tmp.length-1];
	        ret.put("url", wxCon.resourceBrowsPath+"/yuexiang_books/"+fileName);
	        nbRet.setObject(ret);
		}
		
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	
	
	@RequestMapping(value = "/doGetBookTags") 
	public void doGetBookTags (HttpServletResponse response,HttpServletRequest request){
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Boolean isIncludeAdmin = HttpWebIOHelper.getJSONBoolean(jsonMap, "isIncludeAdmin", false);
		@SuppressWarnings("unchecked")
		List<Integer> selected = (List<Integer>) HttpWebIOHelper.getJSONList(jsonMap, "selectedTags", null);
		
		WechatConfigure wxCon = null;
		nbRet = yxApplicationService.loadYuexiangWxConfig();
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		wxCon = (WechatConfigure) nbRet.getObject(); 
		if( wxCon != null){
			
			nbRet = 
					yxApplicationService.getAllTags(isIncludeAdmin, selected);
			
		}
		else{
			nbRet.setError(ReturnCode.WECHAT_CONFIG_LOAD_ERROR);
		}
				
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	@Transactional
	@RequestMapping(value = "/doGetBooks") 
	public void doGetBooks (HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Integer startIndex = HttpWebIOHelper.getJSONInteger(jsonMap, "startIndex", 0);
		Integer pageSize = HttpWebIOHelper.getJSONInteger(jsonMap, "pageSize", 30);
		@SuppressWarnings("unchecked")
		List<Integer> tagFilterList = (List<Integer>) HttpWebIOHelper.getJSONList(jsonMap, "tagFilterList", null);
		String searchKeyWord = HttpWebIOHelper.getJSONString(jsonMap, "searchKeyWord", null);
		Boolean isVerbalOnly = HttpWebIOHelper.getJSONBoolean(jsonMap, "isVerbalOnly", false);
		Boolean forceVerbalTag = HttpWebIOHelper.getJSONBoolean(jsonMap, "forceVerbalTag", false);
		
		if(tagFilterList != null && tagFilterList.size() == 0)
			tagFilterList = null;
		if( searchKeyWord != null && searchKeyWord.length() == 0 )
			searchKeyWord = null;
		
			nbRet = 
					yxApplicationService.getBooksForListView(startIndex, pageSize, tagFilterList, searchKeyWord, isVerbalOnly, forceVerbalTag);
			
				
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	@Transactional
	@RequestMapping(value = "/doGetBooksComments") 
	public void doGetBooksComments (HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Integer startIndex = HttpWebIOHelper.getJSONInteger(jsonMap, "startIndex", 0);
		Integer pageSize = HttpWebIOHelper.getJSONInteger(jsonMap, "pageSize", 30);
		Integer bookId = HttpWebIOHelper.getJSONInteger(jsonMap, "bookId", null);
		Boolean isIncludeChild  = HttpWebIOHelper.getJSONBoolean(jsonMap, "isIncludeChild", false);
		ZaFrontUserWx wxUser = this.wxCommonService.loadWxUserByOpenId(CommonHelper.getOpenIdFromSession(request));
		
		wxUser = this.wxCommonService.fakeAWxUser(wxUser);

			
			nbRet = 
					yxApplicationService.getBooksComments(bookId, startIndex, pageSize, wxUser ,isIncludeChild);
			
				
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	@Transactional
	@RequestMapping(value = "/doGetVerbalArticles") 
	public void doGetVerbalArticles (HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		
//		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
//		Integer startIndex = HttpWebIOHelper.getJSONInteger(jsonMap, "startIndex", 0);
//		Integer pageSize = HttpWebIOHelper.getJSONInteger(jsonMap, "pageSize", 30);
//		Integer bookId = HttpWebIOHelper.getJSONInteger(jsonMap, "bookId", null);
//		Boolean isIncludeChild  = HttpWebIOHelper.getJSONBoolean(jsonMap, "isIncludeChild", false);
//		ZaFrontUserWx wxUser = this.wxCommonService.loadWxUserByOpenId(CommonHelper.getOpenIdFromSession(request));
//		
//		wxUser = this.wxCommonService.fakeAWxUser(wxUser);

			
			nbRet = 
					yxApplicationService.getVerbalAritcles();
			
				
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	
	@Transactional
	@RequestMapping(value = "/doGetTopicComments") 
	public void doGetTopicComments (HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Integer startIndex = HttpWebIOHelper.getJSONInteger(jsonMap, "startIndex", 0);
		Integer pageSize = HttpWebIOHelper.getJSONInteger(jsonMap, "pageSize", 30);
		Integer topicId = HttpWebIOHelper.getJSONInteger(jsonMap, "topicId", null);
		Boolean isPublic  = HttpWebIOHelper.getJSONBoolean(jsonMap, "isPublic", true);
		Boolean isIncludeChild  = HttpWebIOHelper.getJSONBoolean(jsonMap, "isIncludeChild", false);
		ZaFrontUserWx wxUser = this.wxCommonService.loadWxUserByOpenId(CommonHelper.getOpenIdFromSession(request));
		
		wxUser = this.wxCommonService.fakeAWxUser(wxUser);
			
		nbRet = 
				yxApplicationService.getTopicComments(topicId, startIndex, pageSize, wxUser ,isIncludeChild, isPublic);
			
				
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	@Transactional
	@RequestMapping(value = "/doGetBookCommentInfo") 
	public void doGetBookCommentInfo (HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Integer commentId = HttpWebIOHelper.getJSONInteger(jsonMap, "commentId", null);
		Boolean isIncludeChild  = HttpWebIOHelper.getJSONBoolean(jsonMap, "isIncludeChild", false);
		
		
		ZaFrontUserWx wxUser = this.wxCommonService.loadWxUserByOpenId(CommonHelper.getOpenIdFromSession(request));
		wxUser = this.wxCommonService.fakeAWxUser(wxUser);
			
			nbRet = 
					yxApplicationService.getBookCommentInfo(commentId, wxUser, isIncludeChild);
			
				
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	@Transactional
	@RequestMapping(value = "/doGetPromptKeywords") 
    public void doGetPromptKeywords(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		ZaAdminUser adminUser = CommonHelper.getAdminUserFromSession(request);
		String inputedKeywords = HttpWebIOHelper.getJSONString(jsonMap, "inputedKeywords", null);
		Integer maxNumber = HttpWebIOHelper.getJSONInteger(jsonMap, "maxNumber", 5);
		Boolean isVerbalOnly = HttpWebIOHelper.getJSONBoolean(jsonMap, "isVerbalOnly", null);
		Boolean isWarehouse = HttpWebIOHelper.getJSONBoolean(jsonMap, "isWarehouse", null);
		Boolean forceVerbalTag = HttpWebIOHelper.getJSONBoolean(jsonMap, "forceVerbalTag", null);
			nbRet = 
				this.yxApplicationService.getPromptKeywords(inputedKeywords, maxNumber, isWarehouse, isVerbalOnly, forceVerbalTag);
		
			
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    }
	
	@Transactional
	@RequestMapping(value = "/doGetBookInfo") 
	public void doGetBookInfo (HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Integer bookId = HttpWebIOHelper.getJSONInteger(jsonMap, "bookId", null);
		
			nbRet = 
					yxApplicationService.getBookInfo(bookId);
			
				
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	@Transactional
	@RequestMapping(value = "/doDeleteComment") 
	public void doDeleteComment (HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Integer bookId = HttpWebIOHelper.getJSONInteger(jsonMap, "bookId", null);
		Integer topicId = HttpWebIOHelper.getJSONInteger(jsonMap, "topicId", null);
		Integer parentCommentId = HttpWebIOHelper.getJSONInteger(jsonMap, "parentCommentId", null);
		Integer commentId = HttpWebIOHelper.getJSONInteger(jsonMap, "commentId", null);
		ZaFrontUserWx wxUser = this.wxCommonService.loadWxUserByOpenId(CommonHelper.getOpenIdFromSession(request));
		ZaAdminUser adminUser = CommonHelper.getAdminUserFromSession(request);
		
		nbRet = 
				yxApplicationService.deleteComment(bookId, topicId, parentCommentId, commentId, wxUser, adminUser);
			
				
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	@Transactional
	@RequestMapping(value = "/doCreateNewComment") 
	public void doCreateNewComment (HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		
		WechatConfigure wxCon = null;
		nbRet = yxApplicationService.loadYuexiangWxConfig();
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		wxCon = (WechatConfigure) nbRet.getObject(); 
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Integer bookId = HttpWebIOHelper.getJSONInteger(jsonMap, "bookId", null);
		Integer topicId = HttpWebIOHelper.getJSONInteger(jsonMap, "topicId", null);
		Integer parentCommentId = HttpWebIOHelper.getJSONInteger(jsonMap, "parentCommentId", null);
		@SuppressWarnings("unchecked")
		List<String> picList = (List<String>) HttpWebIOHelper.getJSONList(jsonMap, "picList", new ArrayList<String>());
		String commentText = HttpWebIOHelper.getJSONString(jsonMap, "commentText", null);
		ZaFrontUserWx wxUser = this.wxCommonService.loadWxUserByOpenId(CommonHelper.getOpenIdFromSession(request));
		wxUser = this.wxCommonService.fakeAWxUser(wxUser);
		if(wxUser == null ){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		ZaAdminUser adminUser = CommonHelper.getAdminUserFromSession(request);
		
		List<Map<String, Object>> localPicList = this.wxCommonService.saveWxImgToLocalServer(picList, wxCon);
		
		nbRet = 
				yxApplicationService.createNewComment(bookId, topicId, parentCommentId, localPicList, commentText, wxUser, adminUser);
			
				
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	
	@Transactional
	@RequestMapping(value = "/doLikeAComment") 
	public void doLikeAComment(HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Integer commentId = HttpWebIOHelper.getJSONInteger(jsonMap, "commentId", null);
		Boolean isUnlike = HttpWebIOHelper.getJSONBoolean(jsonMap, "isUnlike", false);
		ZaFrontUserWx wxUser = this.wxCommonService.loadWxUserByOpenId(CommonHelper.getOpenIdFromSession(request));
		wxUser = this.wxCommonService.fakeAWxUser(wxUser);
		if(wxUser == null ){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			HttpWebIOHelper.printReturnJson(nbRet, response);	
			return;
		}
		
		nbRet = 
				yxApplicationService.likeAComment(commentId, isUnlike, wxUser);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	
	
	@Transactional
	@RequestMapping(value = "/doGetUserPointsInfo") 
	public void doGetUserPointsInfo(HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		ZaFrontUserWx wxUser = this.wxCommonService.loadWxUserByOpenId(CommonHelper.getOpenIdFromSession(request));
		wxUser = this.wxCommonService.fakeAWxUser(wxUser);
		if(wxUser == null ){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			HttpWebIOHelper.printReturnJson(nbRet, response);	
			return;
		}
		
		nbRet = 
				yxApplicationService.getUserPointsInfo(wxUser);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	
	@Transactional
	@RequestMapping(value = "/doCreateNewBookPost") 
	public void doCreateNewBookPost (HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> bookInfo = HttpWebIOHelper.servletInputStream2JsonMap(request);
		
		String openId = CommonHelper.getOpenIdFromSession(request);
		ZaFrontUserWx wxUser = this.wxCommonService.loadWxUserByOpenId(openId);
		ZaAdminUser adminUser = CommonHelper.getAdminUserFromSession(request);
		
		wxUser = this.wxCommonService.fakeAWxUser(wxUser);
		if( wxUser == null ){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		WechatConfigure wxCon = null;
		nbRet = yxApplicationService.loadYuexiangWxConfig();
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		wxCon = (WechatConfigure) nbRet.getObject(); 
		if( wxCon != null){
			
			nbRet = 
					yxApplicationService.saveNewBookShare(bookInfo, wxUser, adminUser, wxCon);
			
		}
		else{
			nbRet.setError(ReturnCode.WECHAT_CONFIG_LOAD_ERROR);
		}
				
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}

}
