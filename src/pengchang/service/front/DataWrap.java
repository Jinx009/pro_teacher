package pengchang.service.front;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.StringUtils;

import nbBase.database.models.ZaFrontUserWx;
import nbBase.database.models.ZaFrontWxConfig;
import nbBase.helper.common.nbStringUtil;
import nbBase.service.wechat.WechatConfigure;
import nbBase.service.wechat._WechatKeyDefine;
import pengchang.dao.PCOrderDao;
import pengchang.database.models.PCCoreEvent;
import pengchang.database.models.PCCoreEventMsg;
import pengchang.database.models.PCCoreEventMsgImg;
import pengchang.database.models.PCCoreEventRule;
import pengchang.database.models.PCCoreOrder;
import pengchang.database.models.PCMatchEventResult;

public class DataWrap {

	/**
	 * 
	 * @param order
	 * @return
	 */
	public static Map<String, Object> wrapOrderMap(PCCoreOrder order){
		Map<String, Object> one = new HashMap<String, Object>();
		one.put(_WechatKeyDefine.zaCoreOrder.id, order.getId());											      
		one.put(_WechatKeyDefine.zaCoreOrder.order_date, nbStringUtil.DateTime2StrinF01(order.getOrderDate()));						      
		one.put(_WechatKeyDefine.zaCoreOrder.wx_user_id, order.getWxUserId());							      
		one.put(_WechatKeyDefine.zaCoreOrder.front_user_id, order.getFrontUserId());						      
		one.put(_WechatKeyDefine.zaCoreOrder.event_id, order.getEventId());								      
		one.put(_WechatKeyDefine.zaCoreOrder.event_rule_id, order.getEventRuleId());						      
		one.put(_WechatKeyDefine.zaCoreOrder.pay_wx_union_order_id, order.getPayWxUnionOrderId());		      
		one.put(_WechatKeyDefine.zaCoreOrder.is_pay_succeed, order.getIsPaySucceed());					      
		one.put(_WechatKeyDefine.zaCoreOrder.pay_succeed_time, order.getPaySucceedTime());				      
		one.put(_WechatKeyDefine.zaCoreOrder.pay_method, order.getPayMethod());							      
		one.put(_WechatKeyDefine.zaCoreOrder.qr_code_url, order.getQrCodeUrl());							      
		one.put(_WechatKeyDefine.zaCoreOrder.bar_code_url, order.getBarCodeUrl());						      
		one.put(_WechatKeyDefine.zaCoreOrder.user_realname, order.getUserRealname());						      
		one.put(_WechatKeyDefine.zaCoreOrder.user_phone, order.getUserPhone());							      
		one.put(_WechatKeyDefine.zaCoreOrder.user_address, order.getUserAddress());						      
		one.put(_WechatKeyDefine.zaCoreOrder.ordered_copies, order.getOrderedCopies());					      
		one.put(_WechatKeyDefine.zaCoreOrder.total_fee, order.getTotalFee());								      
		one.put(_WechatKeyDefine.zaCoreOrder.mch_order_code, order.getMchOrderCode());					      
		one.put(_WechatKeyDefine.zaCoreOrder.is_front_succeed, order.getIsFrontSucceed());				      
		one.put(_WechatKeyDefine.zaCoreOrder.front_succeed_time, order.getFrontSucceedTime());			      
		one.put(_WechatKeyDefine.zaCoreOrder.wx_pay_callback_id, order.getWxPayCallbackId());
		
		return one;
	}
	
