package pengchang.main.entry.webapp;



import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.nbReturn;
import nbBase.service.common.WxCommonService;
import nbBase.service.front.UserInfoService;
import nbBase.service.wechat.WechatConfigure;
import nbBase.service.wechat.WechatTokenUserInfo;
import pengchang.service.front.ApplicationService;

/**
 * 
 * @author yuhangs
 *
 */

@Controller
public class PCCertainWebAppFront {
	
	@Autowired
	UserInfoService frontUserService;
	
	@Autowired
	ApplicationService frontAppService;
	
	@Autowired
	WxCommonService wxCommonService;
	
	private static final String subProjectPath = "/pengchang";
	
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
	 * 显示一个简单的event详情
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@RequestMapping(value = "simpleDetail") 
	public ModelAndView frontEntry(HttpServletResponse response,HttpServletRequest request) throws Exception{  
	    //创建模型跟视图，用于渲染页面。并且指定要返回的页面为home页面  
		nbReturn nbRet = new nbReturn();
		Map<String,Object> data = new HashMap<String,Object>();
		String modelViewPageName = subProjectPath+"/front/simpleDetail";
		
		WechatConfigure wxCon = null;
		Map<String, String[]> param = request.getParameterMap();
		Integer eventId = param.get("eventId") == null ? null : Integer.valueOf(param.get("eventId")[0].toString());
		
		//从session中的用户ID或者eventID来获取wxConfig，如果失败就获取默认的
		nbRet = frontAppService.loadWechatConfig(
				CommonHelper.getOpenIdFromSession(request), 
				eventId, 
				true);
		
		if( nbRet.isSuccess() ){
			wxCon = (WechatConfigure) nbRet.getObject();
		}
		
		if( wxCon != null ){
			checkAndUpdateWXUserInfo(request, wxCon);
		
			nbRet = this.frontAppService.getEventDetail(eventId, null, null);
		}
	
		
		data.put("ret",nbRet);
		data.put("serverPWD",CommonHelper.getFullPathOfCurrentRequest(request));
	    ModelAndView mav = new ModelAndView(modelViewPageName,data);
	    return mav;  
	}
	
}
