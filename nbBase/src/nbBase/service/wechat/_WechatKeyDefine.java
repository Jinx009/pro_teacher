package nbBase.service.wechat;


public class _WechatKeyDefine {
	
	public static final String errcode = "errcode";
	public static final String errmsg = "errmsg";
	public static final String scope_snsapi_base = "snsapi_base";
	public static final String scope_snsapi_userinfo = "snsapi_userinfo";
	
	public static final String isUser = "isUser";
	
	public static class eventType{
		public static Integer UNKNOWN = 0;
		public static Integer NORMALPAY = 1;
		public static Integer CROWDFUNDING = 2;
		public static Integer EARNEVENT = 3;
	}
	
	public static class eventCFSubType{
		public static Integer MATCH = 1;
	}
	
	public static class wxPayType{
		public static String JSAPI = "JSAPI";
		public static String NATIVE = "NATIVE";
		public static String APP = "APP";
	};
	
	public static class wxTplMsg{
		public static String template_id = "template_id";
		public static String touser = "touser";
		public static String data = "data";
		public static String first = "first";
		public static String value = "value";
		public static String color = "color";
		public static String keyword = "keyword";
		public static String keyword1 = "keyword1";
		public static String keyword2 = "keyword2";
		public static String keyword3 = "keyword3";
		public static String keyword4 = "keyword4";
		public static String keyword5 = "keyword5";
		
		
		public static String remark = "remark";
		public static String url = "url";
		public static String needJumpAuth = "needJumpAuth";
	};
	
	public static class wxUserInfo{
		public static String o_userInfo = "userInfo";
		public static String currentIdInDB = "currentIdInDB";
		public static String access_token = "access_token";
		public static String refresh_token = "refresh_token";
		public static String scope = "scope";
		public static String openid = "openid";
		public static String nickname = "nickname";
		public static String sex = "sex";
		public static String province = "province";
		public static String city = "city";
		public static String country = "country";
		public static String headimgurl = "headimgurl";
		public static String headImgSize = "headImgSize";
		public static String privilege = "privilege";
		public static String unionid = "unionid";
		public static String lastErrorcode = "lastErrorcode";
		public static String lastErrormsg = "lastErrormsg";
		public static String wxappid = "wxappid";
		public static String serverName = "serverName";
	}
	
	public static class pageSDK{
		
			public static String jsapi_ticket = "jsapi_ticket";
			public static String signature = "signature";
			public static String lastErrorcode = "lastErrorcode";
			public static String lastErrormsg = "lastErrormsg";
			public static String noncestr = "noncestr";
			public static String url = "url";
			public static String timestamp = "timestamp";
			public static String jsTicketExpireTime = "jsTicketExpireTime";
			public static String wxappid = "wxappid";
			public static String page_access_token = "page_access_token";
			public static String page_token_expireTime = "page_token_expireTime";
			public static String totalUserNumber = "totalUserNumber";
			public static String userList = "userList";
			public static String isOpenIdUser = "isOpenIdUser";
		
	}

//{
//	startIndex: 100,
//	pageSize: 20,
//	thisAmount:15,
//	totalAmount:500,
//	thisMode: 0,
//	events:
//	[
//	{
//	 eventTime:"2016年12月02日", 
//	 title:"【带摸】活动众筹，手感之旅", 
//	 picUrl:"http://cpc.people.com.cn/NMediaFile/2014/0218/MAIN201402181440396908425232572.jpg", 
//	 desc:"带摸（英文：demo）项目是为了方便用户快速理解和使用 捧场-大家筹 功能的演示项目，支付是真实的，感激之情是真实，但是权益是假设的，敬请知晓！",
//	 participater: "500",
//	 participaterProgress: "30",
//	 moneyAmount: "30 万",
//	 moneyProgress: "30",
//	 leftTime: "20 天",
//	 leftTimeSec : 1728000,
//	 eventId:1,
//	}
//	],
//}
	
	public static class nbEvent{
		public static String o_event = "event";
		public static String startIndex = "startIndex";
		public static String pageSize = "pageSize";
		public static String thisAmount = "thisAmount";
		public static String totalAmount = "totalAmount";
		public static String thisMode = "thisMode";
		public static String recomEvents = "recomEvents";
		