	/**
	 * 
	 * @param event
	 * @return
	 */
	public static Map<String, Object> wrapEventMap(PCCoreEvent event, PCOrderDao orderDao){
		Map<String, Object> one = new HashMap<String, Object>();
		one.put(_WechatKeyDefine.nbEvent.events_eventTime, nbStringUtil.DateTime2StrinF01(event.getEventCreateDate()));
		one.put(_WechatKeyDefine.nbEvent.events_title, event.getEventTitle());
		one.put(_WechatKeyDefine.nbEvent.events_picUrl, event.getEventHeadImg());
		one.put(_WechatKeyDefine.nbEvent.events_desc, event.getEventShortDesc());
		one.put(_WechatKeyDefine.nbEvent.events_eventId, event.getId());
		one.put(_WechatKeyDefine.nbEvent.events_targetMoney, event.getTargetAmount()/100);
		one.put(_WechatKeyDefine.nbEvent.events_targetMember, event.getTargetMember());
		one.put(_WechatKeyDefine.nbEvent.events_createrNickname, event.getWxCreater().getWxNickname());
		one.put(_WechatKeyDefine.nbEvent.events_createrHeadImg, event.getWxCreater().getHeadImgUrl());
		
		int targetParti = event.getTargetMember();
		int currentParti = orderDao.findMemberCount(event.getId());
		
		one.put(_WechatKeyDefine.nbEvent.events_participater, currentParti);
		if( targetParti == 0){
			one.put(_WechatKeyDefine.nbEvent.events_participaterProgress, "n/a");
		}
		else{
			int proc = (int)(currentParti*100/targetParti);
			one.put(_WechatKeyDefine.nbEvent.events_participaterProgress, proc == 0 ? 1 : proc);
		}
		
		int targetAmount = event.getTargetAmount();
		int currentAmount = orderDao.findAmountCount(event.getId());
		one.put(_WechatKeyDefine.nbEvent.events_moneyAmount, currentParti);
		if( targetAmount == 0){
			one.put(_WechatKeyDefine.nbEvent.events_moneyProgress, "n/a");
		}
		else{
			int proc = (int)(currentAmount*100/targetAmount);
			one.put(_WechatKeyDefine.nbEvent.events_moneyProgress, proc == 0 ? 1 : proc);
		}
		
		one.put(_WechatKeyDefine.nbEvent.events_isSample, event.getIsEventSample() == null ? false : event.getIsEventSample());
		one.put(_WechatKeyDefine.nbEvent.events_isSucceed, event.getIsEventSucceed() == null ? false : event.getIsEventSucceed());

		Date deadLine = event.getEventDeadlineDate();
		long currentDate = Calendar.getInstance().getTime().getTime(); 
		one.put(_WechatKeyDefine.nbEvent.events_leftTime, nbStringUtil.convertLeftTimeToString((deadLine.getTime() - currentDate)/1000));
		one.put(_WechatKeyDefine.nbEvent.events_leftTimeSec, (deadLine.getTime() - currentDate)/1000);
		one.put(_WechatKeyDefine.nbEvent.events_wxCardTitle, event.getWxCardTitle());
		one.put(_WechatKeyDefine.nbEvent.events_wxCardImgUrl, event.getWxCardImgUrl());
		one.put(_WechatKeyDefine.nbEvent.events_wxCardDesc, event.getWxCardDesc());
		one.put(_WechatKeyDefine.nbEvent.events_status, event.getEventStatus());
		one.put(_WechatKeyDefine.nbEvent.events_dateTime, nbStringUtil.DateTime2StrinF02(event.getEventTime()));
		one.put(_WechatKeyDefine.nbEvent.events_deadLine, nbStringUtil.DateTime2StrinF02(event.getEventDeadlineDate()));
		one.put(_WechatKeyDefine.nbEvent.events_address, event.getEventAddress());
		
		return one;
	}
	
