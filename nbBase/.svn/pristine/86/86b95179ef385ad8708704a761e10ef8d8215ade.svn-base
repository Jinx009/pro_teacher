package pengchang.main.entry.webapp.admin.REST;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.BarcodeFormat;

import nbBase.database.models.ZaAdminUser;
import nbBase.database.models.ZaFrontWxConfig;
import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.HttpWebIOHelper;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbReturn.ReturnCode;
import nbBase.service.admin.UserInfoService;
import nbBase.service.common.WxCommonService;
import pengchang.service.admin.ApplicationService;





@Controller
public class AdminRESTEntry {

	 @Autowired  
	 private UserInfoService userInfoService;
	 
	 @Autowired  
	 private ApplicationService applicationService;

	 private static final String subProjectPath = "/pengchang";
	 
	 @Autowired
	 private WxCommonService wxCommonService;
	
	@RequestMapping(value = "/admin/doGetSessionStatus") 
    public void doGetSessionStatus(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		//Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = new nbReturn();
		Map<String, Object> retData = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		ZaAdminUser user = (ZaAdminUser) session.getAttribute("currentUserInfo");
		
		if( user != null){
			retData.put("username", user.getUsername());
			retData.put("realName", user.getRealname());
			retData.put("isAuthed", true);
		}else{
			retData.put("username", null);
			retData.put("realName", null);
			retData.put("isAuthed", false);
		}
		
		nbRet.setObject(retData);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    }
	