		public static String events = "events";
		public static String events_eventTime = "eventTime"; //活动条目创建时间
		public static String events_title = "title";
		public static String events_targetMoney = "targetMoney";
		public static String events_targetMember = "targetMember";
		public static String events_createrHeadImg = "createrHeadImg";
		public static String events_createrNickname = "createrNickname";
		
		
		public static String events_picUrl = "picUrl";
		public static String events_desc = "desc";
		public static String events_participater = "participater";
		public static String events_participaterProgress = "participaterProgress";
		public static String events_moneyAmount = "moneyAmount";
		public static String events_moneyProgress = "moneyProgress";
		public static String events_leftTime = "leftTime";
		public static String events_leftTimeSec = "leftTimeSec";
		public static String events_eventId = "eventId";
		public static String events_isSample = "isSample";
		public static String events_isSucceed = "isSucceed";
		public static String events_wxCardTitle = "wxCardTitle";
		public static String events_wxCardDesc = "wxCardDesc";
		public static String events_wxCardImgUrl = "wxCardImgUrl";
		public static String wxAppid = "wxAppid";
		public static String events_dateTime = "eventDateTime"; //活动执行日期
		public static String events_status = "status";
		public static String events_deadLine = "deadLine";
		public static String events_address = "eventAddress";
	}
	
	public static class nbEventRule{
		public static String o_rule = "rule";
		public static String rules = "rules";
		public static String rules_unitPrice = "unitPrice";
		public static String rules_ruleId = "ruleId";
		public static String rules_ruleDesc = "ruleDesc";
		public static String eventId = "eventId";
		public static String wxAppid = "wxAppid";
		public static String artUrl = "artUrl";
		public static String rules_longDesc = "longDesc";
		public static String rules_isNeedAddress = "isNeedAddress";
		public static String rules_isNeedBarCode = "isNeedBarCode";
		public static String rules_maxAmount = "maxAmount";
		public static String rules_minAmount = "minAmount";
		public static String rules_maxMember = "maxMember";
		public static String rules_minMember = "minMember";
		public static String rules_acceptManyCopy = "acceptManyCopy";
		public static String wxCardTitle = "wxCardTitle";
		public static String wxCardDesc = "wxCardDesc";
		public static String wxCardImgUrl = "wxCardImgUrl";
		public static String isSample = "isSample";
	}
	
	public static class wxPayCallback{
		public static String String_List = "String_List";
		public static String is_subscribe = "is_subscribe";
		public static String appid = "appid";
		public static String fee_type = "fee_type";
		public static String nonce_str = "nonce_str";
		public static String out_trade_no = "out_trade_no";
		public static String device_info = "device_info";
		public static String transaction_id = "transaction_id";
		public static String trade_type = "trade_type";
		public static String result_code = "result_code";
		public static String mch_id = "mch_id";
		public static String total_fee = "total_fee";
		public static String attach = "attach";
		public static String time_end = "time_end";
		public static String openid = "openid";
		public static String return_code = "return_code";
		public static String bank_type = "bank_type";
		public static String cash_fee = "cash_fee";
	}
	
	public static class zaCoreOrder{
		public static String o_order = "order";
		public static String id = "id";
		public static String order_date ="order_date";					
		public static String wx_user_id = "wx_user_id";					
		public static String front_user_id = "front_user_id";			
		public static String event_id = "event_id";						
		public static String event_rule_id = "event_rule_id";			
		public static String pay_wx_union_order_id = "pay_wx_union_order_id";
		public static String is_pay_succeed = "is_pay_succeed";			
		public static String pay_succeed_time = "pay_succeed_time";		
		public static String pay_method = "pay_method";					
		public static String qr_code_url = "qr_code_url";				
		public static String bar_code_url = "bar_code_url";				
		public static String user_realname = "user_realname";			
		public static String user_phone = "user_phone";					
		public static String user_address = "user_address";				
		public static String ordered_copies = "ordered_copies";			
		public static String total_fee = "total_fee";					
		public static String mch_order_code = "mch_order_code";			
		public static String is_front_succeed = "is_front_succeed";		
		public static String front_succeed_time = "front_succeed_time";	
		public static String wx_pay_callback_id = "wx_pay_callback_id";	

	}
	