	/**
	 * 
	 * @param rule
	 * @return
	 */
	public static Map<String, Object> wrapEventRuleMap(PCCoreEventRule rule){
		Map<String, Object> one = new HashMap<String, Object>();
		one.put(_WechatKeyDefine.nbEventRule.rules_ruleId, rule.getId());
		one.put(_WechatKeyDefine.nbEventRule.rules_ruleDesc, rule.getRuleShortDesc());
		one.put(_WechatKeyDefine.nbEventRule.rules_unitPrice, rule.getRuleUnitFee());
		one.put(_WechatKeyDefine.nbEventRule.rules_longDesc, rule.getRuleAwardLongDesc());
		one.put(_WechatKeyDefine.nbEventRule.rules_isNeedAddress, rule.getRuleIsNeedAddress());
		one.put(_WechatKeyDefine.nbEventRule.rules_isNeedBarCode, rule.getRuleIsNeedBarcode());
		one.put(_WechatKeyDefine.nbEventRule.rules_maxAmount, rule.getRuleMaxAmount());
		one.put(_WechatKeyDefine.nbEventRule.rules_minAmount, rule.getRuleMinAmount());
		one.put(_WechatKeyDefine.nbEventRule.rules_maxMember, rule.getRuleMaxMember());
		one.put(_WechatKeyDefine.nbEventRule.rules_minMember, rule.getRuleMinMember());
		one.put(_WechatKeyDefine.nbEventRule.rules_acceptManyCopy, rule.getRuleIsCanManyCopy());
		return one;
	}

	/**
	 * 
	 * @param event
	 * @param wxc
	 * @param publicEventMsg
	 * @param privateEventMsg
	 * @return
	 */
	public static Map<String, Object> wrapEventMsgRet(
			PCCoreEvent event, 
			ZaFrontWxConfig wxc,
			List<PCCoreEventMsg> publicEventMsg, 
			List<PCCoreEventMsg> privateEventMsg) {
		
		Map<String, Object> retData = new HashMap<String, Object>();
		
		
		retData.put(_WechatKeyDefine.getEventMsg.eventId, event==null?0:event.getId());
		retData.put(_WechatKeyDefine.getEventMsg.wxAppId, wxc.getWxappid());
		retData.put(_WechatKeyDefine.getEventMsg.serverName, wxc.getServerName());
		retData.put(_WechatKeyDefine.getEventMsg.companyAvator, wxc.getCompanyLogoUrl());
		retData.put(_WechatKeyDefine.getEventMsg.isSample, event==null?false:( event.getIsEventSample()==null?false:event.getIsEventSample() ) );
		retData.put(_WechatKeyDefine.getEventMsg.projectTitle, event==null?wxc.getConfigName():event.getEventTitle());
		retData.put(_WechatKeyDefine.getEventMsg.companyName, wxc.getCompanyName());
		retData.put(_WechatKeyDefine.getEventMsg.additionalDesc, "-");
		retData.put(_WechatKeyDefine.getEventMsg.wxcardTitle, event.getWxCardTitle());
		retData.put(_WechatKeyDefine.getEventMsg.wxcardDesc, event.getWxCardDesc());
		retData.put(_WechatKeyDefine.getEventMsg.wxcardImgUrl, event.getWxCardImgUrl());
		retData.put(_WechatKeyDefine.getEventMsg.publicComments, wrapPureEventMsg(publicEventMsg));
		retData.put(_WechatKeyDefine.getEventMsg.privateComments, wrapPureEventMsg(privateEventMsg));
		
		
		return retData;
	}
	
	/**
	 * 
	 * @param msgList
	 * @return
	 */
	public static List<Map<String, Object>> wrapPureEventMsg(List<PCCoreEventMsg> msgList){
		List<Map<String, Object>> msgMapList = new ArrayList<Map<String, Object>>();
		for( PCCoreEventMsg msg : msgList ){
			Map<String, Object> msgMap = new HashMap<String, Object>();
			msgMap.put(_WechatKeyDefine.getEventMsg.comment_userAvator, msg.getZaFrontUserWx().getHeadImgUrl());
			msgMap.put(_WechatKeyDefine.getEventMsg.comment_userNickname, msg.getZaFrontUserWx().getWxNickname());
			msgMap.put(_WechatKeyDefine.getEventMsg.comment_comTime, nbStringUtil.DateTime2StrinF03(msg.getMessageDate()));
			msgMap.put(_WechatKeyDefine.getEventMsg.comment_comments, msg.getMessage());
			
			List<Map<String, Object>> imgMapList = new ArrayList<Map<String, Object>>();
			for( PCCoreEventMsgImg img : msg.getZaCoreEventMsgImgs() ){
				Map<String, Object> imgMap = new HashMap<String, Object>();
				imgMap.put(_WechatKeyDefine.getEventMsg.comment_comPic_thum, img.getThumUrl());
				imgMap.put(_WechatKeyDefine.getEventMsg.comment_comPic_org, img.getImgUrl());
				imgMapList.add(imgMap);
			}
			
			msgMap.put(_WechatKeyDefine.getEventMsg.comment_comPic, imgMapList);
			
			msgMap.put(_WechatKeyDefine.getEventMsg.comment_companyReplyer, msg.getOwnerReplier());
			msgMap.put(_WechatKeyDefine.getEventMsg.comment_companyComment, msg.getOwnerReply());
			msgMap.put(_WechatKeyDefine.getEventMsg.comment_companyCommentTime, nbStringUtil.DateTime2StrinF03(msg.getOwnerReplyDate()));
			msgMap.put(_WechatKeyDefine.getEventMsg.comment_companyAward, msg.getOwnerAward());
			
			msgMapList.add(msgMap);
		}
		
		return msgMapList;
	}

