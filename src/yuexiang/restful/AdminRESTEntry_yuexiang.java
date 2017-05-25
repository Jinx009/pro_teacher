package yuexiang.restful;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import nbBase.database.models.ZaAdminUser;
import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.HttpWebIOHelper;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbReturn.ReturnCode;
import nbBase.service.common.WxCommonService;
import yuexiang.service.YxAdminService;
import yuexiang.service.YxApplicationService;





@Controller
public class AdminRESTEntry_yuexiang {


	 
	 @Autowired  
	 private YxAdminService yxAdminService;
	 
	 @Autowired  
	 private YxApplicationService yxApplicationService;

	 
	 @Autowired
	 private WxCommonService wxCommonService;
	
	
	@RequestMapping(value = "/admin/doGetBookList") 
    public void doGetBookList(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		ZaAdminUser adminUser = CommonHelper.getAdminUserFromSession(request);
		Integer startIndex = HttpWebIOHelper.getJSONInteger(jsonMap, "startIndex", 0);
		Integer pageSize = HttpWebIOHelper.getJSONInteger(jsonMap, "pageSize", 0);
		Boolean isWarehouse = HttpWebIOHelper.getJSONBoolean(jsonMap, "isWarehouse", true);
		Boolean isVerbalOnly = HttpWebIOHelper.getJSONBoolean(jsonMap, "isVerbalOnly", null);
		
			nbRet = 
				this.yxAdminService.getBookListForAdmin(startIndex, pageSize, isWarehouse, isVerbalOnly);
		
			
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    } 
	
	@Transactional
	@RequestMapping(value = "/admin/doAddVerbalToBook") 
    public void doAddVerbalToBook(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		ZaAdminUser adminUser = CommonHelper.getAdminUserFromSession(request);
		Integer bookId = HttpWebIOHelper.getJSONInteger(jsonMap, "bookId", null);
		String verbalUrl = HttpWebIOHelper.getJSONString(jsonMap, "verbalUrl", null);
		//String reader = HttpWebIOHelper.getJSONString(jsonMap, "reader", null);
		
		if( verbalUrl == null || bookId == null){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		
			nbRet = 
				this.yxAdminService.addVerbalToBook(bookId, verbalUrl);
		
			
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    } 
	
	
	@RequestMapping(value = "/admin/doGetApprovalStatus") 
    public void doGetApprovalStatus(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		ZaAdminUser adminUser = CommonHelper.getAdminUserFromSession(request);
		Integer bookId = HttpWebIOHelper.getJSONInteger(jsonMap, "bookId", null);
			nbRet = 
				this.yxAdminService.getApprovalStatus(bookId);
		
			
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    }
	
	@Transactional
	@RequestMapping(value = "/admin/doSetWxUserExpert") 
    public void doSetWxUserExpert(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		ZaAdminUser adminUser = CommonHelper.getAdminUserFromSession(request);
		Integer wxUserId = HttpWebIOHelper.getJSONInteger(jsonMap, "wxUserId", null);
		Boolean isExpert = HttpWebIOHelper.getJSONBoolean(jsonMap, "isExpert", false);
			nbRet = 
				this.yxAdminService.setWxUserExpert(wxUserId, adminUser, isExpert);
		
			
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    } 
	
	@RequestMapping(value = "/admin/doGetVerbalStatus") 
    public void doGetVerbalStatus(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		ZaAdminUser adminUser = CommonHelper.getAdminUserFromSession(request);
		Integer bookId = HttpWebIOHelper.getJSONInteger(jsonMap, "bookId", null);
			nbRet = 
				this.yxAdminService.getVerbalStatus(bookId);
		
			
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    } 
	
	
	@Transactional
	@RequestMapping(value = "/admin/doApprove") 
    public void doApprove(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		ZaAdminUser adminUser = CommonHelper.getAdminUserFromSession(request);
		Integer bookId = HttpWebIOHelper.getJSONInteger(jsonMap, "bookId", null);
		Integer approvelIndex = HttpWebIOHelper.getJSONInteger(jsonMap, "approvelIndex", null);
		Boolean isUnApprove = HttpWebIOHelper.getJSONBoolean(jsonMap, "isUnApprove", false);
			nbRet = 
				this.yxAdminService.approve(bookId, approvelIndex, isUnApprove, adminUser);
		
			
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    } 
	
	@Transactional
	@RequestMapping(value = "/admin/doSetBestComment") 
	public void doSetBestComment (HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Integer bookId = HttpWebIOHelper.getJSONInteger(jsonMap, "bookId", null);
		Integer parentCommentId = HttpWebIOHelper.getJSONInteger(jsonMap, "parentCommentId", null);
		Integer commentId = HttpWebIOHelper.getJSONInteger(jsonMap, "commentId", null);
		Boolean isIncludeChild  = HttpWebIOHelper.getJSONBoolean(jsonMap, "isIncludeChild", false);
			
			nbRet = 
					yxAdminService.setBestComment(bookId, parentCommentId, commentId, isIncludeChild);
			
				
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
//	@Transactional
//	@RequestMapping(value = "/admin/doCopyUserFeedToWarehouse") 
//	public void doCopyUserFeedToWarehouse (HttpServletResponse response,HttpServletRequest request) throws Exception{
//		nbReturn nbRet = new nbReturn();
//		
//		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
//		Integer bookUserFeedId = HttpWebIOHelper.getJSONInteger(jsonMap, "bookUserFeedId", null);
//		Boolean isForceCopy = HttpWebIOHelper.getJSONBoolean(jsonMap, "isForceCopy", false);
//		
//		nbRet = 
//				yxAdminService.copyUserFeedToWarehouse(bookUserFeedId, isForceCopy);
//			
//				
//		HttpWebIOHelper.printReturnJson(nbRet, response);	
//	}
	
	
}
