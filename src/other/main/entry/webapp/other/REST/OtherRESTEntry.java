package other.main.entry.webapp.other.REST;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
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
import nbBase.helper.common.nbStringUtil;
import nbBase.service.common.WxCommonService;
import nbBase.service.front.UserInfoService;
import nbBase.service.wechat.WechatConfigure;
import nbBase.service.wechat.WechatPageSDK;
import other.helper.other.casioEventHelper;
import other.service.other.OtherApplicationService;





@Controller
public class OtherRESTEntry {
	 
	 @Autowired
	 private UserInfoService frontUserService;


	 
	 @Autowired
	 private WxCommonService wxCommonService;
	 
	 @Autowired
	 private OtherApplicationService otherApplicationService;
	 
	
	 
	 /**
		 * 前端页面调用这个api获取需要的微信jssdk参数进行装填，如果session中有已经有个用户信息，返回值会带上所有信息返回给前端
		 * @param response
		 * @param request
		 * @throws Exception
		 */
		@RequestMapping(value = "/other/doGetFrontSessionInfo") 
		public void doGetFrontSessionInfo (HttpServletResponse response,HttpServletRequest request) throws Exception{
			
			nbReturn nbRet = new nbReturn();
			Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
			String url = (String) jsonMap.get("url");
			
			HttpSession session = request.getSession();
			@SuppressWarnings("unchecked")
			Map<String, Object> sessionTokenUserInfo = (Map<String, Object>) (session.getAttribute("currentWXUserinfo"));
			Map<String, Object> sessionJSSDK = null;
			
			WechatConfigure wxCon = casioEventHelper.prepareWxConfig();
			
			nbRet = frontUserService.getJSSign(
									url,
									wxCon);
			if( nbRet.isSuccess() )
			{
				WechatPageSDK jsSDK = (WechatPageSDK) nbRet.getObject();
				sessionJSSDK = jsSDK.getValueMap();
			}
			
			
			if( sessionTokenUserInfo != null ){
				sessionJSSDK.putAll(sessionTokenUserInfo);
			}
			
			nbRet.setError(ReturnCode._SUCCESS, sessionJSSDK);
			
			HttpWebIOHelper.printReturnJson(nbRet, response);	
		} 
	 
	@RequestMapping(value = "/other/doGetSessionStatus") 
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
	
	
	@RequestMapping(value = "/other/doSaveP12BinFile") 
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
	
	@RequestMapping(value = "/other/doSaveNewPicture") 
    public void doSaveNewPicture(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		//Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = new nbReturn();
		
		List<FileItem> items = 
		HttpWebIOHelper.getFileItemsFromSevletRequest(request);
		
//		Integer wxConfigId = HttpWebIOHelper.getFormDataIntegerParameter(items,"wxConfigId");
//		
//		HttpSession session = request.getSession();
//		ZaAdminUser user = (ZaAdminUser) session.getAttribute("currentUserInfo");
//		
//		ZaFrontWxConfig wxcon = 
//		this.wxCommonService.loadZaWxConfigure(wxConfigId);
//		
//		if( wxcon==null ){
//			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
//			HttpWebIOHelper.printReturnJson(nbRet, response);
//			return;
//		}
//		
//		nbRet = 
//				this.wxCommonService.uploadFile(
//							items,
//							null, 
//							wxcon.getResourcePath(),
//							wxcon.getResourceBrowsPath()); //p12key文件不能随便存在公开的资源目录下
//		
//			
//		HttpWebIOHelper.printReturnJson(nbRet, response);	
    }

	
	@RequestMapping(value = "/other/doSaveNewPictureWx") 
    public void doSaveNewPictureWx(HttpServletResponse response,HttpServletRequest request) throws Exception{  
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		String imageId = jsonMap.get("imgId").toString()+CommonHelper.getIpAddress(request).replace(".", "");
		Integer imageNumber = CommonHelper.getObjectInteger(jsonMap.get("imgNumber"));
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> picList = (List<Map<String, Object>>) jsonMap.get("imgData");
		
		List<String> wxImgs = new ArrayList<String>();
		
		for( Map<String, Object> pic : picList){
			wxImgs.add(pic.get("wx").toString());
		}
		
		WechatConfigure wxcon = casioEventHelper.prepareWxConfig();
		
		if( wxImgs.size() == 2){
			nbRet = casioEventHelper.mergeImgFiles(
					casioEventHelper.getFileFromWxServer(wxImgs.get(0), wxcon), 
					casioEventHelper.getFileFromWxServer(wxImgs.get(1), wxcon),  
					"/usr/local/www/pc-res.0angle.com/casio/"+imageId+".jpg");
		}
		
		if( nbRet.isSuccess() ){
			nbRet.setObject(imageId);
			HttpWebIOHelper.printReturnJson(nbRet, response);
		}	
    }