	public static List<Map<String, Object>> wrapDoGetEarnEvents(List<PCCoreEvent> events) {
		List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();
		
		for( PCCoreEvent event : events){
			Map<String, Object> oneEvent = new HashMap<String, Object>();
			oneEvent.put(_WechatKeyDefine.getEarnEvents.id, event.getId());
			oneEvent.put(_WechatKeyDefine.getEarnEvents.desc, event.getEventShortDesc());
			oneEvent.put(_WechatKeyDefine.getEarnEvents.title, event.getEventTitle());
			oneEvent.put(_WechatKeyDefine.getEarnEvents.picUrl, event.getEventHeadImg());
			eventList.add(oneEvent);
		}

		return eventList;
	}
	
	public static Map<String, Object> wrapDoCheckUserStatusOfThePlay(PCCoreOrder order, PCCoreEvent event, PCCoreEventRule rule) {
		if( order != null && event.getId() != order.getEventId() )
			return null;
		if( order != null && order.getEventRuleId() != rule.getId() )
			return null;
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put(_WechatKeyDefine.getEarnEvents.id, event.getId());
		ret.put(_WechatKeyDefine.getEarnEvents.title, event.getEventShortDesc());
		if( order != null){
			ret.put(_WechatKeyDefine.getEarnEvents.orderId, order.getId());
		}
		ret.put(_WechatKeyDefine.getEarnEvents.ruleId, rule.getId());
		ret.put(_WechatKeyDefine.getEarnEvents.ruleTitle, rule.getRuleShortDesc());
		ret.put(_WechatKeyDefine.getEarnEvents.payAmountInCent, rule.getRuleUnitFee());
		ret.put(_WechatKeyDefine.getEarnEvents.playedTimes, (order == null || order.getEarnPlayTimes() == null) ? 0 : order.getEarnPlayTimes());
		
		return ret;
	}