	public static class getEventStatus{
		public static String eventId = "eventId";
		public static String wxAppId = "wxAppId";
		public static String leftTimeSec = "leftTimeSec";
		public static String isSample = "isSample";
		public static String deadlineDate = "deadLine";
		public static String gotTotalMoney = "gotTotalMoney";
		public static String gotPartiNum = "gotPartiNum";
		public static String eventStatus = "eventStatus";
		public static String partis = "parti";
		public static String parti_headimgUrl = "headImgUrl";
		public static String parti_nickname = "nickname";
		public static String parti_partiTime = "partiTime";
		public static String parti_partiMoney = "partiMoney";
		
	}
	
	public static class getEventMsg{
		public static String eventId = "eventId";
		public static String wxAppId = "wxAppId";
		public static String serverName = "serverName";
		public static String companyAvator = "companyAvator";
		public static String isSample = "isSample";
		public static String projectTitle = "projectTitle";
		public static String companyName = "companyName";
		public static String additionalDesc = "additionalDesc";
		public static String wxcardTitle = "wxcardTitle";
		public static String wxcardDesc = "wxcardDesc";
		public static String wxcardImgUrl = "wxcardImgUrl";
		
		public static String publicComments = "publicComments";
		public static String privateComments = "privateComments";
		
		
		public static String comment_userAvator = "userAvator";
		public static String comment_userNickname = "userNickname";
		public static String comment_comTime = "comTime";
		public static String comment_comments = "comments";
		public static String comment_comPic = "comPic";
		public static String comment_comPic_thum = "url";
		public static String comment_comPic_org = "orgUrl";
		
		public static String comment_companyReplyer = "companyReplyer";
		public static String comment_companyCommentTime = "companyCommentTime";
		public static String comment_companyComment = "companyComment";
		public static String comment_companyAward = "companyAward";
		
	}
	
	public static class doSaveEventModify{
		
		public static String eventId = "eventId";
		public static String isCFEvent = "isCFEvent";
		public static String eventTitle = "eventTitle";
		public static String isActive = "isActive";
		public static String eventDesc = "eventDesc";
		public static String targetMoney = "targetMoney";
		public static String targetMember = "targetMember";
		public static String deadlineDate = "deadlineDate";
		public static String deadlineDateInSec = "deadlineDateInSec";
		public static String eventDate = "eventDate";
		public static String eventAddress = "eventAddress";
		public static String eventDetailUrl = "eventDetailUrl";
		public static String cfEventType = "cfEventType";
		
		public static String wxAppId = "wxAppId";
		public static String openId = "openId";
		
		public static String rules = "rules";
		
		public static String rule_isToDeleted = "isToDeleted";
		public static String rule_ruleId = "ruleId";
		public static String rule_ruleTitle = "ruleTitle";
		public static String rule_unitPrice = "unitPrice";
		public static String rule_isNeedAddress = "isNeedAddress";
		public static String rule_ruleDesc = "ruleDesc";
		public static String rule_isCountInAmount = "isCountInAmount";
		public static String rule_isCountInMember = "isCountInMember";
		public static String rule_isAcceptableAfterDeadline = "isAcceptableAfterDeadline";
		public static Object eventSubType = "eventSubType";

	}
	
	public static class getMyPengList{
		public static String peng = "peng";
		public static String peng_event_title = "eventTitle";
		public static String peng_event_desc = "eventDesc";
		public static String peng_order_id = "orderId";
	}
	
	public static class getEarnEvents{
		public static String title = "title";
		public static String picUrl = "picUrl";
		public static String desc = "desc";
		public static String id = "id";
		public static String orderId = "orderId";
		public static String payAmountInCent = "payAmountInCent";
		public static String ruleId = "ruleId";
		public static String ruleTitle = "ruleTitle";
		public static String correctNumber = "correctNumber";
		public static String totalNumber = "totalNumber";
		public static String playedTimes = "playedTimes";
	}
}