	@RequestMapping(value = "/admin/doSaveWxConfigDetail") 
    public void doSaveWxConfigDetail(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		@SuppressWarnings({ "unchecked" })
		Map<String, Object> jsonObject = (Map<String, Object>) jsonMap.get("configure");
		
		if( jsonObject == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		HttpSession session = request.getSession();
		ZaAdminUser user = (ZaAdminUser) session.getAttribute("currentUserInfo");
		
		nbRet = 
				this.applicationService.saveWxConfigure(jsonObject);
		
			
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    }
	
	
	@RequestMapping(value = "/admin/doSaveP12BinFile") 
    public void doSaveP12BinFile(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		//Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = new nbReturn();
		
		List<FileItem> items = 
		HttpWebIOHelper.getFileItemsFromSevletRequest(request);
		
		Integer wxConfigId = HttpWebIOHelper.getFormDataIntegerParameter(items,"wxConfigId");
		
		HttpSession session = request.getSession();
		ZaAdminUser user = (ZaAdminUser) session.getAttribute("currentUserInfo");
		String targetAFileName = "/usr/local/certs/p12_"+Calendar.getInstance().getTimeInMillis()+".bin";
		
		ZaFrontWxConfig wxcon = 
		this.wxCommonService.loadZaWxConfigure(wxConfigId);
		
		if( wxcon==null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet = 
				this.wxCommonService.uploadFile(
							items,
							targetAFileName , 
							null,
							null); //p12key文件不能随便存在公开的资源目录下
		
			
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    }
	
	@RequestMapping(value = "/admin/doSaveNewPicture") 
    public void doSaveNewPicture(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		//Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = new nbReturn();
		
		List<FileItem> items = 
		HttpWebIOHelper.getFileItemsFromSevletRequest(request);
		
		Integer wxConfigId = HttpWebIOHelper.getFormDataIntegerParameter(items,"wxConfigId");
		
		HttpSession session = request.getSession();
		ZaAdminUser user = (ZaAdminUser) session.getAttribute("currentUserInfo");
		
		ZaFrontWxConfig wxcon = 
		this.wxCommonService.loadZaWxConfigure(wxConfigId);
		
		if( wxcon==null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet = 
				this.wxCommonService.uploadFile(
							items,
							null, 
							wxcon.getResourcePath(),
							wxcon.getResourceBrowsPath()); //p12key文件不能随便存在公开的资源目录下
		
			
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    }
	
	
	
	@RequestMapping(value = "/admin/doGetWxUserList") 
    public void doGetWxUserList(HttpServletResponse response,HttpServletRequest request) throws Exception{  
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Integer start = CommonHelper.getObjectInteger(jsonMap.get("start"));
		Integer pageSize = CommonHelper.getObjectInteger(jsonMap.get("pageSize"));
		
		if( pageSize == 0 )
			pageSize = 50;
		if( start < 0 )
			start = 0;
		
		HttpSession session = request.getSession();
		ZaAdminUser user = (ZaAdminUser) session.getAttribute("currentUserInfo");
		
		nbRet = 
				this.applicationService.getWxUserList(start, pageSize);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    }
	
	
	@RequestMapping(value = "/admin/doLogout") 
    public void doLogout(HttpServletResponse response,HttpServletRequest request) throws Exception{  
		nbReturn nbRet = new nbReturn();
		
		HttpSession session = request.getSession();
		ZaAdminUser user = (ZaAdminUser) session.getAttribute("currentUserInfo");
		
		if( user != null){
			session.setAttribute("currentUserInfo", null);
		}
		
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    }
	
	@RequestMapping(value = "/admin/doSendAward") 
	public void wechatSendAward(HttpServletResponse response,HttpServletRequest request) throws Exception{  
	    //创建模型跟视图，用于渲染页面。并且指定要返回的页面为home页面  
		nbReturn nbRet = new nbReturn();
		
//		nbRet = frontAppService.loadWechatConfig(
//				CommonHelper.getOpenIdFromSession(request), 
//				CommonHelper.getEventIdFromSession(request), 
//				true);
//		WechatConfigure wxCon = null;
//		if( nbRet.isSuccess() ){
//			wxCon = (WechatConfigure) nbRet.getObject();
//		}
//	
//		if( wxCon == null){
//			nbRet.setError(ReturnCode.CREATE_TOKEN_ERROR);
//		}
//		else{
//			nbRet = 
//					wxCommonService.sendAward(  "我是商户名称", 
//												"我是活动名称",
//												"我是祝福语",
//												"127.0.0.1",
//												"我是备注",
//												"o5hTUviNXw7dSavHzC1NNJd2tV7w", 
//												100,
//												1,
//												null,
//												CommonHelper.getEventIdFromSession(request),
//												wxCon);
//		}
		
	    HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	
	@RequestMapping(value = "/admin/doUpdateOneEvent") 
    public void doUpdateOneEvent(HttpServletResponse response,HttpServletRequest request) throws Exception{  
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		
		nbRet = 
				this.applicationService.updateOneEvent(jsonMap);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	
	
	@RequestMapping(value = "/admin/doGetEventList") 
    public void doGetEventList(HttpServletResponse response,HttpServletRequest request) throws Exception{  
		nbReturn nbRet = new nbReturn();
		
		nbRet = 
				this.applicationService.getEventList();
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	@RequestMapping(value = "/admin/doGetQrCodeImg") 
    public void doGetQrCodeImg(HttpServletResponse response,HttpServletRequest request) throws Exception{  
		nbReturn nbRet = new nbReturn();
		
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		String content = "";
		
		if( jsonMap != null )
			content = CommonHelper.getObjectString(jsonMap.get("content"));
		
		if(content.length() < 1)
			content = "test";
		
		System.out.println(content);
		
		// 禁止图像缓存。         
//        response.setHeader("Pragma", "no-cache");         
//        response.setHeader("Cache-Control", "no-cache");         
//        response.setDateHeader("Expires", 0);         
//        response.setContentType("image/jpeg");         
//        // 将图像输出到Servlet输出流中。         
//        ServletOutputStream sos = response.getOutputStream();         
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024*1024);
        CommonHelper.codeEncoder(baos, content, BarcodeFormat.QR_CODE, 320, 320, null);         
        byte[] array = baos.toByteArray();
        System.out.println(array.length);
        nbRet.setObject("data:image/png;base64,"+Base64.getEncoder().encodeToString(array));
        System.out.println(nbRet.getObject().toString());
        HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	@RequestMapping(value = "/admin/doGetOneEventDetail") 
    public void doGetOneEventDetail(HttpServletResponse response,HttpServletRequest request) throws Exception{  
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Integer eventId = CommonHelper.getObjectInteger(jsonMap.get("eventId"));
		
		if( eventId == 0 ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet = 
				this.applicationService.getEventDetailInformation(eventId);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	
	@RequestMapping(value = "/admin/doGetUserMenu") 
    public void doGetUserMenu(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		nbReturn nbRet = new nbReturn();
		
//		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
//		if( jsonMap.get("eventId") == null || jsonMap.get("rivalId") == null || jsonMap.get("result") == null){
//			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
//			HttpWebIOHelper.printReturnJson(nbRet, response);
//			return;
//		}
		
		HttpSession session = request.getSession();
		ZaAdminUser user = (ZaAdminUser) session.getAttribute("currentUserInfo");
		
		if( user == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);	
			return;
		}
		
		nbRet = 
				this.userInfoService.getUserMenu(user);
		
		
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    }
	
	
	@RequestMapping(value = "/admin/doGetWxConfigList") 
    public void doGetWxConfigList(HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();
		
		HttpSession session = request.getSession();
		ZaAdminUser adminUser = (ZaAdminUser) session.getAttribute("currentUserInfo");
		
		if( adminUser == null ){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			HttpWebIOHelper.printReturnJson(nbRet, response);	
			return;
		}
		
		nbRet = 
				this.applicationService.getWxConfigList(adminUser);
		
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	@RequestMapping(value = "/admin/doGetWxConfigDetail") 
    public void doGetWxConfigDetail(HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();
		
		HttpSession session = request.getSession();
		ZaAdminUser adminUser = (ZaAdminUser) session.getAttribute("currentUserInfo");
		
		if( adminUser == null ){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			HttpWebIOHelper.printReturnJson(nbRet, response);	
			return;
		}
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Integer configId = CommonHelper.getObjectInteger(jsonMap.get("configId"));
		if( configId == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);	
			return;
		}
		
		nbRet = 
				this.applicationService.getWxConfigDetail(configId);
		
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
}