	public static Map<String, Object> wrapDoGetEventInfo(
			PCCoreEvent event, 
			ZaFrontUserWx wxUser, 
			WechatConfigure wxcon,
			Integer partiNumber,
			Integer amountNumber,
			List<PCCoreEventMsgImg> imgList,
			List<PCCoreEventMsg> msgList,
			Boolean isUserPlayer) {
		
		Map<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("eventId", event.getId());
		ret.put("isSample", event.getIsEventSample());
		ret.put("openId", wxUser == null ? null : wxUser.getWxOpenId());
		ret.put("wxAppId", wxcon.wxappid);
		ret.put("wxCardTitle", event.getWxCardTitle());
		ret.put("wxCardDesc", event.getWxCardDesc());
		ret.put("wxCardImgUrl", event.getWxCardImgUrl());
		ret.put("eventTitle", event.getEventTitle());
		ret.put("eventType", event.getEventType());
		ret.put("eventSubType", event.getEventSubType());
		ret.put("eventStatus", event.getEventStatus());
		ret.put("callerHeadImgUrl", event.getWxCreater().getHeadImgUrl());
		ret.put("callerNickname", event.getWxCreater().getWxNickname());
		ret.put("eventAddress", event.getEventAddress());
		ret.put("eventTime",nbStringUtil.DateTime2StrinF02(event.getEventTime()));
		ret.put("eventPartiNumber", partiNumber);
		ret.put("eventAmount", amountNumber);
		ret.put("eventType", event.getEventType());
		ret.put("eventSubType", event.getEventSubType());
		ret.put("eventIntroduce", event.getEventDetailUrl());
		ret.put("eventShortDesc", event.getEventShortDesc());
		ret.put("registerDeadline", nbStringUtil.DateTime2StrinF02(event.getEventDeadlineDate()));
		
		List<Map<String, Object>> eventImags = new ArrayList<Map<String, Object>>();
		for( PCCoreEventMsgImg img : imgList){
			Map<String, Object> oneImg = new HashMap<String, Object>();
			oneImg.put("thum", img.getThumUrl());
			oneImg.put("pic", img.getImgUrl());
			eventImags.add(oneImg);
		}
		ret.put("eventImages", eventImags);
		
		List<Map<String, Object>> rules = new ArrayList<Map<String, Object>>();
		for( PCCoreEventRule rule : event.getZaCoreEventRules()){
			if( rule.getRuleIsDeleted() )
				continue;
			Map<String, Object> oneRule = new HashMap<String, Object>();
			oneRule.put("unitPrice", rule.getRuleUnitFee());
			oneRule.put("ruleId", rule.getId());
			oneRule.put("ruleDesc", rule.getRuleShortDesc());
			rules.add(oneRule);
		}
		ret.put("rules", rules);
		
		List<Map<String, Object>> popComments = new ArrayList<Map<String, Object>>();
		for( PCCoreEventMsg msg : msgList){
			Map<String, Object> oneMsg = new HashMap<String, Object>();
			oneMsg.put("text", msg.getMessage());
			popComments.add(oneMsg);
		}
		ret.put("popComments", popComments);
		ret.put("isUserPlayer", isUserPlayer);
		
		
		return ret;
	}

	public static Object wrapDoGetMatchEventResult(
			PCCoreEvent event,
			List<PCMatchEventResult> matchResultList, 
			List<ZaFrontUserWx> playerWxList,
			WechatConfigure wxCon,
			ZaFrontUserWx wxUser,
			Boolean isUserPlayer) {
		
		Map<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("eventId", event.getId());
		ret.put("isSample", event.getIsEventSample());
		ret.put("openId", wxUser == null ? null : wxUser.getWxOpenId());
		ret.put("isUserPlayer", isUserPlayer);
		ret.put("wxAppId", wxCon.wxappid);
		ret.put("wxCardTitle", event.getWxCardTitle());
		ret.put("wxCardDesc", event.getWxCardDesc());
		ret.put("wxCardImgUrl", event.getWxCardImgUrl());
		ret.put("myPlayerId", wxUser == null ? 0 : wxUser.getId());
		
		List<Map<String, Object>> matchResult = new ArrayList<Map<String, Object>>();
		for( PCMatchEventResult result : matchResultList){
			Map<String, Object> oneResult = new HashMap<String, Object>();
			Map<String, Object> playerA = new HashMap<String, Object>();
			Map<String, Object> playerB = new HashMap<String, Object>();
			
			playerA.put("wxId", result.getPlayerA().getId());
			playerA.put("headImgUrl", result.getPlayerA().getHeadImgUrl());
			playerA.put("nickname", result.getPlayerA().getWxNickname());
			oneResult.put("playerA", playerA);
			
			playerB.put("wxId", result.getPlayerB().getId());
			playerB.put("headImgUrl", result.getPlayerB().getHeadImgUrl());
			playerB.put("nickname", result.getPlayerB().getWxNickname());
			oneResult.put("playerB", playerB);
			
			oneResult.put("result", result.getPlayResult());
			oneResult.put("isPlayerBConfirmed", result.getPlayBConfirmed());
			oneResult.put("id", result.getId());
			
			if( result.getPlayBConfirmed() || //如果双方都已经confirm了
				(wxUser != null && result.getPlayerA().getId() == wxUser.getId() )){ //或者对方还没有confirm，但是query的发起人是也是发起confirm的playerA的话
				matchResult.add(oneResult);
			}
		}
		ret.put("matchResult", matchResult);
	
		List<Map<String, Object>> playerList = new ArrayList<Map<String, Object>>();
		for( ZaFrontUserWx player : playerWxList){
			Map<String, Object> onePlayer = new HashMap<String, Object>();
			
			onePlayer.put("wxId", player.getId());
			onePlayer.put("headImgUrl", player.getHeadImgUrl());
			onePlayer.put("nickname",player.getWxNickname());
			playerList.add(onePlayer);
		}
		ret.put("playerList", playerList);
		
		return ret;
		
	}

