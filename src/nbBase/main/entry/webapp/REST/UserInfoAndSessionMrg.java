package nbBase.main.entry.webapp.REST;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import nbBase.data.vo.VerifyCodePictureVo;
import nbBase.database.models.ZaSmsSend;
import nbBase.database.models.ZaUser;
import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.HttpWebIOHelper;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbReturn.ReturnCode;
import nbBase.helper.common.nbStringUtil;
import nbBase.service.common.DataWrapUtil;
import nbBase.service.front.UserInfoService;




@Controller
public class UserInfoAndSessionMrg {

	@Autowired  
	private UserInfoService userInfoService;
	
	@RequestMapping(value = "/doLogin") 
    public void doLogin(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		nbReturn nbRet = new nbReturn();
		HttpSession session = request.getSession();
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		
		
		String clientUUID = (String)jsonMap.get("clientUUID");
		if ( clientUUID == null ) clientUUID = request.getRemoteAddr();
		
		String appId =  (String)jsonMap.get("appId");
		if(  appId == null ) appId = "syhAppID";
		
		String pictureVerifyCode = (String)jsonMap.get("pictureVerifyCode");
		nbRet = CommonHelper.checkVerifyCodePicture(pictureVerifyCode, session, true);
		
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet = userInfoService.verifyUser((String)jsonMap.get("username"),
				                           (String)jsonMap.get("password"),
				                           appId,
				                           clientUUID);
		
		if( nbRet.isSuccess() ){
			CommonHelper.setCurrentUserInfoFromSession(request, (ZaUser)nbRet.getObject());
		}
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
		
    }
	
	@RequestMapping(value = "/doGetSessionStatus") 
    public void doGetSessionStatus(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		//Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = new nbReturn();
		
		ZaUser user = CommonHelper.getCurrentUserInfoFromSession(request);
		
		nbRet.setObject(DataWrapUtil.wrapUserInfo(user));
		
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    }
	
	@RequestMapping(value = "/doLogout") 
    public void doLogout(HttpServletResponse response,HttpServletRequest request) throws Exception{  
		nbReturn nbRet = new nbReturn();
		
		ZaUser user = CommonHelper.getCurrentUserInfoFromSession(request);
		
		if( user != null){
			CommonHelper.setCurrentUserInfoFromSession(request, null);
		}
		
		HttpWebIOHelper.printReturnJson(nbRet, response);	
    }
	
	@RequestMapping(value = "/doRegisterUser") 
    public void doRegisterUser(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = new nbReturn();
		
//		var userData = { realname:$scope.realname, 
//		         username:$scope.username, 
//		         password:$scope.password, 
//		         confirmPassword:$scope.confirmPassword, 
//		         phoneCode:$scope.phoneCode, 
//		         verifyCode:$scope.verifyCode};
		//检查clientUUID
		String clientUUID = (String)jsonMap.get("clientUUID");
		if ( clientUUID == null ) clientUUID = request.getRemoteAddr();
		
		//检查用户传来的appID
		String appId =  (String)jsonMap.get("appId");
		if(  appId == null ) appId = "syhAppID";
		
		//获取其他的相关参数
		String verifyCode = (String)jsonMap.get("verifyCode");
		String phoneCode = (String)jsonMap.get("phoneCode");
		Integer phoneCodeId = (Integer) jsonMap.get("phoneCodeId");
		String realname = (String)jsonMap.get("realname");
		String username = (String)jsonMap.get("username");
		String password = (String)jsonMap.get("password");
		String confirmPassword = (String)jsonMap.get("confirmPassword");
		String email = (String)jsonMap.get("email");
		String mobile = (String)jsonMap.get("mobile");
		
		//检查密码是否符合要求
		nbRet = CommonHelper.verifyPasswordFormat(password, confirmPassword);
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet = CommonHelper.checkVerifyCodePicture(verifyCode, request, true);
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet = userInfoService.checkPhoneCode(phoneCodeId, phoneCode);
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		
		nbRet = userInfoService.RegisterUser(username, password, mobile, email, appId, realname);
		if( nbRet.isSuccess() ){
			nbRet.setObject(DataWrapUtil.wrapUserInfo((ZaUser)nbRet.getObject()));
		}
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
		
    }
	