	@RequestMapping(value = "/other/doSaveNewPictureBase64") 
    public void doSaveNewPictureBase64(HttpServletResponse response,HttpServletRequest request) throws Exception{  
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		String imageId = jsonMap.get("imgId").toString()+CommonHelper.getIpAddress(request).replace(".", "");
		Integer imageNumber = CommonHelper.getObjectInteger(jsonMap.get("imgNumber"));
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> picList = (List<Map<String, Object>>) jsonMap.get("imgData");
		
		List<byte[]> byteList = new ArrayList<byte[]>();
		
		for( Map<String, Object> pic : picList){
			String base64String = pic.get("base64").toString();
			try{
				String[] infos = base64String.split(":|;|,"); 
				String type = infos[1].split("/")[1];
				byte[] bytes = nbStringUtil.decryptBASE64(infos[3]);
				byteList.add(bytes);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if( byteList.size() == 2){
			nbRet = casioEventHelper.mergeImgFiles(byteList.get(0), byteList.get(1), "/usr/local/www/pc-res.0angle.com/casio/"+imageId+".jpg");
		}
		
		if( nbRet.isSuccess() ){
			nbRet.setObject(imageId);
			HttpWebIOHelper.printReturnJson(nbRet, response);
		}	
    }
	
	
	@RequestMapping(value = "/other/doSaveCasio001EventStatus") 
    public void doSaveCasio001EventStatus(HttpServletResponse response,HttpServletRequest request) throws Exception{  
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Date currentDate = Calendar.getInstance().getTime();
		Integer client = CommonHelper.getObjectInteger(jsonMap.get("client"));//0 weibo 1 weixin
		String picUrl = CommonHelper.getObjectString(jsonMap.get("picUrl"));
		Boolean weiboShared = CommonHelper.getObjectBoolean(jsonMap.get("weiboShared"), false);		
		
		nbRet = 
		this.otherApplicationService.createNewStatusRecord(
				currentDate,
				client,
				picUrl,
				weiboShared
				);
		
		
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    }
	
	@RequestMapping(value = "/other/doUpdateWeixinShared") 
    public void doUpdateWeixinShared(HttpServletResponse response,HttpServletRequest request) throws Exception{  
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		String picUrl = CommonHelper.getObjectString(jsonMap.get("picUrl"));
		Boolean weixinShared = CommonHelper.getObjectBoolean(jsonMap.get("weixinShared"), false);		
		
		nbRet = 
		this.otherApplicationService.updateWeixinShared(
				picUrl,
				weixinShared
				);
		
		
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    }
	
	@RequestMapping(value = "/other/doUpdateWeixinSharedResult") 
    public void doUpdateWeixinSharedResult(HttpServletResponse response,HttpServletRequest request) throws Exception{  
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		String picUrl = CommonHelper.getObjectString(jsonMap.get("picUrl"));
		Boolean weixinSharedResult = CommonHelper.getObjectBoolean(jsonMap.get("weixinSharedResult"), false);		
		
		nbRet = 
		this.otherApplicationService.updateWeixinSharedResult(
				picUrl,
				weixinSharedResult
				);
		
		
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    }
	
	
	
	
	
	
	@RequestMapping(value = "/other/doGetQrCodeImg") 
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
	
}