	public static List<Map<String, Object>> adminWrapDoGetWxConfigList(List<ZaFrontWxConfig> wxConfigList) {
		
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		
		for( ZaFrontWxConfig wxConfig : wxConfigList){
			Map<String, Object> mapConfig = new HashMap<String, Object>();
			mapConfig.put("id", wxConfig.getId());
			mapConfig.put("configName", StringUtils.isNullOrEmpty(wxConfig.getConfigName()) ?  "-" :  wxConfig.getConfigName());
			mapConfig.put("companyLogoUrl", StringUtils.isNullOrEmpty(wxConfig.getCompanyLogoUrl()) ? " " : wxConfig.getCompanyLogoUrl()  );
			mapConfig.put("companyName", StringUtils.isNullOrEmpty(wxConfig.getCompanyName()) ? "-" : wxConfig.getCompanyName());
			ret.add(mapConfig);
		}
		
		return ret;
		
	}

	public static Map<String, Object> adminWrapDoGetWxConfigDetail(ZaFrontWxConfig wxConfig) {
		
		Map<String, Object> mapConfig = new HashMap<String, Object>();

  	   
		mapConfig.put("id", wxConfig.getId());
		mapConfig.put("configName", StringUtils.isNullOrEmpty(wxConfig.getConfigName()) ?  "-" :  wxConfig.getConfigName());
		mapConfig.put("companyLogoUrl", StringUtils.isNullOrEmpty(wxConfig.getCompanyLogoUrl()) ? " " : wxConfig.getCompanyLogoUrl()  );
		mapConfig.put("companyName", StringUtils.isNullOrEmpty(wxConfig.getCompanyName()) ? "-" : wxConfig.getCompanyName());
		
		mapConfig.put("payKey", wxConfig.getPayKey());
		mapConfig.put("mchId", wxConfig.getMchId());
		mapConfig.put("wxappid", wxConfig.getWxappid());
		mapConfig.put("certFileP12", wxConfig.getCertFile_p12());
		mapConfig.put("serverDefaultIp", wxConfig.getServerDefaultIp());
		mapConfig.put("appSecret", wxConfig.getAppSecret());
		mapConfig.put("orderDefault", wxConfig.getOrderDefaultExpireTime());
		mapConfig.put("wxPayNotifyUrl", wxConfig.getWxPayNotifyUrl());
		mapConfig.put("encodingAesKey", wxConfig.getEncodingAesKey());
		mapConfig.put("isActive", wxConfig.getIsActive());
		mapConfig.put("isDefault", wxConfig.getIsDefault());
		mapConfig.put("serverName", wxConfig.getServerName());
		mapConfig.put("tplmsgPaySuccess", wxConfig.getTplmsgPaySuccess());
		mapConfig.put("resourcePath", wxConfig.getResourcePath());
		mapConfig.put("resourceBrowsPath", wxConfig.getResourceBrowsPath());
		mapConfig.put("certP12Bin", wxConfig.getCertP12Bin());
		mapConfig.put("tplmsgCfResult", wxConfig.getTplmsgCfResult());
		mapConfig.put("tplmsgMatchResultConfirm", wxConfig.getTplmsgMatchResultConfirm());
		
		
		return mapConfig;
	}

}
