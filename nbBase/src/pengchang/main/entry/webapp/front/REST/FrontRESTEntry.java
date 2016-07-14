package pengchang.main.entry.webapp.front.REST;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import nbBase.database.models.ZaWxPayCallback;
import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.HttpWebIOHelper;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbReturn.ReturnCode;
import nbBase.helper.common.nbStringUtil;
import nbBase.service.common.WxCommonService;
import nbBase.service.front.UserInfoService;
import nbBase.service.wechat.WechatConfigure;
import nbBase.service.wechat.WechatPageSDK;
import nbBase.service.wechat._WechatKeyDefine;
import nbBase.service.wechat._WechatUtils;
import pengchang.database.models.PCCoreOrder;
import pengchang.service.front.ApplicationService;





@Controller
public class FrontRESTEntry {
	
	@Autowired
	UserInfoService frontUserService; 
	
	@Autowired
	ApplicationService frontAppService;
	
	@Autowired
	WxCommonService wxCommonService;
	
	
	/**
	 * 后台的callback，用来设置order是否支付成功
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/wxPayCallback") 
	public void wxPayCallback(HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		Map<String, Object> param = HttpWebIOHelper.parseXMLParam(request);
		
		Map<String, Object> retData = 
				this.frontAppService.savePayCallback(param);
		
		WechatConfigure wxCon = wxCommonService.loadWxConfigureByAppId((String)param.get(_WechatKeyDefine.wxPayCallback.appid));
		String resultCode = (String)param.get(_WechatKeyDefine.wxPayCallback.result_code);
		Boolean isSuccess = (resultCode != null && resultCode.toLowerCase().equals("success")) ? true : false;
		
		
		String totalFee = (String)param.get(_WechatKeyDefine.wxPayCallback.total_fee);
		String payTime = (String)param.get(_WechatKeyDefine.wxPayCallback.time_end);
		Float fee = Float.valueOf(totalFee);
		
		PCCoreOrder order = (PCCoreOrder) retData.get("ZaCoreOrder");
		ZaWxPayCallback wpc = (ZaWxPayCallback) retData.get("ZaWxPayCallback");
		@SuppressWarnings("unchecked")
		Map<String, Object> event = (Map<String, Object>) this.frontAppService.getEventDetail(order.getEventId(), order.getEventRuleId(), wxCon).getObject();
		@SuppressWarnings("unchecked")
		Map<String, Object> rule = ((List<Map<String, Object>>)(event.get(_WechatKeyDefine.nbEventRule.rules))).get(0);
		
		
		if( wxCon != null && isSuccess && order != null && wpc != null && event !=null && rule != null || true){
			
			String[] keywords = {
					(String) rule.get(_WechatKeyDefine.nbEventRule.rules_ruleDesc), 
					order.getOrderedCopies().toString(), 
					"¥ "+fee/100.0+" 元", 
					nbStringUtil.DateTime2StrinF01(nbStringUtil.String2DateTimeF02(payTime))
					};
			System.out.println("wxPay:");
			for(int i = 0 ; i < keywords.length ; i++)
				System.out.println(keywords[i]);
			this.wxCommonService.sendTplMessage(
					wxCon.tplMsgPaySuccess, 
					wxCon.serverName + "/myPeng.html?orderId="+order.getId(), 
					true, 
						"\r\n-------------------------\r\n"+
						event.get(_WechatKeyDefine.nbEvent.events_title)+
						"\r\n-------------------------\r\n"+
						"本众筹活动的截止时间为： "+
						event.get(_WechatKeyDefine.nbEvent.events_deadLine) +
						" 请保持关注本公众号，以便获取众筹结果通知！"+
						//nbStringUtil.cutString((String) event.get(_WechatKeyDefine.nbEvent.events_desc),15) +
						"\r\n", 
					keywords,
					"\r\n点击查看订单详情\r\n", 
					(String)param.get(_WechatKeyDefine.wxPayCallback.openid), 
					wxCon);
			
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("return_code", (String)param.get(_WechatKeyDefine.wxPayCallback.return_code));
		ret.put("return_msg", "OK");
		//System.out.println(_WechatUtils.Map2XMLString(ret));
		HttpWebIOHelper.printReturnText(_WechatUtils.Map2XMLString(ret), response);
	}
	
	
	/**
	 * 后台的callback，用来设置order是否支付成功
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/wxTemplateTest") 
	public void wxTemplateTest(HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn ret = this.wxCommonService.loadDefaultConfig();
		WechatConfigure wxCon = (WechatConfigure) ret.getObject();
			
			String[] keywords = {
					"支持2元",
					"1",
					"¥ 0.01 元",
					"2016年07月04日"
					};
			System.out.println("wxPay:");
			for(int i = 0 ; i < keywords.length ; i++)
				System.out.println(keywords[i]);
			this.wxCommonService.sendTplMessage(
					wxCon.tplMsgPaySuccess, 
					wxCon.serverName + "/myPeng.html?orderId=27", 
					true, 
						"\r\n-------------------------\r\n"+
						"随时随踢运动手环众筹（测试）"+
						"\r\n-------------------------\r\n"+
						nbStringUtil.cutString("随时随踢运动手环众筹（测试），是针对随时随踢的会员进行的运动手环众筹活动。每只手环299（测试时期2元，不发货），众筹目标100人，截止时间为2016年7月6日 18:00点整。如果到截止时间为止未能达到目标人数，系统将自动全额退款给已付款者。",
								15)+
						"\r\n", 
					keywords,
					"\r\n点击查看订单详情\r\n", 
					"oecAVt4hSa4941GwbIWkLDhrTTwA", 
					wxCon);
			
		}
		
	
	
	/**
	 * 通过公众号向session中的用户定向发送一个红包，所以session是必须的，之前必须是通过oauth登录过的页面才能调用次api
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value =  "/doSendATargetedAward") 
	public void doSendATargetedAward (HttpServletResponse response,HttpServletRequest request) throws Exception{
		//Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = new nbReturn();
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);

		
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<String, Object> sessionTokenUserInfo = (Map<String, Object>) (session.getAttribute("currentWXUserinfo"));
		
		nbRet = frontAppService.loadWechatConfig(
				CommonHelper.getOpenIdFromSession(request), 
				CommonHelper.getEventIdFromSession(request), 
				true);
		WechatConfigure wxCon = null;
		if( nbRet.isSuccess() ){
			wxCon = (WechatConfigure) nbRet.getObject();
		}
		
		if( wxCon != null){
			nbRet = 
			this.wxCommonService.sendAward(
					"我是商户名称：捧场",
					"我是活动名称",
					"我是祝福语",
					null, //clientIp == null
					"我是备注",
					null, //目标对象的appid; 如果为空则使用session中的openId，也就是当前登录用户
					100,
					1,
					sessionTokenUserInfo,
					"EVENTID:"+CommonHelper.getEventIdFromSession(request), 
					wxCon);
		
		}
		else{
			nbRet.setError(ReturnCode.WECHAT_CONFIG_LOAD_ERROR);
		}
		
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	/**
	 * 通过公众号向session中的用户定向转账，所以session是必须的，之前必须是通过oauth登录过的页面才能调用次api
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value =  "/doTransferMoney") 
	public void doTransferMoney (HttpServletResponse response,HttpServletRequest request) throws Exception{
		//Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = new nbReturn();

		String openId = CommonHelper.getOpenIdFromSession(request); 
		Integer eventId = CommonHelper.getEventIdFromSession(request); 
		
		if(openId == null){
			openId = "oecAVt4hSa4941GwbIWkLDhrTTwA"; 
		}
		
		nbRet = frontAppService.loadWechatConfig(
				openId,
				eventId,
				true);
		
		WechatConfigure wxCon = null;
		if( nbRet.isSuccess() ){
			wxCon = (WechatConfigure) nbRet.getObject();
		}
		
		if( wxCon != null){
			nbRet = 
			this.wxCommonService.transferMoney(
					"您的活动经费", //发钱理由
					openId, 
					this.frontAppService.getEventAmountCount(eventId),
					wxCon,
					"EVENTID:"+eventId);
		
		}
		else{
			nbRet.setError(ReturnCode.WECHAT_CONFIG_LOAD_ERROR);
		}
		
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	/**
	 * 前端页面调用这个api获取需要的微信jssdk参数进行装填，如果session中有已经有个用户信息，返回值会带上所有信息返回给前端
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value =  "/doGetFrontSessionInfo") 
	public void doGetFrontSessionInfo (HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		String url = (String) jsonMap.get("url");
		
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<String, Object> sessionTokenUserInfo = (Map<String, Object>) (session.getAttribute("currentWXUserinfo"));
		Map<String, Object> sessionJSSDK = null;
		
		
		nbRet = frontAppService.loadWechatConfig(
				CommonHelper.getOpenIdFromSession(request), 
				CommonHelper.getEventIdFromSession(request), 
				true);
		WechatConfigure wxCon = null;
		if( nbRet.isSuccess() ){
			wxCon = (WechatConfigure) nbRet.getObject();
		}
		
		if( wxCon == null ){
			nbRet.setError(ReturnCode.WECHAT_CONFIG_LOAD_ERROR);
			HttpWebIOHelper.printReturnJson(nbRet, response);	
			return;
		}
		
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
	

	/**
	 * 检测某个openId是否关注了公众号
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value =  "/doCheckIfOpenIdIsUser") 
	public void doCheckIfOpenIdIsUser (HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();
		Map<String, Object> jsonMap = CommonHelper.getObjectJSONObject(HttpWebIOHelper.servletInputStream2JsonMap(request));
		String openId = (String) jsonMap.get("openId");
		
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<String, Object> sessionTokenUserInfo = (Map<String, Object>) (session.getAttribute("currentWXUserinfo"));
		
		nbRet = frontAppService.loadWechatConfig(
				CommonHelper.getOpenIdFromSession(request), 
				CommonHelper.getEventIdFromSession(request), 
				true);
		WechatConfigure wxCon = null;
		if( nbRet.isSuccess() ){
			wxCon = (WechatConfigure) nbRet.getObject();
		}
		
		if( wxCon == null ){
			nbRet.setError(ReturnCode.WECHAT_CONFIG_LOAD_ERROR);
			HttpWebIOHelper.printReturnJson(nbRet, response);	
			return;
		}
		
		System.out.println("doCheckIfOpenIdIsUser:"+openId);
//		boolean isOK = false;
		if( openId == null ){
			if( sessionTokenUserInfo != null){
				
				openId = CommonHelper.getOpenIdFromSession(request);
				//(String) sessionTokenUserInfo.get(_WechatKeyDefine.wxUserInfo.openid);
			}
			else{
				nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
				HttpWebIOHelper.printReturnJson(nbRet, response);
				return;
			}
			
		}
		
		System.out.println("doCheckIfOpenIdIsUser--:"+openId);
		if( openId != null ){
			
			nbRet = this.frontUserService.checkIfUserFollowed(
					openId, 
					wxCon);
		}
		
//		if( !isOK ){
//			nbRet.setError(ReturnCode.PARAMETER_PHARSE_ERROR);
//		}
		
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	
	/**
	 * 在需要微信支付的时候，调用这个api获取需要的所有的参数，然后在前端进行直接装填
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value =  "/doGetParameterForWxPay") 
	public void doGetParameterForWxPay (HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Integer eventId = Integer.valueOf(jsonMap.get("eventId").toString());
		Integer eventRuleId = Integer.valueOf(jsonMap.get("eventRuleId").toString());
		Integer copies = Integer.valueOf(jsonMap.get("copies").toString());
		Integer totalFee = Integer.valueOf(jsonMap.get("totalFee").toString());
		String realname = (String) jsonMap.get("realname");
		String userPhone = (String) jsonMap.get("userPhone");
		String userAddress = (String) jsonMap.get("userAddress");
		
		nbRet = frontAppService.loadWechatConfig(
				CommonHelper.getOpenIdFromSession(request), 
				CommonHelper.getEventIdFromSession(request), 
				true);
		WechatConfigure wxCon = null;
		if( nbRet.isSuccess() ){
			wxCon = (WechatConfigure) nbRet.getObject();
		}
		
		
		if( wxCon == null ){
			nbRet.setError(ReturnCode.WECHAT_CONFIG_LOAD_ERROR);
			HttpWebIOHelper.printReturnJson(nbRet, response);	
			return;
		}
		
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<String, Object> sessionTokenUserInfo = (Map<String, Object>) (session.getAttribute("currentWXUserinfo"));
		if( sessionTokenUserInfo == null ){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
		}
		else{
			
			nbRet = frontAppService.createUnionOrder(
					"我是支付内容", 
					"我是详情部分", 
					"我是拖油瓶", 
					totalFee, 
					"127.0.0.1", 
					eventId,
					eventRuleId,
					copies,
					realname,
					userPhone,
					userAddress,
					wxCon,
					sessionTokenUserInfo);
		}
		
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	
	/**
	 * 获取项目列表
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value =  "/doGetEventList") 
	public void doGetEventList (HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();
		String url = null;
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		
		Integer startIndex = CommonHelper.getObjectInteger(jsonMap.get("startIndex"));
		Integer pageSize = CommonHelper.getObjectInteger(jsonMap.get("pageSize"));
		Integer orderMode =  CommonHelper.getObjectInteger(jsonMap.get("displayMode")); // null or 0 : 普通模式(不显示sample和不Active的)； 1 ： 未完成的放在前面
		Integer recomEventId =  CommonHelper.getObjectInteger(jsonMap.get("recomEventId")); //null or 0 ：没有置顶活动; 其他：置顶的活动id号
		String createrOpenId = CommonHelper.getObjectString(jsonMap.get("createrOpenId"));
		
		/**
		 * displayMode : EDCBA  
		 * 				subType-A : 1 其他众筹 2：比赛众筹  
		 * 				eventType-B 0 :无效 1：普通支付 2：众筹项目 3：单人对赌项目
		 * 				isSample-C 0:不要显示sample 1：只显示sample 2：可显示sample
		 * 				isAcive-D 0:不显示无效的 1：只显示无效的 2：都显示
		 */
		
