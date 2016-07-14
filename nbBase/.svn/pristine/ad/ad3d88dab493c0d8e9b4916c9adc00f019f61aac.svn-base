package pengchang.main.entry.webapp.front.REST;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;

import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.HttpWebIOHelper;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbReturn.ReturnCode;
import nbBase.service.common.WxCommonService;
import nbBase.service.front.UserInfoService;
import nbBase.service.wechat.WechatConfigure;
import pengchang.service.front.ApplicationServiceEarnEvent;
import pengchang.service.front.WordList;




@Controller
public class FrontRESTEntry_earnEvent {

	@Autowired
	UserInfoService frontUserService;

	
	@Autowired
	WxCommonService wxCommonService;
	
	@Autowired
	ApplicationServiceEarnEvent frontAppServiceEarnEvent;
	
	/**
	 * 获取对赌类项目列表
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/doGetEarnEvents") 
	public void doGetEarnEvents (HttpServletResponse response,HttpServletRequest request) throws Exception{

		nbReturn nbRet = new nbReturn();
		Map<String, Object> jsonMap = CommonHelper.getObjectJSONObject(HttpWebIOHelper.servletInputStream2JsonMap(request));
		Integer recomEventId = CommonHelper.getObjectInteger(jsonMap.get("recomEventId"));
		
		if( recomEventId == 0 ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
//		HttpSession session = request.getSession();
//		@SuppressWarnings("unchecked")
//		Map<String, Object> sessionTokenUserInfo = (Map<String, Object>) (session.getAttribute("currentWXUserinfo"));
		
		nbRet = wxCommonService.loadDefaultConfig();
		
		WechatConfigure wxCon = null;
		if( nbRet.isSuccess() ){
			wxCon = (WechatConfigure) nbRet.getObject();
		}
		
		if( wxCon == null ){
			nbRet.setError(ReturnCode.WECHAT_CONFIG_LOAD_ERROR);
			HttpWebIOHelper.printReturnJson(nbRet, response);	
			return;
		}
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(recomEventId);
		nbRet = this.frontAppServiceEarnEvent.getEarnEventList(ids);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}

	/**
	 * 检查用户针对某个对赌项目是在进行中还是其他什么状态
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/doCheckUserStatusOfThePlay") 
	public void doCheckUserStatusOfThePlay (HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Integer eventId = jsonMap.get("eventId") == null ? null : Integer.valueOf(jsonMap.get("eventId").toString());
		
		nbRet = checkUserStatusOfThePlay(response, request, eventId);
		
		// 返回的状态有： error ; OK 有一个order说明有一个进行中的; OK 没有order 没有正在进行中的
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	
	private nbReturn checkUserStatusOfThePlay(HttpServletResponse response, HttpServletRequest request, Integer eventId) {
		nbReturn nbRet = new nbReturn();
		
		if( eventId == null ){
			nbRet.setError(ReturnCode.REQUESTED_EVENT_OR_RULE_WRONG);
			return nbRet;
		}
		
		String openId = CommonHelper.getOpenIdFromSession(request);
		if(openId == null )
			openId = "oecAVt4hSa4941GwbIWkLDhrTTwA";
		
		if( openId == null ){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			return nbRet;
		}
		
		nbRet = 
				this.frontAppServiceEarnEvent.checkEarnStatusOfUser(eventId, openId);
		
		// 返回的状态有： error ; OK 有一个order说明有一个进行中的; OK 没有order 没有正在进行中的
		return nbRet;
		
	}
	
	@RequestMapping(value = "/doGenerateRandomWordsList") 
	public void doGenerateRandomWordsList (HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Integer orderId = jsonMap.get("orderId") == null ? null : Integer.valueOf(jsonMap.get("orderId").toString());
		if( orderId == null){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		
		String openId = CommonHelper.getOpenIdFromSession(request);
		if(openId == null )
			openId = "oecAVt4hSa4941GwbIWkLDhrTTwA";
		
		nbRet = this.frontAppServiceEarnEvent.checkUserStatusOfThePlayWithOrder(openId, orderId);
		if( nbRet.isSuccess() ){
			
			WordList wordList = this.frontAppServiceEarnEvent.generateRandomWordsList(20, orderId); 
			HttpSession session = request.getSession();
			session.setAttribute("earnPlayTryTimestamp", wordList.tryTimestamp);
			
			nbRet.setObject( wordList);
		}
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	@RequestMapping(value = "/doSaveTryResult") 
	public void doSaveTryResult (HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();	
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		
		JSONArray wordAnswerList = (JSONArray) jsonMap.get("data");
		HttpSession session = request.getSession();
		Long tryTimestamp = (Long) session.getAttribute("earnPlayTryTimestamp");
		
		nbRet = this.frontAppServiceEarnEvent.updateByTryTimestamp(tryTimestamp, wordAnswerList);
		
		session.setAttribute("earnPlayTryTimestamp", null);//无论是否成功，一旦保存过了就要清楚session中这次尝试
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
//	@RequestMapping(value = "/doGetEarnEventAwardInfo") 
//	public void doGetEarnEventAwardInfo (HttpServletResponse response,HttpServletRequest request) throws Exception{
//		
//		nbReturn nbRet = new nbReturn();	
//		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
//		
//		Integer orderId =jsonMap.get("orderId") == null ? null :  Integer.valueOf(jsonMap.get("orderId").toString());
//		Integer eventId =jsonMap.get("eventId") == null ? null :  Integer.valueOf(jsonMap.get("eventId").toString());
//		Integer ruleId =jsonMap.get("ruleId") == null ? null :  Integer.valueOf(jsonMap.get("ruleId").toString());
//		
//		HttpSession session = request.getSession();
//		Long tryTimestamp = (Long) session.getAttribute("earnPlayTryTimestamp");
//		
//		String openId = CommonHelper.getOpenIdFromSession(request);
//		if(openId == null )
//			openId = "oecAVt4hSa4941GwbIWkLDhrTTwA";
//		
//		nbRet = 
//				this.frontAppServiceEarnEvent.getTryResult(
//					eventId,
//					ruleId,
//					orderId,
//					tryTimestamp);
//		
//		HttpWebIOHelper.printReturnJson(nbRet, response);
//	}
	
	
	
	@RequestMapping(value = "/doSendEarnEventAward") 
	public void doSendEarnEventAward (HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();	
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		
		Integer orderId =jsonMap.get("orderId") == null ? null :  Integer.valueOf(jsonMap.get("orderId").toString());
		Integer eventId =jsonMap.get("eventId") == null ? null :  Integer.valueOf(jsonMap.get("eventId").toString());
		Integer ruleId =jsonMap.get("ruleId") == null ? null :  Integer.valueOf(jsonMap.get("ruleId").toString());
		Long tryTimestamp =jsonMap.get("tryTimestamp") == null ? null :  Long.valueOf(jsonMap.get("tryTimestamp").toString());
		
		HttpSession session = request.getSession();
		
		String openId = CommonHelper.getOpenIdFromSession(request);
		if(openId == null )
			openId = "oecAVt4hSa4941GwbIWkLDhrTTwA";
		
		if( openId == null ){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		@SuppressWarnings("unchecked")
		Map<String, Object> sessionTokenUserInfo = (Map<String, Object>) (session.getAttribute("currentWXUserinfo"));
		
		nbRet = wxCommonService.loadDefaultConfig();
		
		WechatConfigure wxCon = null;
		if( nbRet.isSuccess() ){
			wxCon = (WechatConfigure) nbRet.getObject();
		}
		

		if( wxCon != null){
			
			nbRet = 
					this.frontAppServiceEarnEvent.sendAward(
							eventId, 
							ruleId, 
							orderId, 
							tryTimestamp, 
							openId,
							sessionTokenUserInfo,
							wxCon);
		
		}
		else{
			nbRet.setError(ReturnCode.WECHAT_CONFIG_LOAD_ERROR);
		}
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
}
