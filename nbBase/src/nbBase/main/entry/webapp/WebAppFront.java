package nbBase.main.entry.webapp;



import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbStringUtil;
import nbBase.service.common.WxCommonService;
import nbBase.service.front.UserInfoService;
import nbBase.service.wechat.WechatConfigure;
import nbBase.service.wechat.WechatTokenUserInfo;

/**
 * 
 * @author yuhangs
 *
 */

@Controller
public class WebAppFront {
	
	@Autowired
	UserInfoService frontUserService;
	
	
	@Autowired
	WxCommonService wxCommonService;
	
	String subProjectPath = null;
	 
	 public WebAppFront(){
		 subProjectPath = CommonHelper.loadAppSpecifiedConfig("subAppRootPath");
		 if( nbStringUtil.isNullEmpty(subProjectPath))
			 subProjectPath = "/pengchang";
		 else
			 if( !subProjectPath.startsWith("/") ){
				 subProjectPath = "/" + subProjectPath;
			 }
	 }
	
	/**
	 * 检查当前的weixinSession状态。如果用户没有登录过，就检查code是否有，通过code来获取openid和微信信息
	 * @param request
	 * @return
	 */
	private boolean checkAndUpdateWXUserInfo(HttpServletRequest request, WechatConfigure wxCon){
		
		Map<String, String[]> params = request.getParameterMap();
		
		
		String wechatCode = null;
		String wechatState = null;
		if( params.get("code") != null )
			wechatCode = params.get("code")[0];
		if( params.get("state") != null )
			wechatState = params.get("state")[0];
		
		if( wechatCode != null){
			WechatTokenUserInfo wechatToken = frontUserService.checkAndUpdateWXUserInfo(wechatCode, wxCon);
			if( wechatToken != null ){
				HttpSession session = request.getSession();
				session.setAttribute("currentWXUserinfo", wechatToken.getValueMap());
				System.out.println("user : "+ wechatToken.getOpenid() +" （"+wechatToken.getNickname()+"） 已经被设置成session内容");
				return true;
			}else{
			
			}
			System.out.println("通过wechatCode用户获取失败，将使用已有的session中的用户信息");
		}
		return false;
	}
	
	/**
	 * 前端的统一入口
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{functionName}") 
	public ModelAndView frontEntry(HttpServletResponse response,HttpServletRequest request, @PathVariable String functionName) throws Exception{  
	    //创建模型跟视图，用于渲染页面。并且指定要返回的页面为home页面  
		nbReturn nbRet = new nbReturn();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("serverUrl", CommonHelper.getFullPathOfCurrentRequest(request));
		data.put("successToUrl", "/index.html");
		System.out.println("functionName = " + functionName);
		
		if( functionName.toLowerCase().startsWith("other/") ||
			functionName.toLowerCase().startsWith("admin/") ){
				data.put("retmessage", "重复分发，你不该来这里的！");
				data.put("serverPWD",CommonHelper.getFullPathOfCurrentRequest(request));
				return new ModelAndView("retPage",data);
		}
		
		String modelViewPageName = subProjectPath+"/front/"+functionName;
		
		WechatConfigure wxCon = null;
//		JSONObject jsonMap = CommonHelper.getObjectJSONObject(HttpWebIOHelper.servletInputStream2JsonMap(request)); 
//		Integer eventId = jsonMap.getInteger("eventId");
//		
//		Map<String, String[]> params = request.getParameterMap();

		
		//从session中的用户ID或者eventID来获取wxConfig，如果失败就获取默认的
		nbRet = this.wxCommonService.loadDefaultConfig();
		
		if( nbRet.isSuccess() ){
			wxCon = (WechatConfigure) nbRet.getObject();
		}
		
		if( wxCon != null ){
			checkAndUpdateWXUserInfo(request, wxCon);
			
			switch(functionName){
			
//				case "eventList" :
//					break;
//				case "eventDetail" :
//					break;
//				case "payTheEvent" :
//					break;
//				case "resultMsg" :
//					break;
//				case "myPeng" :
//					break;
//				case "simpleWxEditor":
//					break;
//				case "messageBoard":
//					break;
//				case "pcwxmsg":
//					break;
//				case "eventStatus":
//					break;
//				case "myEvents":
//					break;
//				case "eventMatchResult":
//					break;
//				case "eventMatchResultConfirm":
//					break;
//				case "createOrModifyEvent":
//					break;
//				case "createSimpleEvent":
//					break;
//				case "test":
//					break;
//				case "myPengList":
//					break;
//				case "simpleDetail":
//					nbRet = this.frontAppService.getEventDetail(eventId, null, null);
//					break;
				/*对赌类项目的入口*/
//				case "earnEvents":
//					break;
//				case "earnEventPlay":
//					break;
//				/*对赌类项目的入口 -- end*/
//				default:
//					data.put("retmessage", "你在找什么！？");
//					modelViewPageName = "retPage";
//					break;
				}
		}
		
		data.put("ret",nbRet);
		data.put("serverPWD",CommonHelper.getFullPathOfCurrentRequest(request));
	    ModelAndView mav = new ModelAndView(modelViewPageName,data);
	    return mav;  
	}
	
}