	@RequestMapping(value="/doGetVerifyCodePicture")
    public void doGetVerifyCodePicture(HttpServletResponse response,HttpServletRequest request) throws IOException{
				
		nbReturn nbRet = new nbReturn();
		nbRet =  CommonHelper.pharseGetParameters(request.getQueryString());
		
		@SuppressWarnings("unchecked")
		Map<String, Object> paramMap = (Map<String, Object>)nbRet.getObject();
		
		Long verifyCodeWidth = 72l;
		Long verifyCodeHeight = 25l;
		
		if( paramMap != null && paramMap.get("width") != null){
			verifyCodeWidth = Long.valueOf((String)paramMap.get("width"));
			if( verifyCodeWidth == null)
				verifyCodeWidth = 72l;
		}
		
		if( paramMap != null && paramMap.get("height") != null){
			verifyCodeHeight = Long.valueOf((String)paramMap.get("height"));
			if( verifyCodeHeight == null)
				verifyCodeHeight = 25l;
		}
		
		
		nbRet = CommonHelper.createVerifyCodePicture(verifyCodeWidth, verifyCodeHeight);
		if( nbRet.isSuccess() ){
			VerifyCodePictureVo verifyCodePictureVo = (VerifyCodePictureVo) nbRet.getObject();
			
		
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, 15);
			Date dateTime = cal.getTime();
			
			// 将四位数字的验证码保存到Session中。
	        HttpSession session = request.getSession();        
	        session.setAttribute("verifyCode", verifyCodePictureVo.getVerifyCode());
	        session.setAttribute("verifyCodeExpireTime", dateTime);
	        
	        // 禁止图像缓存。         
	        response.setHeader("Pragma", "no-cache");         
	        response.setHeader("Cache-Control", "no-cache");         
	        response.setDateHeader("Expires", 0);         
	        response.setContentType("image/jpeg");         
	        // 将图像输出到Servlet输出流中。         
	        ServletOutputStream sos = response.getOutputStream();         
	        ImageIO.write(verifyCodePictureVo.getBuffImg(), "jpeg", sos);         
	        sos.close();  
		}
    }         
	
	@RequestMapping(value="/doGetVerifyCode")
    public void doGetVerifyCode(HttpServletResponse response,HttpServletRequest request) throws IOException{
				
		nbReturn nbRet = new nbReturn();
		HttpSession session = request.getSession();        
        String verifyCode = (String) session.getAttribute("verifyCode");
        Date expireTime = (Date) session.getAttribute("verifyCodeExpireTime");
        
        if( verifyCode == null){
        	nbRet.setError(ReturnCode.VERIFYCODE_WRONG);
        	HttpWebIOHelper.printReturnJson(nbRet, response);
        	return;
        }
        
        Calendar cal = Calendar.getInstance();
        Date currentTime = cal.getTime();
        
        if( expireTime.before(currentTime)){
        	nbRet.setError(ReturnCode.VERIFYCODE_EXPIRED);
        	HttpWebIOHelper.printReturnJson(nbRet, response);
        	return;
        }
		
		nbRet.setObject(verifyCode);
		HttpWebIOHelper.printReturnJson(nbRet, response);
    } 
	
	@RequestMapping(value="/doSendPhoneCode")
    public void doSendPhoneCode(HttpServletResponse response,HttpServletRequest request) throws Exception{
		

		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = new nbReturn();

		ZaUser user = CommonHelper.getCurrentUserInfoFromSession(request);
		
//		var userData = { realname:$scope.realname, 
//		         username:$scope.username, 
//		         password:$scope.password, 
//		         confirmPassword:$scope.confirmPassword, 
//		         phoneCode:$scope.phoneCode, 
//		         verifyCode:$scope.verifyCode};

		//检查clientUUID
		String clientUUID = (String)jsonMap.get("clientUUID");
		if ( clientUUID == null ) clientUUID = request.getRemoteAddr();
		
		//检查用户传来的appID
		String appId =  (String)jsonMap.get("appId");
		if(  appId == null ) appId = "syhAppID";
		
		//获取其他的相关参数
		String verifyCode = (String)jsonMap.get("verifyCode");
		String username = (String)jsonMap.get("username");

		String sendReasonCommnets = (String)jsonMap.get("sendReasonCommnets");
		

		if( sendReasonCommnets == null) sendReasonCommnets = "UNKOWN";
        
        nbRet = CommonHelper.checkVerifyCodePicture(verifyCode, request, false);
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
        nbRet = userInfoService.sendPhoneCode(appId, username, sendReasonCommnets);
        
        if(nbRet.isSuccess()){
        	ZaSmsSend smsSend = (ZaSmsSend) nbRet.getObject();
        	
        	Calendar resendTime = Calendar.getInstance();
        	resendTime.setTime(smsSend.getLatestEventTime());
        	resendTime.add(Calendar.SECOND, smsSend.getContinouseTryCycle());
        	
        	Calendar expireTime = Calendar.getInstance();
        	expireTime.setTime(smsSend.getRequestedTime());
        	expireTime.add(Calendar.SECOND, smsSend.getLifecycle());
        	
    		Map<String, Object> retMap = new HashMap<String, Object>();
    		retMap.put("phoneCheckCode", nbStringUtil.encryptMD5WithoutBase64(smsSend.getPhoneCode()));
    		retMap.put("phoneCheckCodeId", smsSend.getId());
    		retMap.put("resendTime", nbStringUtil.DateTime2String(resendTime.getTime()));
    		retMap.put("expireTime", nbStringUtil.DateTime2String(expireTime.getTime()));
    		retMap.put("allowCountDown", smsSend.getContinouseTryCycle());
    		
    		nbRet.setObject(retMap);
        }
        else{
        	nbRet.setObject(null);
        }
        
		HttpWebIOHelper.printReturnJson(nbRet, response);
    }
	
	@RequestMapping(value = "/doResetPassword") 
    public void doResetPassword(HttpServletResponse response,HttpServletRequest request) throws Exception{  

		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = new nbReturn();
		
		
//		var userData = { username:$scope.username, 
//		         password:$scope.password, 
//		         confirmPassword:$scope.confirmPassword, 
//		         phoneCode:$scope.phoneCode, 
//		         verifyCode:$scope.verifyCode};

		//检查clientUUID
		String clientUUID = (String)jsonMap.get("clientUUID");
		if ( clientUUID == null ) clientUUID = request.getRemoteAddr();
		
		//检查用户传来的appID
		String appId =  (String)jsonMap.get("appId");
		if(  appId == null ) appId = "syhAppID";
		
		//获取其他的相关参数
		String verifyCode = (String)jsonMap.get("verifyCode");
		String phoneCode = (String)jsonMap.get("phoneCode");
		Integer phoneCodeId = (Integer) jsonMap.get("phoneCodeId");
		String realname = (String)jsonMap.get("realname");
		String username = (String)jsonMap.get("username");
		String password = (String)jsonMap.get("password");
		String confirmPassword = (String)jsonMap.get("confirmPassword");
		
		//检查密码是否符合要求
		nbRet = CommonHelper.verifyPasswordFormat(password, confirmPassword);
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet = CommonHelper.checkVerifyCodePicture(verifyCode, request, true);
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet = userInfoService.checkPhoneCode(phoneCodeId, phoneCode);
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		
		nbRet = userInfoService.resetPassword(appId, username, password);
		
		if( nbRet.isSuccess() ){
			nbRet.setObject(DataWrapUtil.wrapUserInfo((ZaUser)nbRet.getObject()));
		}

		HttpWebIOHelper.printReturnJson(nbRet, response);
    }
	
}