		nbRet = frontAppService.loadWechatConfig(
				CommonHelper.getOpenIdFromSession(request), 
				null, 
				true);
		
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		WechatConfigure wxCon = (WechatConfigure) nbRet.getObject();
		
		nbRet = 
		this.frontAppService.getEventList(
				startIndex,
				pageSize,
				orderMode,
				recomEventId,
				createrOpenId,
				wxCon
				);
	
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	/**
	 * 获取某个event
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@Deprecated
	@RequestMapping(value =  "/doGetEvent_old") 
	public void doGetEvent_old (HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		
		Integer eventId = jsonMap.get("eventId") == null ? null : Integer.valueOf(jsonMap.get("eventId").toString()); 
		
		
		nbRet = frontAppService.loadWechatConfig(
				CommonHelper.getOpenIdFromSession(request), 
				CommonHelper.getEventIdFromSession(request), 
				true);
		WechatConfigure wxCon = null;
		if( nbRet.isSuccess() ){
			wxCon = (WechatConfigure) nbRet.getObject();
		}
		
		
		nbRet = 
		this.frontAppService.getEvent(
				eventId,
				wxCon);
		
	
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	/**
	 * 获取某个event
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value =  "/doGetEvent") 
	public void doGetEvent(HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		
		Integer eventId = jsonMap.get("eventId") == null ? null : Integer.valueOf(jsonMap.get("eventId").toString()); 
		System.out.println("----"+jsonMap.get("isShowEmptyText"));
		Boolean isShowEmptyText = CommonHelper.getObjectBoolean(jsonMap.get("isShowEmptyText"), true);
		
		if( eventId == null || eventId == 0){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
			
		}
		
		nbRet = frontAppService.loadWechatConfig(
				CommonHelper.getOpenIdFromSession(request), 
				eventId, 
				true);
		
		WechatConfigure wxCon = null;
		if( nbRet.isSuccess() ){
			wxCon = (WechatConfigure) nbRet.getObject();
		}
		
		
		nbRet = this.frontAppService.getEventInfo(
				eventId,
				wxCon,
				CommonHelper.getOpenIdFromSession(request),
				isShowEmptyText);
		
	
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	/**
	 * 获取某个event的详细信息
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@Transactional
	@RequestMapping(value =  "/doGetEventDetailInfo") 
	public void doGetEventDetailInfo (HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();
		String url = null;
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		
		Integer eventId = jsonMap.get("eventId") == null ? null : Integer.valueOf( jsonMap.get("eventId").toString());
		Integer eventRuleId = jsonMap.get("eventRuleId") == null ? null : Integer.valueOf( jsonMap.get("eventRuleId").toString());
		
		if(eventId != null && eventId == 0 ) eventId = null;
		
		if( eventId != null ){
			
			HttpSession session = request.getSession();
			@SuppressWarnings("unchecked")
			Map<String, Object> sessionTokenUserInfo = (Map<String, Object>) (session.getAttribute("currentWXUserinfo"));
			nbRet = frontAppService.loadWechatConfig(sessionTokenUserInfo.get(_WechatKeyDefine.wxUserInfo.openid).toString(), eventId, true);
			
			if( nbRet.isSuccess() ){
				WechatConfigure wxCon = (WechatConfigure) nbRet.getObject();
				nbRet = 
				this.frontAppService.getEventDetail(
						eventId,
						eventRuleId,
						wxCon
						);
			}
		}
		
	
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	/**
	 * 穿透获取
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@Transactional
	@RequestMapping(value =  "/doGetEventDetailInfoByWxCreater") 
	public void doGetEventDetailInfoByWxCreater (HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();
		String url = null;
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		
		Integer eventId = jsonMap.get("eventId") == null ? null : Integer.valueOf( jsonMap.get("eventId").toString());
		Integer eventRuleId = jsonMap.get("eventRuleId") == null ? null : Integer.valueOf( jsonMap.get("eventRuleId").toString());
		String openId = CommonHelper.getOpenIdFromSession(request);
		
		if(openId == null )
			openId = "oecAVt4hSa4941GwbIWkLDhrTTwA"; 
		
		if(eventId != null && eventId == 0 ) eventId = null;
		
		if( eventId != null ){
			
			nbRet = frontAppService.loadWechatConfig(openId, eventId, true);
			
			if( nbRet.isSuccess() ){
				WechatConfigure wxCon = (WechatConfigure) nbRet.getObject();
				nbRet = 
				this.frontAppService.getEventDetailByWxCreater(
						eventId,
						eventRuleId,
						openId
						);
			}
		}
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	/**
	 * 通过页面调用了设置oder支付成功（仅作参考，最终判断order是否支付成功是通过后台的callback实现的
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value =  "/doSetFrontPaySucceed") 
	public void doSetFrontPaySucceed (HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();

		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		
		String prepayId = (String) jsonMap.get("prepayId");
		
		nbRet = frontAppService.loadWechatConfig(
				CommonHelper.getOpenIdFromSession(request), 
				CommonHelper.getEventIdFromSession(request), 
				true);
		WechatConfigure wxCon = null;
		if( nbRet.isSuccess() ){
			wxCon = (WechatConfigure) nbRet.getObject();
		}
		
		
		nbRet = 
		this.frontAppService.setFrontPaySucceed(
				prepayId,
				wxCon
				);
		
	
		HttpWebIOHelper.printReturnJson(nbRet, response);	
	}
	
	/**
	 * 获取order的详细信息
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value =  "/doGetOrderInfo") 
	public void doGetOrderInfo(HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		//System.out.println(HttpWebIOHelper.servletInputStream2String(request));
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		
		
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<String, Object> sessionTokenUserInfo = (Map<String, Object>) (session.getAttribute("currentWXUserinfo"));
		if( sessionTokenUserInfo == null ){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		if( jsonMap.get("orderId") == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		Integer orderId = Integer.valueOf(jsonMap.get("orderId").toString());
		
		nbRet = 
				this.frontAppService.getOrderDetail(orderId, (String)sessionTokenUserInfo.get(_WechatKeyDefine.wxUserInfo.openid));
		
		/**
		 * 把当前的用户session信息放进去
		 */
//		@SuppressWarnings("unchecked")
//		Map<String, Object> tmp = (Map<String, Object>) nbRet.getObject();
//		tmp.put(_WechatKeyDefine.wxUserInfo.o_userInfo, sessionTokenUserInfo);
//		nbRet.setObject(tmp);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	/**
	 * 获取某个event的状态
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value =  "/doGetEventStatus") 
	public void doGetEventStatus(HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		//System.out.println(HttpWebIOHelper.servletInputStream2String(request));
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		if( jsonMap.get("eventId") == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		Integer eventId = Integer.valueOf(jsonMap.get("eventId").toString());
		
		Integer startIndex = jsonMap.get("startIndex") == null ? null : Integer.valueOf(jsonMap.get("startIndex").toString());
		Integer pageSize  = jsonMap.get("pageSize") == null ? null : Integer.valueOf(jsonMap.get("pageSize").toString());
		
		if(startIndex == null)
			startIndex = 0;
		
		if( pageSize == null )
			pageSize = 120;
		
		nbRet = 
				this.frontAppService.getEventStatus(eventId, startIndex, pageSize);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	/**
	 * p12秘钥文件一开始是以文件方式存储在硬盘上，并把url记录在数据库中，通过这个方法可以把文件的二进制码读出来，直接保存在数据库
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value =  "/doInsertCertFileToVarbinary") 
	public void doInsertCertFileToVarbinary(HttpServletResponse response,HttpServletRequest request) throws Exception{
		Map<String, String[]> param = request.getParameterMap();
		int wxConfigId = 0;
		if(param != null && param.get("wxConfigId")!=null){
			wxConfigId = Integer.valueOf(param.get("wxConfigId").toString()).intValue();
		}
		if( wxConfigId == 0){
			wxConfigId = 1;
		}
		this.wxCommonService.insertCertFileToVarbinary(wxConfigId);
	}
	
	@RequestMapping(value =  "/doConfirmMatchEventResult") 
	public void doConfirmMatchEventResult(HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		if( jsonMap.get("eventId") == null || jsonMap.get("resultId") == null){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		Integer eventId = Integer.valueOf(jsonMap.get("eventId").toString());
		Integer resultId = Integer.valueOf(jsonMap.get("resultId").toString());
		
		String openId = CommonHelper.getOpenIdFromSession(request);
		
//		if( openId == null)
//			openId = "oecAVt4hSa4941GwbIWkLDhrTTwA";
		
		nbRet = this.frontAppService.loadWechatConfig(
				openId,
				eventId,
				false);
		
		if( !nbRet.isSuccess() ){
			nbRet.setError(ReturnCode.WECHAT_CONFIG_LOAD_ERROR);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		WechatConfigure wxCon = (WechatConfigure) nbRet.getObject();
		
		nbRet = 
				this.frontAppService.confirmMatchEventResult(
						eventId,
						openId,
						resultId,
						wxCon);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	@RequestMapping(value =  "/doSaveMatchEventResult") 
	public void doSaveMatchEventResult(HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		if( jsonMap.get("eventId") == null || jsonMap.get("rivalId") == null || jsonMap.get("result") == null){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		Integer eventId = Integer.valueOf(jsonMap.get("eventId").toString());
		Integer rivalId = Integer.valueOf(jsonMap.get("rivalId").toString());
		Integer result = Integer.valueOf(jsonMap.get("result").toString());
		
		String openId = CommonHelper.getOpenIdFromSession(request);
		
//		if( openId == null)
//			openId = "oecAVt4hSa4941GwbIWkLDhrTTwA";
		
		nbRet = this.frontAppService.loadWechatConfig(
				openId,
				eventId,
				false);
		
		if( !nbRet.isSuccess() ){
			nbRet.setError(ReturnCode.WECHAT_CONFIG_LOAD_ERROR);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		WechatConfigure wxCon = (WechatConfigure) nbRet.getObject();
		
		nbRet = 
				this.frontAppService.saveMatchEventResult(
						eventId,
						openId,
						rivalId,
						result,
						wxCon);
		
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	@RequestMapping(value =  "/doGetMatchEventResult") 
	public void doGetMatchEventResult(HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		if( jsonMap.get("eventId") == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		Integer eventId = Integer.valueOf(jsonMap.get("eventId").toString());
		Boolean onlyNotConfirmed = jsonMap.get("onlyNotConfirmed") == null ? false : Boolean.valueOf(jsonMap.get("onlyNotConfirmed").toString());
		
		
		String openId = CommonHelper.getOpenIdFromSession(request);
		
//		if( openId == null)
//			openId = "oecAVt4hSa4941GwbIWkLDhrTTwA";
		
		nbRet = this.frontAppService.loadWechatConfig(
				openId,
				eventId,
				false);
		
		if( !nbRet.isSuccess() ){
			nbRet.setError(ReturnCode.WECHAT_CONFIG_LOAD_ERROR);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		WechatConfigure wxCon = (WechatConfigure) nbRet.getObject();
		
		nbRet = 
				this.frontAppService.getMatchEventResult(
						eventId,
						openId,
						wxCon,
						onlyNotConfirmed);
		
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	/**
	 * 得到某个event的留言板内容
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@Transactional
	@RequestMapping(value =  "/doGetEventMessage") 
	public void doGetEventMessage(HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		if( jsonMap.get("eventId") == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		Integer eventId = Integer.valueOf(jsonMap.get("eventId").toString());
		Integer startIndex = jsonMap.get("startIndex") == null ? null : Integer.valueOf(jsonMap.get("startIndex").toString());
		Integer pageSize = jsonMap.get("pageSize") == null ? null : Integer.valueOf(jsonMap.get("pageSize").toString());
		
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<String, Object> sessionTokenUserInfo = (Map<String, Object>) (session.getAttribute("currentWXUserinfo"));
		if( sessionTokenUserInfo == null){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet = 
				this.frontAppService.getEventMsg(eventId, sessionTokenUserInfo, null, startIndex, pageSize);
		
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	

	/**
	 * 保存一个留言
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value =  "/doSaveEventMessage") 
	public void doSaveEventMessage(HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		if( jsonMap.get("eventId") == null && jsonMap.get("wxAppId") == null){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		Integer eventId = jsonMap.get("eventId") == null ? null : Integer.valueOf(jsonMap.get("eventId").toString());
		String wxAppId = jsonMap.get("wxAppId") == null ? null : jsonMap.get("wxAppId").toString();
		String msgText = jsonMap.get("msgText") == null ? "" : jsonMap.get("msgText").toString();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> imgList = jsonMap.get("imgList") == null ? null : (List<Map<String, Object>>)jsonMap.get("imgList");
		
		
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<String, Object> sessionTokenUserInfo = (Map<String, Object>) (session.getAttribute("currentWXUserinfo"));
		if( sessionTokenUserInfo == null){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet = 
				this.frontAppService.saveEventMsg(eventId, sessionTokenUserInfo, wxAppId, msgText, imgList);
		
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	/**
	 * 扫描所有众筹event，并更新众筹状态。如果需要退款额话，会生成一个退款文件到硬盘上
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value =  "/doScanAndUpdateEvents") 
	public void doScanAndUpdateEvents (HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();
		
		nbRet = this.frontAppService.scanAndUpdateEvents();
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	
	/**
	 * 保存或创建一个event
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	@RequestMapping(value =  "/doSaveEventModify") 
	public void doSaveEventModify (HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		if( jsonMap.get("eventId") == null){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		String openId = CommonHelper.getOpenIdFromSession(request);
		openId = openId == null ? "oecAVt4hSa4941GwbIWkLDhrTTwA" :openId;
	
		
		nbRet = this.frontAppService.loadWechatConfig(
				openId, 
				CommonHelper.getEventIdFromSession(request),
				false);
		
		
		if( !nbRet.isSuccess() ){
			nbRet.setError(ReturnCode.WECHAT_CONFIG_LOAD_ERROR);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		WechatConfigure wxCon = (WechatConfigure) nbRet.getObject();
		
		nbRet = 
				this.frontAppService.updateEvents(jsonMap, wxCon, openId);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	/**
	 * 获取我支持的event的列表
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value =  "/doGetMyPengList") 
	public void doGetMyPengList (HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		nbReturn nbRet = new nbReturn();

		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<String, Object> sessionTokenUserInfo = (Map<String, Object>) (session.getAttribute("currentWXUserinfo"));
		
		if( sessionTokenUserInfo == null){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet = 
				this.frontAppService.getMyPengList(CommonHelper.getOpenIdFromSession(request));
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	}
	
	@RequestMapping(value =  "/doCheckIfPayWindowOpen") 
	public void doCheckIfPayWindowOpen(HttpServletResponse response,HttpServletRequest request) throws Exception{
		nbReturn nbRet = new nbReturn();
		
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<String, Object> sessionTokenUserInfo = (Map<String, Object>) (session.getAttribute("currentWXUserinfo"));
		if( sessionTokenUserInfo == null){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		Integer eventId = CommonHelper.getObjectInteger(jsonMap.get("eventId"));
		Integer ruleId = CommonHelper.getObjectInteger(jsonMap.get("eventRuleId"));
		if( eventId == 0 || ruleId == 0){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
	
		nbRet = 
				this.frontAppService.checkIfPayWindowOpen(eventId,ruleId);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
	
	}
}
	

//String mchOrderCode = (String)param.get(_WechatKeyDefine.wxPayCallback.out_trade_no);
//String tradeId = (String)param.get(_WechatKeyDefine.wxPayCallback.transaction_id);

/**
 * 发送模板消息
 * @param response
 * @param request
 * @throws Exception
 */
//@RequestMapping(value =  "/doSendTplMessage") 
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