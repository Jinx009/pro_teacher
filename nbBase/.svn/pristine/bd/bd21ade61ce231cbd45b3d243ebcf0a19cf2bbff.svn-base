package pengchang.main.entry.webapp.front.REST;


import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import nbBase.helper.common.HttpWebIOHelper;
import nbBase.helper.common.nbReturn;
import nbBase.service.wechat._WechatUtils;





@Controller
public class FrontRESTEntry_Utils {
	
	
	/**
	 * 获取保护了的图片内容
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/doGetProtectedPicStream") 
	public void doGetProtectedPicStream (HttpServletResponse response,HttpServletRequest request) throws Exception{
		Map<String, String[]> params = request.getParameterMap();
		String targetUrl = null;
		Map<String, Object> gotReturn = null;
		if( params != null && params.get("target") !=null) {
			targetUrl = URLDecoder.decode(params.get("target")[0],"utf-8");
		}
		
		if(targetUrl != null){
			
			gotReturn = _WechatUtils.httpGetBytes(targetUrl);
			
			if( gotReturn != null ){
				// 禁止图像缓存。         
		        //response.setHeader("Pragma", "no-cache");         
		        //response.setHeader("Cache-Control", "no-cache");         
		        //response.setDateHeader("Expires", 0);         
		        response.setContentType((String)gotReturn.get("contentType"));    
		        byte[] data = (byte[]) gotReturn.get("content");
		        // 将图像输出到Servlet输出流中。         
		        ServletOutputStream sos = response.getOutputStream();       
		        sos.write(data);    
		        sos.close();  
			}
		}
	}
	
	
	/**
	 * curl的模拟
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/doCurl") 
	public void doCurl (HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();
		String url = null;
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		if( jsonMap == null){
			Map<String, String[]> params = request.getParameterMap();
			if( params.get("url") != null )
				url = params.get("url")[0];
		}else{
			url = (String) jsonMap.get("url");
		}
		Map<String,Object> retData = new HashMap<String, Object>();
		retData.put("content", _WechatUtils.httpGet(url));
		nbRet.setObject(retData);
	
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
}

/**
 * 发送模板消息
 * @param response
 * @param request
 * @throws Exception
 */
//@RequestMapping(value = "/doSendTplMessage") 
//public void doSendTplMessage (HttpServletResponse response,HttpServletRequest request) throws Exception{
//	
//	nbReturn nbRet = new nbReturn();
//	
//	WechatConfigure wxCon = wxCommonService.loadWxConfigure(request, null);
//	if( wxCon == null ){
//		nbRet.setError(ReturnCode.WECHAT_CONFIG_LOAD_ERROR);
//		HttpWebIOHelper.printReturnJson(nbRet, response);	
//		return;
//	}
//	
//	
//	String template_id = "tD6FOuIKz6JyvSbh_KtKzZYHpqi4RCPvEdUtzgPOKMA";
//	String url = "http://pc.0angle.com/myPeng.html#?orderId=3";
//	Boolean needJumpAuth = true;
//	String first_data = "这是您的支付凭证。";
//	String[] keywords = {"这里是订单号", "这里是支付单号", "这里是支付金额", "这里是支付时间"};
//	String  remark_data= "这里是备注";
//	
//	
//	HttpSession session = request.getSession();
//	@SuppressWarnings("unchecked")
//	Map<String, Object> sessionTokenUserInfo = (Map<String, Object>) (session.getAttribute("currentWXUserinfo"));
//	@SuppressWarnings("unchecked")
//	Map<String, Object> sessionJSSDK =  (Map<String, Object>) (session.getAttribute("currentJSSDKInfo"));
//	
//	
//	nbRet = 
//	this.wxCommonService.sendTplMessage(
//			template_id,
//			url,
//			needJumpAuth,
//			first_data,
//			keywords,
//			remark_data,
//			sessionTokenUserInfo == null ? null : (String)sessionTokenUserInfo.get(_WechatKeyDefine.wxUserInfo.openid),
//			sessionJSSDK == null? null : (String)sessionJSSDK.get(_WechatKeyDefine.pageSDK.page_access_token),
//			sessionJSSDK == null? null : (Long)sessionJSSDK.get(_WechatKeyDefine.pageSDK.page_token_expireTime), 
//			wxCon
//			);
//
//	HttpWebIOHelper.printReturnJson(nbRet, response);	
//}