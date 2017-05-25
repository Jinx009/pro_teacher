package pengchang.service.front;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.BarcodeFormat;

import nbBase.dao.ZaUserWxDao;
import nbBase.dao.ZaWxPayCallbackDao;
import nbBase.dao.ZaWxPayUnionOrderDao;
import nbBase.dao.common._SendAwardLogDao;
import nbBase.dao.common._WxConfigDao;
import nbBase.database.models.ZaFrontUserWx;
import nbBase.database.models.ZaFrontWxConfig;
import nbBase.database.models.ZaPayWxUnionOrder;
import nbBase.database.models.ZaWxPayCallback;
import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbReturn.ReturnCode;
import nbBase.helper.common.nbStringUtil;
import nbBase.service.common.DataWrapUtil;
import nbBase.service.common.If_ErrorCheckService;
import nbBase.service.common.WxCommonService;
import nbBase.service.wechat.WechatConfigure;
import nbBase.service.wechat.WechatMchPay;
import nbBase.service.wechat.WechatPageSDK;
import nbBase.service.wechat._WechatKeyDefine;
import nbBase.service.wechat._WechatUtils;
import nbBase.service.wechat._WxIF_Configure;
import pengchang.dao.PCEventDao;
import pengchang.dao.PCEventMsgDao;
import pengchang.dao.PCEventMsgImgDao;
import pengchang.dao.PCEventRuleDao;
import pengchang.dao.PCMatchEventResultDao;
import pengchang.dao.PCOrderDao;
import pengchang.database.models.PCCoreEvent;
import pengchang.database.models.PCCoreEventMsg;
import pengchang.database.models.PCCoreEventMsgImg;
import pengchang.database.models.PCCoreEventRule;
import pengchang.database.models.PCCoreOrder;
import pengchang.database.models.PCMatchEventResult;

@Service("frontAppService")
public class ApplicationServiceImpl implements ApplicationService, If_ErrorCheckService, _WxIF_Configure {
	
	@Autowired
	PCEventDao eventDao;
	
	@Autowired
	PCEventRuleDao eventRuleDao;
	
	@Autowired
	PCOrderDao orderDao ;
	
	@Autowired
	ZaUserWxDao userWxDao;
	
	@Autowired
	ZaWxPayUnionOrderDao wxPayUnionOrderDao;
	
	@Autowired
	_WxConfigDao wxConfigDao;
	
	@Autowired
	_SendAwardLogDao sendAwardLogDao;
	
	@Autowired
	ZaWxPayCallbackDao wxPayCallbackDao;
	
	@Autowired
	PCEventMsgDao eventMsgDao;
	
	@Autowired
	PCEventMsgImgDao eventMsgImgDao;
	
	@Autowired
	WxCommonService wxCommonService;
	
	@Autowired
	PCMatchEventResultDao matchEventResultDao;
	
	
	private Integer lastErrCode;
	private String lastErrMsg;
	
	@Override
	public Integer getLastErrCode() {
		return lastErrCode;
	}

	@Override
	public String getLastErrMsg() {
		return lastErrMsg;
	}

	@Override
	public void SetLastError(Integer code, String msg) {
		this.lastErrCode = code;
		this.lastErrMsg = msg;
	}


	@Override
	public nbReturn createUnionOrder(
			String orderBody, 
			String orderDetail, 
			String attach, 
			Integer totalFee, 
			String clientIp, 
			Integer eventId, 
			Integer eventRuleId, 
			Integer copies,
			String realname,
			String userPhone,
			String userAddress,
			WechatConfigure wxCon,
			Map<String, Object> userInfo) throws Exception {
		
		nbReturn nbRet = new nbReturn();
		
		if( userInfo == null){
			nbRet.setError(ReturnCode.SESSION_LOST);
			return nbRet;
		}
		
		PCCoreEvent event = this.eventDao.find(eventId);
		PCCoreEventRule rule = this.eventRuleDao.find(eventRuleId);
		if( event == null || rule == null || rule.getEventId() != event.getId() ){
			nbRet.setError(ReturnCode.REQUESTED_EVENT_OR_RULE_WRONG);
			return nbRet;
		}
		
		if( ((String)userInfo.get("openid")).equals("oecAVt4hSa4941GwbIWkLDhrTTwA") ){
			totalFee = 1;
		}
		else
		{
			Integer unitPrice = rule.getRuleUnitFee();
			Boolean acceptManyCopy = rule.getRuleIsCanManyCopy();
			if( acceptManyCopy && unitPrice != 0 ){
				if( (copies * unitPrice) != totalFee ){
					nbRet.setError(ReturnCode.REQUESTED_FEE_WRONG);
					return nbRet;
				}
			}else{
				if( unitPrice != 0 ){
					totalFee =unitPrice;
				}
			}
		}
		
		orderBody = event.getEventTitle();
		orderDetail = rule.getRuleShortDesc();
		attach = "";
		if( rule.getRuleIsCanManyCopy() && rule.getRuleUnitFee() != 0 ){
			attach = "共 "+copies+" 份";
		}

		WechatMchPay wechatPay = new WechatMchPay(wxCon);
		
		ZaPayWxUnionOrder wxUnionOrder = new ZaPayWxUnionOrder();
		wxUnionOrder.setWxConfigureId( wxCon.idInDB );
		wxUnionOrder.setWxDeviceInfo("n/a");
		wxUnionOrder.setWxFeeType("CNY");
		wxUnionOrder.setWxGoodsTag("WXG");
		wxUnionOrder.setWxNonceStr( _WechatUtils.createUniqueString());
		wxUnionOrder.setWxOrderAttach(attach);
		wxUnionOrder.setWxOrderBody(orderBody);
		wxUnionOrder.setWxOrderDetail(orderDetail);
		wxUnionOrder.setWxOutTradeNo(_WechatUtils.createBillNumber(wxCon.mchId));
		wxUnionOrder.setWxProductId(eventId*1000000+eventRuleId);
		wxUnionOrder.setWxSpbillCreateIp(clientIp == null ? wxCon.server_ip : clientIp);
		wxUnionOrder.setWxTradeType(_WechatKeyDefine.wxPayType.JSAPI);
		wxUnionOrder.setWxTotalFee(totalFee);
		wxUnionOrder.setWxTimeStart( _WechatUtils.StringToTime(_WechatUtils.getTimeString(0l)));
		wxUnionOrder.setWxTimeExpire(_WechatUtils.StringToTime(_WechatUtils.getTimeString(wxCon.orderDefaultExpireTime)));
		wxUnionOrder = wxPayUnionOrderDao.save(wxUnionOrder);
		System.out.println("\r\nunion order id："+wxUnionOrder.getId()+" 被创建了！\r\n");
		
		PCCoreOrder order = new PCCoreOrder();
		
		order.setBarCodeUrl(null);
		order.setFrontUserId(null);
		order.setIsPaySucceed(false);
		order.setIsFrontSucceed(false);
		order.setMchOrderCode(wxUnionOrder.getWxOutTradeNo());
		order.setOrderDate(wxUnionOrder.getWxTimeStart());
		order.setOrderedCopies(copies);
		order.setPayMethod(1);//wx jssdk pay
		order.setPaySucceedTime(null);
		order.setPayWxUnionOrderId(wxUnionOrder.getId());
		order.setQrCodeUrl(null);
		order.setTotalFee(totalFee);
		order.setWxUserId((Integer)userInfo.get(_WechatKeyDefine.wxUserInfo.currentIdInDB));
		order.setUserAddress(userAddress);
		order.setUserPhone(userPhone);
		order.setUserRealname(realname);
		order.setEventId(eventId);
		order.setEventRuleId(eventRuleId);
		
		order = orderDao.save(order);
		
		System.out.println("开始压制二维码和条码图片【"+wxCon.resourcePath+"】");
		try{
			if( wxCon != null && !nbStringUtil.isNullEmpty(wxCon.resourcePath) ){
				
				String code39 = wxCon.mchId.substring(wxCon.mchId.length() - 5);
				code39 += String.format("%05d", order.getId());
				code39 += String.format("%05d", event.getId());
				code39 += order.getMchOrderCode().substring(order.getMchOrderCode().length()-5);
				
				String barCodeFilename = "barcode"+order.getId()+".jpg";
				
				System.out.println("开始压制条形码图片");
				
				CommonHelper.codeEncoder( wxCon.resourcePath+"/barcodes/"+barCodeFilename,
										code39, BarcodeFormat.CODE_39, 300, 50, null);
				System.out.println("压制条形码图片结束");
				
				order.setBarCodeUrl(wxCon.resourceBrowsPath + "/barcodes/" + barCodeFilename);
				
				String code128 = 
						order.getMchOrderCode() +
						"--" + 
						order.getWxUserId() + 
						"--" + 
						wxCon.wxappid + 
						"--" + 
						nbStringUtil.DateTime2StrinF01(order.getOrderDate()) +
						"--";
				
				String qrCodeFilename = "qrcode"+order.getId()+".jpg";
				
				System.out.println("开始压制二维码图片");
				CommonHelper.codeEncoder(  wxCon.resourcePath+"/qrcodes/"+qrCodeFilename,
										code128, BarcodeFormat.QR_CODE, 320, 320, null);
				System.out.println("压制二维码图片结束");
				
				order.setQrCodeUrl(wxCon.resourceBrowsPath + "/qrcodes/" + qrCodeFilename);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println("抓到Exception了");
		}
		
		order = orderDao.update(order);
			
		
		if( wechatPay.createUnionOrder(wxUnionOrder, userInfo) ){
			nbRet.setObject(wechatPay.getWxPayParam());
			TreeMap<String, Object> wxPayParam = wechatPay.getWxPayParam();
			wxUnionOrder = wxPayUnionOrderDao.find(wxUnionOrder.getId());
			System.out.println("wxUnionOrder = "+wxUnionOrder+"wxPayParam="+wxPayParam);
			wxUnionOrder.setWxPrepayId((String)wxPayParam.get(WechatMchPay.parmaterKeys.prepay_id));
			wxPayUnionOrderDao.update(wxUnionOrder);	
		}
		else{
			nbRet.setError(Long.valueOf(wechatPay.getLastErrcode()), wechatPay.getLastErrmsg());
		}
		
		return nbRet;
	}
	

	
	@Override
	public nbReturn getEventList(
			Integer startIndex, 
			Integer pageSize, 
			Integer orderMode, 
			Integer recomEventId, 
			String createrOpenId,
			WechatConfigure wxCon) {
		
		nbReturn nbRet = new nbReturn();
		
		if( startIndex == null ) startIndex = 0;
		if( orderMode == null ) orderMode = 0;
		if( recomEventId == null ) recomEventId = 0;
		
		
		List<PCCoreEvent> events = eventDao.find(
				startIndex, 
				pageSize,
				orderMode, 
				createrOpenId,
				null,
				_WechatKeyDefine.eventType.CROWDFUNDING);
		
		Map<String, Object> retData = new HashMap<String, Object>();
		retData.put(_WechatKeyDefine.nbEvent.startIndex, startIndex);
		retData.put(_WechatKeyDefine.nbEvent.pageSize, pageSize);
		retData.put(_WechatKeyDefine.nbEvent.thisAmount, Integer.valueOf(retData.size()));
		retData.put(_WechatKeyDefine.nbEvent.totalAmount, eventDao.findAllCount(_WechatKeyDefine.eventType.CROWDFUNDING));
		retData.put(_WechatKeyDefine.nbEvent.thisMode, orderMode);
		retData.put(_WechatKeyDefine.nbEvent.wxAppid, wxCon == null ? null : wxCon.wxappid);
		
		
		List<Map<String,Object>> eveLists = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> recomLists = new ArrayList<Map<String,Object>>();
		
		
		for( PCCoreEvent event : events){
			
			if( event.getId() == recomEventId.intValue() ){
				recomLists.add(DataWrap.wrapEventMap(event, this.orderDao));
			}else{
				eveLists.add(DataWrap.wrapEventMap(event, this.orderDao));
			}
			
		}
		
		retData.put(_WechatKeyDefine.nbEvent.recomEvents, recomLists);
		retData.put(_WechatKeyDefine.nbEvent.events, eveLists);
		nbRet.setObject(retData);
		return nbRet;
	}

	@Deprecated
	@Override
	public nbReturn getEvent(Integer eventId, WechatConfigure wxCon) {
		nbReturn nbRet = new nbReturn();
		
		PCCoreEvent event = eventDao.find(eventId);
		if( event == null ){
			nbRet.setError(ReturnCode.GENERAL_ERROR);
			return nbRet;
		}
		
		List<PCCoreEventRule> rules = this.eventRuleDao.findByEventId(eventId);
		
		List<Map<String,Object>> ruleList = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> retData = new HashMap<String, Object>();
		retData.put(_WechatKeyDefine.nbEventRule.wxAppid, wxCon == null ? null : wxCon.wxappid);
		retData.put(_WechatKeyDefine.nbEventRule.artUrl, event.getEventDetailUrl().toLowerCase().startsWith("http") ? event.getEventDetailUrl() : "simpleDetail");
		retData.put(_WechatKeyDefine.nbEventRule.eventId, event.getId());
		retData.put(_WechatKeyDefine.nbEventRule.wxCardTitle, event.getWxCardTitle());
		retData.put(_WechatKeyDefine.nbEventRule.wxCardImgUrl, event.getWxCardImgUrl());
		retData.put(_WechatKeyDefine.nbEventRule.wxCardDesc, event.getWxCardDesc());
		retData.put(_WechatKeyDefine.nbEventRule.isSample, event.getIsEventSample() == null ? false : event.getIsEventSample());
		
		
		for( PCCoreEventRule rule : rules){
			ruleList.add(DataWrap.wrapEventRuleMap(rule));
		}
		
		retData.put(_WechatKeyDefine.nbEventRule.rules, ruleList);
		nbRet.setObject(retData);
		
		return nbRet;
	}
	
	@Override
	public nbReturn getEventDetailByWxCreater(Integer eventId, Integer eventRuleId, String openId) {
		nbReturn nbRet = new nbReturn();
		if( openId == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		PCCoreEvent event = this.eventDao.find(eventId);
		
		if( event == null){
			nbRet.setError(ReturnCode.PARAMETER_PHARSE_ERROR);
			return nbRet;
		}
		if( !event.getWxCreater().getWxOpenId().equals(openId) ){
			nbRet.setError(ReturnCode.NOT_CREATER);
			return nbRet;
		}
		
		nbRet.setObject(event);
		
		return nbRet;
	}

	@Override
	public nbReturn getEventDetail(Integer eventId, Integer eventRuleId, WechatConfigure wxCon) {
		nbReturn nbRet = new nbReturn();
		PCCoreEvent event = this.eventDao.find(eventId);
		if( event == null){
			nbRet.setError(ReturnCode.GENERAL_ERROR);
			return nbRet;
		}
		
		List<PCCoreEventRule> ruleList = new ArrayList<PCCoreEventRule>();
		if( eventRuleId != null ){
			PCCoreEventRule rule = this.eventRuleDao.find(eventRuleId);
			if( rule == null ){
				nbRet.setError(ReturnCode.GENERAL_ERROR);
				return nbRet;
			}
			ruleList.add(rule);
		}else{
			ruleList = event.getZaCoreEventRules();
		}
		
		
		Map<String, Object> eventMap = DataWrap.wrapEventMap(event, this.orderDao);
		
		List<Map<String, Object>> ruleMapList = new ArrayList<Map<String, Object>>();
		
		for(PCCoreEventRule rule : ruleList){
			ruleMapList.add(DataWrap.wrapEventRuleMap(rule));
		}
		eventMap.put(_WechatKeyDefine.nbEventRule.rules, ruleMapList);
		eventMap.put(_WechatKeyDefine.nbEventRule.wxAppid, wxCon == null ? null : wxCon.wxappid);	
		nbRet.setObject(eventMap);
		
//		nbRet.setObject(event);
		
		return nbRet;
	}

	
	@Override
	public nbReturn setFrontPaySucceed(String prepayId, WechatConfigure wxCon) {
		nbReturn nbRet = new nbReturn();
		
		PCCoreOrder order = this.orderDao.findByPrePayId(prepayId);
		if( order == null ){
			nbRet.setError(ReturnCode.GENERAL_ERROR);
			return nbRet;
		}
		
		order.setIsFrontSucceed(true);
		order.setFrontSucceedTime(Calendar.getInstance().getTime());
		
		this.orderDao.update(order);
		
		return nbRet;
	}

	private String object2String(Object obj){
		if( obj != null )
			return obj.toString();
		return null;
	}
	
	private Integer object2Integer(Object obj){
		if( obj != null )
			return Integer.valueOf(obj.toString());
		return null;
	}
	
	/**
	 * 返回 orderid
	 */
	@Override
	public Map<String, Object> savePayCallback(Map<String, Object> param) {
		String tmp;
		Map<String, Object> retData = new HashMap<String, Object>();
		ZaWxPayCallback wpc = this.wxPayCallbackDao.findByTransactionId( object2String(param.get(_WechatKeyDefine.wxPayCallback.transaction_id)) );
		boolean isUpdate = true;
		if( wpc == null ){
			isUpdate = false;
			wpc = new ZaWxPayCallback();
		}
		
		wpc.setAppid(		object2String(param.get(_WechatKeyDefine.wxPayCallback.appid))  	);
		wpc.setAttach(		object2String(param.get(_WechatKeyDefine.wxPayCallback.attach))		);
		wpc.setBankType(	object2String(param.get(_WechatKeyDefine.wxPayCallback.bank_type))	);
		wpc.setCashFee(		object2Integer(param.get(_WechatKeyDefine.wxPayCallback.cash_fee))	);
		wpc.setDeviceInfo(	object2String(param.get(_WechatKeyDefine.wxPayCallback.device_info))	);
		wpc.setFeeType(		object2String(param.get(_WechatKeyDefine.wxPayCallback.fee_type))	);
		
		tmp = object2String(param.get(_WechatKeyDefine.wxPayCallback.is_subscribe));
		wpc.setIsSubscribe(	tmp.toUpperCase().equals("Y") ? true : false	);
		
		wpc.setMchId(		object2String(param.get(_WechatKeyDefine.wxPayCallback.mch_id))		);
		wpc.setNonceStr(	object2String(param.get(_WechatKeyDefine.wxPayCallback.nonce_str))	);
		wpc.setOpenid(		object2String(param.get(_WechatKeyDefine.wxPayCallback.openid)		));
		wpc.setOutTradeNo(	object2String(param.get(_WechatKeyDefine.wxPayCallback.out_trade_no))	);
		wpc.setResultCode(	object2String(param.get(_WechatKeyDefine.wxPayCallback.result_code))	);
		wpc.setReturnCode(	object2String(param.get(_WechatKeyDefine.wxPayCallback.return_code))	);
		
		tmp = object2String(param.get(_WechatKeyDefine.wxPayCallback.time_end));
		if( tmp != null){
			wpc.setTimeEnd(	nbStringUtil.String2DateTimeF02(tmp));
		}
		
		wpc.setTotalFee(	object2Integer(param.get(_WechatKeyDefine.wxPayCallback.total_fee))		);
		wpc.setTradeType(	object2String(param.get(_WechatKeyDefine.wxPayCallback.trade_type))		);
		wpc.setTransactionId(object2String(param.get(_WechatKeyDefine.wxPayCallback.transaction_id))	);
		
		if( !isUpdate )
			wpc = this.wxPayCallbackDao.save(wpc);
		else
			wpc = this.wxPayCallbackDao.update(wpc);
		
		retData.put("ZaWxPayCallback", wpc);
		
		PCCoreOrder order = this.orderDao.findByOutTradeCode(wpc.getOutTradeNo());
		
		if( order == null){
			System.out.println("["+wpc.getOutTradeNo()+"] 对应的订单没有找到！");
			return retData;
		}
		
		order.setWxPayCallbackId(wpc.getId());
		if( wpc.getResultCode().toUpperCase().equals("SUCCESS")){
			order.setIsPaySucceed(true);
			order.setPaySucceedTime(Calendar.getInstance().getTime());
			order.setOrderDeliverStatus(1); //null 交付状态不可用； 0 等待支付； 1 支付成功等待交付 ； 2 已经发货 ； 4 用户收到货物 并关闭订单； 6 用户收到货物并退货； 7 退货过程结束关闭订单； 
		}
		
		
		this.orderDao.update(order);
		
		retData.put("ZaCoreOrder", order);
		
		return retData;
		
	}

	@Override
	public nbReturn getOrderDetail(Integer orderId, String openId) {
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> retData = new HashMap<String, Object>();
		
		if( orderId == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		PCCoreOrder order = this.orderDao.find(orderId);
		
		if( order == null ){
			nbRet.setError(ReturnCode.GENERAL_ERROR);
			return nbRet;
		}
		
		ZaFrontUserWx wxUser = this.userWxDao.findByOpenId(openId);
		
		if( order.getWxUserId() != wxUser.getId() ){
			nbRet.setError(ReturnCode.AUTHORIZE_FAILED);
			return nbRet;
		}
		
		Map<String, Object> orderMap = DataWrap.wrapOrderMap(order);
		retData.put(_WechatKeyDefine.zaCoreOrder.o_order, orderMap);
		
		PCCoreEvent event = this.eventDao.find(order.getEventId());
		PCCoreEventRule rule = this.eventRuleDao.find(order.getEventRuleId());
		
		if( event == null || rule == null ){
			nbRet.setError(ReturnCode.GENERAL_ERROR);
			return nbRet;
		}
		
		Map<String, Object> eventMap = DataWrap.wrapEventMap(event, this.orderDao);
		retData.put(_WechatKeyDefine.nbEvent.o_event, eventMap);
		
		Map<String, Object> ruleMap = DataWrap.wrapEventRuleMap(rule);
		retData.put(_WechatKeyDefine.nbEventRule.o_rule, ruleMap);
		
		
		nbRet.setObject(retData);
		
		return nbRet;
	}

	@Override
	public nbReturn getEventStatus(Integer eventId, Integer startIndex, Integer pageSize) {
		nbReturn nbRet = new nbReturn();
		
		WechatConfigure wxCon = this.getWxConfigByEventId(eventId);
		if( wxCon == null ){
			nbRet.setError(ReturnCode.GENERAL_ERROR);
			return nbRet;
		}
		
		PCCoreEvent event = this.eventDao.find(eventId);
		List<PCCoreOrder> orderList = this.orderDao.findPayedSupporterByEventId(eventId, startIndex, pageSize);
		Integer totalMoney = this.orderDao.findAmountCount(eventId);
		Integer partiNum = this.orderDao.findMemberCount(eventId);
		
		Map<String, Object> retData = new HashMap<String, Object>();
		List<Map<String, Object>> supporterList = new ArrayList<Map<String, Object>>();
		
		
		retData.put(_WechatKeyDefine.getEventStatus.eventId, eventId);
		retData.put(_WechatKeyDefine.getEventStatus.wxAppId, wxCon.wxappid);
		retData.put(_WechatKeyDefine.getEventStatus.isSample, event.getIsEventSample());
		retData.put(_WechatKeyDefine.getEventStatus.deadlineDate, event.getEventDeadlineDate() == null ? null : nbStringUtil.DateTime2StrinF03(event.getEventDeadlineDate()));
		retData.put(_WechatKeyDefine.getEventStatus.eventStatus, event.getEventStatus());
		retData.put(_WechatKeyDefine.getEventStatus.gotTotalMoney, totalMoney);
		retData.put(_WechatKeyDefine.getEventStatus.gotPartiNum, partiNum);
		
		if( event.getEventDeadlineDate() != null)
			retData.put(_WechatKeyDefine.getEventStatus.leftTimeSec, (event.getEventDeadlineDate().getTime() - Calendar.getInstance().getTimeInMillis())/1000);
		for( PCCoreOrder order : orderList ){
			Map<String, Object> oneSupporter = new HashMap<String, Object>();
			
			ZaFrontUserWx wxUser = order.getWxSupporter();
			oneSupporter.put(_WechatKeyDefine.getEventStatus.parti_headimgUrl, wxUser.getHeadImgUrl());
			oneSupporter.put(_WechatKeyDefine.getEventStatus.parti_nickname, wxUser.getWxNickname());
			oneSupporter.put(_WechatKeyDefine.getEventStatus.parti_partiMoney, order.getTotalFee());
			oneSupporter.put(_WechatKeyDefine.getEventStatus.parti_partiTime, nbStringUtil.DateTime2StrinF01(order.getPaySucceedTime()));
			
			supporterList.add(oneSupporter);
		}
		retData.put(_WechatKeyDefine.getEventStatus.partis, supporterList);
		
		nbRet.setObject(retData);
		
		return nbRet;
	}

	/**
	 * 
	 * @param eventId
	 * @return
	 */
	private WechatConfigure getWxConfigByEventId(Integer eventId) {
		ZaFrontWxConfig wxCon = this.wxConfigDao.findByEventId(eventId);
		ZaFrontWxConfig wxDefaultCon = this.wxConfigDao.find(1); //第一个是默认的
		
		if( wxCon == null )
			wxCon = wxDefaultCon;
		
		if( wxCon.getWxappid() == null || wxCon.getWxappid().length() < 5 ){
			wxCon = wxDefaultCon;
		}
		
		if( wxCon.getMchId() == null || wxCon.getMchId().length() < 3 ){
			wxCon = wxDefaultCon;
		}
		
		return DataWrapUtil.wrapWxConfigMap(wxCon, (_WxIF_Configure) this.wxCommonService);
		
	}

	@Override
	public nbReturn getEventMsg(
			Integer eventId, 
			Map<String, Object> sessionTokenUserInfo, 
			Integer wxConfigId,
			Integer startIndex,
			Integer pageSize) {
		
		nbReturn nbRet = new nbReturn();
		
		List<PCCoreEventMsg> publicEventMsg;
		List<PCCoreEventMsg> privateEventMsg;
		
		if( eventId == null && wxConfigId == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		ZaFrontWxConfig wxc = null;
		PCCoreEvent event = null;
		
		if( eventId == null){
			wxc = this.wxConfigDao.find(wxConfigId);
			publicEventMsg = this.eventMsgDao.findByWxConfigId(wxConfigId, startIndex, pageSize);
			privateEventMsg = this.eventMsgDao.findByWxConfigIdAndOpenId(wxConfigId, (String)sessionTokenUserInfo.get(_WechatKeyDefine.wxUserInfo.openid));
		}else{
			event = this.eventDao.find(eventId);
			wxc = this.wxConfigDao.find(event.getWxConfigId());
			publicEventMsg = this.eventMsgDao.findByEventId(eventId, startIndex, pageSize, true);
			privateEventMsg = this.eventMsgDao.findByEventIdAndOpenId(eventId, (String)sessionTokenUserInfo.get(_WechatKeyDefine.wxUserInfo.openid));
		}
		
		Map<String, Object> retData = DataWrap.wrapEventMsgRet(event, wxc, publicEventMsg, privateEventMsg);
		nbRet.setObject(retData);
		return nbRet;
	}

	
	
	@Override
	public nbReturn saveEventMsg(
			Integer eventId, 
			Map<String, Object> sessionTokenUserInfo, 
			String wxAppId,
			String msgText, 
			List<Map<String, Object>> imgList) {
		
		nbReturn nbRet = new nbReturn();
		if( eventId == null && wxAppId == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		ZaFrontWxConfig wxc = null;
		PCCoreEvent event = null;
		if( eventId == null ){
			wxc = this.wxConfigDao.findByAppId(wxAppId);
		}else{
			event = this.eventDao.find(eventId);
			wxc = this.wxConfigDao.find(event.getWxConfigId());
		}
		
		ZaFrontUserWx wxUser = this.userWxDao.find(Integer.valueOf(sessionTokenUserInfo.get(_WechatKeyDefine.wxUserInfo.currentIdInDB).toString()));
		
		PCCoreEventMsg msg = new PCCoreEventMsg();
		
		msg.setMessage(msgText);
		msg.setMessageDate(Calendar.getInstance().getTime());
		msg.setZaCoreEvent(event);
		msg.setZaFrontUserWx(wxUser);
		msg.setZaFrontWxConfig(wxc);
		
		msg = this.eventMsgDao.save(msg);
		
		for(Map<String, Object> imgMap: imgList){
			PCCoreEventMsgImg msgImg = new PCCoreEventMsgImg();
			String[] imgTmp = null;
			try {
				imgTmp = 
						wxCommonService.saveWxImgToLocalServer(
						(imgMap.get(_WechatKeyDefine.getEventMsg.comment_comPic_org).toString()), 
						DataWrapUtil.wrapWxConfigMap(wxc, (_WxIF_Configure) this.wxCommonService)
						);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			if( imgTmp != null){
				msgImg.setImgUrl(imgTmp[1]);
				msgImg.setThumUrl(imgTmp[0]);
				msgImg.setZaCoreEventMsg(msg);
				msgImg = this.eventMsgImgDao.save(msgImg);
			}
		}
		
		return nbRet;
	}

	private class TokenStore{
		int wxConfigId;
		String pageToken;
		Long expireTime;
	}
	private class TokenStoreList{
		List<TokenStore> pageToken = new ArrayList<TokenStore>();
		
		public TokenStore findByConId(int id){
			for(TokenStore token : pageToken){
				if( token.wxConfigId == id){
					return token;
				}
			}
			return null;
		}
		
		public void replace(int id, String token, Long expireTime){
			
			TokenStore token1 = this.findByConId(id);
			if( token1 == null ){
				token1 = new TokenStore();
				pageToken.add(token1);
			}
			token1.wxConfigId = id;
			token1.pageToken = token;
			token1.expireTime = expireTime;
			
			
		}
	}
	
	
	@Override
	public nbReturn scanAndUpdateEvents() {
		nbReturn nbRet = new nbReturn();
		StringBuilder sb = new StringBuilder();
//		TokenStoreList tokenList = new TokenStoreList();

		
		List<PCCoreEvent> eventList = this.eventDao.findByEventStatusCode(1, _WechatKeyDefine.eventType.CROWDFUNDING);
//		String pageToken = null;
//		Long pageTokenExpire = null;
		
		for( PCCoreEvent event : eventList){
			
			StringBuilder returnPayList = new StringBuilder();
			nbReturn tmpRet = this.loadWechatConfig(null, event.getId(), false);
			if( !tmpRet.isSuccess() ){
				System.out.println("试图处理众筹项目【"+event.getEventTitle()+"】时候发现找不到微信的Config信息("+event.getWxConfigId()+")");
				continue;
			}
			WechatConfigure wxcon = (WechatConfigure) tmpRet.getObject();
			WechatPageSDK pageSDK = new WechatPageSDK(wxcon);
			
			try {
				if( !pageSDK.getPageAccessToken() ){
					nbRet.setError(ReturnCode.TOKEN_NOT_EXIST);
					return nbRet;
				}
			} catch (Exception e1) {
				System.out.println("pageToken获取失败！");
				e1.printStackTrace();
				nbRet.setError(ReturnCode.TOKEN_NOT_EXIST);
				return nbRet;
			}
			//tokenList.replace(wxc.getId(), pageSDK.getPage_access_token(), pageSDK.getPage_token_expireTime());
			
			Boolean needUpdate = false;
			Integer gotAmount = this.orderDao.findValidAmountCount(event.getId());
			Integer gotMember = this.orderDao.findValidMemberCount(event.getId());
			Integer targetAmount = event.getTargetAmount();
			Integer targetMember = event.getTargetMember();
			Boolean amountSucceed = targetAmount == 0 ? false : (gotAmount >= targetAmount ? true : false);
			Boolean memberSucceed = targetMember == 0 ? false : (gotMember >= targetMember ? true : false);
			if(targetAmount == 0 && targetMember == 0){ //如果两个都为0的话，说明创建者没有设定达标线
				amountSucceed = true;
				memberSucceed = true;
			}
			
			
			List<ZaFrontUserWx> wxUserList = this.orderDao.findUniquePayedSupporterByEventId(event.getId());
			
			if( ((amountSucceed || memberSucceed) && event.getCfEventType() != 1) ||
				((amountSucceed && memberSucceed) && event.getCfEventType() == 1) 
				){ //1的话表示众筹成功的标志是必须都成立
				//众筹成功
				event.setEventStatus(2);
				needUpdate = true;
				sb.append("第"+event.getId()+"号活动【"+event.getEventTitle()+"】\r\n"
						+ "目标金额为 "+targetAmount+",当前筹集金额为 "+gotAmount+"；目标人数为 "+targetMember+",当前追捧人数为 "+gotMember+"。\r\n"
						+ "项目截止时间为："+nbStringUtil.DateTime2StrinF02(event.getEventDeadlineDate())+"；项目状态将被设置为 众筹成功 (code: 2)\r\n");
				
				String[] keywords = {event.getEventTitle(), "成功！"};
				String first = "\r\n恭喜您！您已众筹成功\r\n";
				String remark = "\r\n点击查看您的订单详情，如果您参与是线下活动，可使用详情中的二维码作为入场券。\r\n";
				
				for(ZaFrontUserWx wxUser : wxUserList){
					try {
						wxCommonService.sendTplMessage(
								wxcon.tplMsgPaySuccess,
								wxcon.serverName+"/eventDetail.html?eventId="+event.getId(), 
								true, 
								first, 
								keywords, 
								remark, 
								wxUser.getWxOpenId(),
								wxcon
								);
					} catch (Exception e) {
						System.out.println("eventId = "+event.getId() +" wxUserId = "+wxUser.getId()+" pageToken = "+pageSDK.getPage_access_token()+" expire = "+pageSDK.getJsTicketExpireTime());
						e.printStackTrace();
					}
				}
			}
			else{
				//暂时还没有达标
				Long deadline = event.getEventDeadlineDate().getTime();
				if( deadline <= Calendar.getInstance().getTime().getTime() ){ //已经过了deadLine
					
					event.setEventStatus(3);				
					needUpdate = true;
					
					sb.append("第"+event.getId()+"号活动【"+event.getEventTitle()+"】\r\n"
							+ "目标金额为 "+targetAmount+",当前筹集金额为 "+gotAmount+"；目标人数为 "+targetMember+",当前追捧人数为 "+gotMember+"。\r\n"
							+ "项目截止时间为："+nbStringUtil.DateTime2StrinF02(event.getEventDeadlineDate())+"；项目状态将被设置为 众筹失败 (code: 3)\r\n");
					
					String[] keywords = {event.getEventTitle(), "失败！"};
					String first = "\r\n抱歉！您参与的众筹项目失败了\r\n";
					String remark = "\r\n由于在时间内，未能达到项目方设定的条件，所以众筹失败了。\r\n";
					
					for(ZaFrontUserWx wxUser : wxUserList){
						List<PCCoreOrder> ordersOfThisUser = this.orderDao.findByWxUserIdAndEventId(wxUser.getId(), event.getId());
						for(PCCoreOrder order : ordersOfThisUser){
							returnPayList.append(order.getMchOrderCode()+"  "+order.getTotalFee()+"  "+event.getEventTitle()+" 众筹失败！\r\n");
						}
						try {
							wxCommonService.sendTplMessage(
									wxcon.tplMsgPaySuccess,
									wxcon.serverName+"/eventDetail.html?eventId="+event.getId(), 
									true, 
									first, 
									keywords, 
									remark, 
									wxUser.getWxOpenId(),
									wxcon);
						} catch (Exception e) {
							System.out.println("eventId = "+event.getId()+" wxUserId = "+wxUser.getId()+" pageToken = "+pageSDK.getPage_access_token()+" expire = "+pageSDK.getJsTicketExpireTime());
							e.printStackTrace();
						}
					}
				}
			}
			//众筹状态扫表处理完毕
			
			try{
				if( returnPayList.length() > 2){
					File returnPayTextFile = new File(wxcon.resourcePath+"/returnPayText/"+event.getId());
					if( ! returnPayTextFile.exists() ){
						returnPayTextFile.getParentFile().mkdirs();
						returnPayTextFile.createNewFile();
					}
					FileOutputStream fos = new FileOutputStream(returnPayTextFile);
					OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
					osw.write(returnPayList.toString());
					osw.flush();
					osw.close();
				}
			}catch(Exception e){
				System.out.println("创建退款文档失败！");
				e.printStackTrace();
			}
			
			if( needUpdate ){
				event = this.eventDao.update(event);
				nbRet.setObject(sb.toString());
			}
			
		}
		
		return nbRet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public nbReturn updateEvents(
			Map<String, Object> jsonMap,
			WechatConfigure wxCon,
			String openid) {
		
		nbReturn nbRet = new nbReturn();
		
		ZaFrontUserWx wxUser = this.userWxDao.findByOpenId(openid);
		
		Integer eventId = jsonMap.get(_WechatKeyDefine.doSaveEventModify.eventId) == null ? null : Integer.valueOf(jsonMap.get(_WechatKeyDefine.doSaveEventModify.eventId).toString());
		Boolean isCFEvent = jsonMap.get(_WechatKeyDefine.doSaveEventModify.isCFEvent) == null ? null : Boolean.valueOf(jsonMap.get(_WechatKeyDefine.doSaveEventModify.isCFEvent).toString());
		String eventTitle = jsonMap.get(_WechatKeyDefine.doSaveEventModify.eventTitle) == null ? "发布者忘记填了" : jsonMap.get(_WechatKeyDefine.doSaveEventModify.eventTitle).toString();
		Boolean isActive = jsonMap.get(_WechatKeyDefine.doSaveEventModify.isActive) == null ? false : Boolean.valueOf(jsonMap.get(_WechatKeyDefine.doSaveEventModify.isActive).toString());
		String eventDesc = jsonMap.get(_WechatKeyDefine.doSaveEventModify.eventDesc) == null ? eventTitle : jsonMap.get(_WechatKeyDefine.doSaveEventModify.eventDesc).toString();
		Integer targetMoney = jsonMap.get(_WechatKeyDefine.doSaveEventModify.targetMoney) == null ? 0 : Integer.valueOf(jsonMap.get(_WechatKeyDefine.doSaveEventModify.targetMoney).toString())*100;
		Integer targetMember = jsonMap.get(_WechatKeyDefine.doSaveEventModify.targetMember) == null ? 0 : Integer.valueOf(jsonMap.get(_WechatKeyDefine.doSaveEventModify.targetMember).toString());
		Date deadlineDate = jsonMap.get(_WechatKeyDefine.doSaveEventModify.deadlineDate) == null ? null : nbStringUtil.String2DateTime(jsonMap.get(_WechatKeyDefine.doSaveEventModify.deadlineDate).toString());
		Date eventDate = jsonMap.get(_WechatKeyDefine.doSaveEventModify.eventDate) == null ? null : nbStringUtil.String2DateTime(jsonMap.get(_WechatKeyDefine.doSaveEventModify.eventDate).toString());
		String eventAddress = jsonMap.get(_WechatKeyDefine.doSaveEventModify.eventAddress) == null ? "发布者忘记填了" : jsonMap.get(_WechatKeyDefine.doSaveEventModify.eventAddress).toString();
		String eventDetailUrl = jsonMap.get(_WechatKeyDefine.doSaveEventModify.eventDetailUrl) == null ? "" : jsonMap.get(_WechatKeyDefine.doSaveEventModify.eventDetailUrl).toString();
		Integer cfEventType = jsonMap.get(_WechatKeyDefine.doSaveEventModify.cfEventType) == null ? 0 : Integer.valueOf(jsonMap.get(_WechatKeyDefine.doSaveEventModify.cfEventType).toString());
		Integer eventType = isCFEvent ? 2 : 1; //要么是众筹（2）,要么是普通收费项目(1)
		Integer eventSubType = jsonMap.get(_WechatKeyDefine.doSaveEventModify.eventSubType) == null ? 0 : Integer.valueOf(jsonMap.get(_WechatKeyDefine.doSaveEventModify.eventSubType).toString());
		if( eventDetailUrl == null || eventDetailUrl.length() == 0){
			eventDetailUrl = eventDesc;
		}
		
		PCCoreEvent event = this.eventDao.find(eventId==0?null:eventId);
		if( event == null ){
			event = new PCCoreEvent();
			event.setEventCreateDate(Calendar.getInstance().getTime());
			event.setEventHeadImg(wxCon.companyLogoUrl);
			event.setIsEventSample(false);
			event.setIsEventSucceed(null);
			event.setWxConfigId(this.wxConfigDao.findByAppId(wxCon.wxappid).getId());
			event.setWxCreater(wxUser);
			event.setZaCoreEventMsgs(new ArrayList<PCCoreEventMsg>());
			
		}
		
		event.setWxCardDesc(wxUser.getWxNickname()+" 邀请您参与 "+eventTitle+" 活动！");
		event.setWxCardTitle(wxCon.configName);
		event.setWxCardImgUrl(wxUser.getHeadImgUrl());
		
		event.setIsEventPassedReview(false);
		event.setEventStatus(cfEventType != 0?((event.getEventStatus()==null || event.getEventStatus()==0)?1:event.getEventStatus()):0);
		event.setEventTitle(eventTitle);
		event.setIsEventActive(isActive);
		event.setEventShortDesc(eventDesc);
		event.setTargetAmount(targetMoney);
		event.setTargetMember(targetMember);
		event.setEventDeadlineDate(deadlineDate);
		event.setEventDetailUrl(eventDetailUrl);
		event.setEventTime(eventDate);
		event.setEventAddress(eventAddress);
		event.setCfEventType(cfEventType);
		event.setEventType(eventType);
		event.setEventSubType(eventSubType);
		
		if(  event.getId() == 0){
			event = this.eventDao.save(event);
		}
		else{
			event = this.eventDao.update(event);
		}
		// wrap rules
		
		List<Map<String, Object>> rulesList = jsonMap.get(_WechatKeyDefine.doSaveEventModify.rules) == null ? new ArrayList<Map<String, Object>>() : (List<Map<String, Object>>)jsonMap.get(_WechatKeyDefine.doSaveEventModify.rules);
		
		for( Map<String, Object> ruleMap : rulesList){
			Boolean rule_isToDeleted = ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_isToDeleted) == null ? false : Boolean.valueOf(ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_isToDeleted).toString());
			Integer rule_ruleId = ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_ruleId) == null ? null : Integer.valueOf(ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_ruleId).toString());
			Integer rule_unitPrice = ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_unitPrice) == null ? 0 : Integer.valueOf(ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_unitPrice).toString())*100;
			Boolean rule_isNeedAddress = ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_isNeedAddress) == null ? false : Boolean.valueOf(ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_isNeedAddress).toString());
			Boolean rule_isCountInAmount = ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_isCountInAmount) == null ? false : Boolean.valueOf(ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_isCountInAmount).toString());
			Boolean rule_isCountInMember = ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_isCountInMember) == null ? false : Boolean.valueOf(ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_isCountInMember).toString());
			Boolean rule_isAcceptableAfterDeadline = ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_isAcceptableAfterDeadline) == null ? false : Boolean.valueOf(ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_isAcceptableAfterDeadline).toString());
			String rule_ruleTitle = ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_ruleTitle) == null ? null: ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_ruleTitle).toString();
			if( rule_ruleTitle.length() <= 0 )
				rule_ruleTitle = rule_unitPrice == 0 ? "看心情打赏":"捧"+rule_unitPrice/100+"."+rule_unitPrice%100+"元参加";
			String rule_ruleDesc = ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_ruleDesc) == null ? rule_ruleTitle : ruleMap.get(_WechatKeyDefine.doSaveEventModify.rule_ruleDesc).toString();
			
			PCCoreEventRule rule = this.eventRuleDao.find(rule_ruleId);
			if( rule == null ){
				rule = new PCCoreEventRule();
				rule.setEventId(event.getId());
				rule.setRuleIsActive(false);
				rule.setRuleIsCanManyCopy(rule_unitPrice == 0 ? false : true);
				rule.setRuleIsNeedBarcode(true);
				rule.setRuleMaxAmount(0);
				rule.setRuleMinAmount(0);
				rule.setRuleMaxMember(0);
				rule.setRuleMinMember(0);
			}
			rule.setRuleIsDeleted(rule_isToDeleted);
			rule.setRuleShortDesc(rule_ruleTitle);
			rule.setRuleUnitFee(rule_unitPrice);
			rule.setRuleIsNeedAddress(rule_isNeedAddress);
			rule.setRuleAwardLongDesc(rule_ruleDesc);
			rule.setIsCountInAmount(rule_isCountInAmount);
			rule.setIsCountInMember(rule_isCountInMember);
			rule.setIsAcceptableAfterDeadline(rule_isAcceptableAfterDeadline);
			
			if( rule.getId() == 0 ){
				rule = this.eventRuleDao.save(rule);
			}
			else{
				rule = this.eventRuleDao.update(rule);
			}
		}

		
		
		event = this.eventDao.update(event);
		nbRet.setObject(event);
		
		return nbRet;
		
		// wrap rules end
		
		

//		public static String wxAppId = "wxAppId";
//		public static String openId = "openId";
		
	
		
		
	}

	/**
	 * 
	 * @param openId
	 * @return
	 */
	@Override
	public nbReturn getMyPengList(String openId) {
		
		nbReturn nbRet = new nbReturn();
		
		List<Map<String, Object>> retInfoList = new ArrayList<Map<String, Object>>();
		
		List<PCCoreOrder> orderList = this.orderDao.findByOpenId(openId);
		for( PCCoreOrder order : orderList){
			Map<String, Object> retInfo = new HashMap<String, Object>();
			PCCoreEvent event = this.eventDao.find(order.getEventId());
			if( event == null ){
				System.out.println("发现order中有错误数据：orderid=【"+order.getId()+"】");
				continue;
			}
			retInfo.put(_WechatKeyDefine.getMyPengList.peng_event_title, event.getEventTitle());
			retInfo.put(_WechatKeyDefine.getMyPengList.peng_event_desc, event.getEventShortDesc());
			retInfo.put(_WechatKeyDefine.getMyPengList.peng_order_id, order.getId());
			retInfoList.add(retInfo);
		}
		
		nbRet.setObject(retInfoList);
		return nbRet;
	}

	
	@Transactional
	@Override
	public nbReturn getEventInfo(Integer eventId, WechatConfigure wxCon, String currentUserOpenId, boolean isShowEmptyText) {
		nbReturn nbRet = new nbReturn();
		
		PCCoreEvent event = this.eventDao.find(eventId);
		
		if( event == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		ZaFrontUserWx wxUser = this.userWxDao.findByOpenId(currentUserOpenId);
		Integer amount = this.orderDao.findAmountCount(eventId);
		Integer partNumber = this.orderDao.findMemberCount(eventId);
		System.out.println("isShowEmptyText "+isShowEmptyText);
		List<PCCoreEventMsg> msgList = this.eventMsgDao.findByEventId(eventId, 0, 20, isShowEmptyText);
		List<PCCoreEventMsgImg> msgImgList = this.eventMsgImgDao.findByEventId(eventId, 0, 50);
		
		Boolean isUserPlayer = false;
		
		
		if( wxUser != null){
			List<PCCoreOrder> currentUsersOrderList = 
					this.orderDao.findByWxUserIdAndEventId(wxUser.getId(), eventId);
			
			for( PCCoreOrder order : currentUsersOrderList ){
				
				if( order.getIsPaySucceed() ){
					
					PCCoreEventRule rule = this.eventRuleDao.find(order.getEventRuleId());
					
					if( rule.getIsCountInAmount() || rule.getIsCountInMember() ){
						isUserPlayer = true;
						break;
					}
				}
			}
		}
		
		
		nbRet.setObject(DataWrap.wrapDoGetEventInfo(
				event, 
				wxUser, 
				wxCon, 
				partNumber, 
				amount, 
				msgImgList, 
				msgList,
				isUserPlayer
				)  );
		
		return nbRet;
	}

	@Override
	public nbReturn getMatchEventResult(Integer eventId, String openId, WechatConfigure wxCon, Boolean onlyNotConfirmed) {
		nbReturn nbRet = new nbReturn();
		PCCoreEvent event = this.eventDao.find(eventId);
		if( event == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		ZaFrontUserWx wxUser = this.userWxDao.findByOpenId(openId);
		
		List<PCMatchEventResult> matchResultList = this.matchEventResultDao.findByEventId(eventId, wxUser, onlyNotConfirmed);
		List<ZaFrontUserWx> playerList = this.orderDao.findUniquePayedSupporterByEventId(eventId);
		
		Boolean isUserPlayer = false;
		
		if( wxUser != null){
			List<PCCoreOrder> currentUsersOrderList = 
					this.orderDao.findByWxUserIdAndEventId(wxUser.getId(), eventId);
			
			for( PCCoreOrder order : currentUsersOrderList ){
				
				if( order.getIsPaySucceed() ){
					
					PCCoreEventRule rule = this.eventRuleDao.find(order.getEventRuleId());
					
					if( rule.getIsCountInAmount() || rule.getIsCountInMember() ){
						isUserPlayer = true;
						break;
					}
				}
			}
		}
		
		nbRet.setObject( DataWrap.wrapDoGetMatchEventResult(
				event,
				matchResultList, 
				playerList,
				wxCon, 
				wxUser,
				isUserPlayer)
				);
		
		
		return nbRet;
	}

	@Override
	public nbReturn saveMatchEventResult(Integer eventId, String openId, Integer rivalId, Integer result,
			WechatConfigure wxCon) {
		
		nbReturn nbRet = new nbReturn();
		PCCoreEvent event = this.eventDao.find(eventId);
		if( event == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		ZaFrontUserWx playerA = this.userWxDao.findByOpenId(openId);
		if( playerA == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		ZaFrontUserWx playerB = this.userWxDao.find(rivalId);
		if( playerB == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		PCMatchEventResult matchResult = new PCMatchEventResult();
		
		matchResult.setEvent(event);
		matchResult.setPlayBConfirmed(false);
		matchResult.setPlayerA(playerA);
		matchResult.setPlayerB(playerB);
		matchResult.setPlayResult(result);
		
		matchResult = this.matchEventResultDao.save(matchResult);
		
		String[] keywords = {
				playerA.getWxNickname()+"：【胜】 VS "+playerB.getWxNickname()+"：【负】", 
				playerA.getWxNickname(), 
				nbStringUtil.DateTime2StrinF02(Calendar.getInstance().getTime())
				};
		String first = "\r\n【"+event.getEventTitle()+"】活动\r\n有一个新的比赛结果需要您的确认\r\n";
		String remark = "\r\n点击进行【确认】操作。\r\n";
		
		try {
			this.wxCommonService.sendTplMessage(
					wxCon.tplMsgMatchResultConfirm, 
					wxCon.serverName+"/eventMatchResultConfirm.html?eventId="+event.getId(), 
					true, 
					first, 
					keywords, 
					remark, 
					playerB.getWxOpenId(), 
					wxCon);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return nbRet;
	}

	@Override
	public nbReturn confirmMatchEventResult(Integer eventId, String openId, Integer resultId, WechatConfigure wxCon) {
		nbReturn nbRet = new nbReturn();
		
//		ZaCoreEvent event = this.eventDao.find(eventId);
//		if( event == null ){
//			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
//			return nbRet;
//		}
		
		PCMatchEventResult matchResult = this.matchEventResultDao.find(resultId);
		
		ZaFrontUserWx playerB = this.userWxDao.findByOpenId(openId);
		if( playerB == null ||  matchResult == null || matchResult.getPlayerB().getId() != playerB.getId() ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}

		matchResult.setPlayBConfirmed(true);
		
		matchResult = this.matchEventResultDao.update(matchResult);
		
		return nbRet;
	}

	@Override
	public nbReturn checkIfPayWindowOpen(Integer eventId, Integer ruleId) {
		nbReturn nbRet = new nbReturn();
		
		PCCoreEvent event = this.eventDao.find(eventId);
		PCCoreEventRule rule = this.eventRuleDao.find(ruleId);
		//List<ZaCoreEventRule> allRules = this.eventRuleDao.findByEventId(eventId);
		//List<ZaCoreOrder> orders = this.orderDao.findByWxUserIdAndEventId(wxUserId, eventId)
		if( event == null || rule == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		//int targetAmount = event.getTargetAmount();
		//int targetMember = event.getTargetMember();
		//int cfType = event.getCfEventType(); //0: 不是众筹项目； 1： 目标金额和目标人数必须同时达到  ； 2：目标金额和目标人数只要先达到了就可以了 ； 3： 目标金额必须达到； 4：目标人数必须达到
		//int eventType = event.getEventType(); //0 未知类型（一般表示本条内容是无效的）； 1 普通支付类的项目 ； 2 众筹项目  ； 3 单人对赌项目 ；
		//if( eventType != 2) cfType = 0; //保证如果不是众筹项目，众筹条件就是0，无效的
		//int totalAmount = this.orderDao.findAmountCount(eventId);
		//boolean amountOK = ( totalAmount >= targetAmount );
		//int totalMember = this.orderDao.findMemberCount(eventId);
		//boolean memberOK = ( totalMember >= targetMember );
		int eventStatus = event.getEventStatus();//0 ：不是众筹项目，肯定执行； 1：众筹中； 2：众筹成功； 3：众筹失败； 4：活动方取消
		if( eventStatus == 3 ||
			eventStatus == 4 ){
			nbRet.setError(ReturnCode.EVENT_IS_CLOSED);
			return nbRet;
		}
		
		
		Date eventDeadLine = event.getEventDeadlineDate();
		Date currentDate = Calendar.getInstance().getTime();
		boolean isTimeOver = currentDate.after(eventDeadLine);

		boolean isAccAfterDeadline = rule.getIsAcceptableAfterDeadline();
		boolean isCountInAmount = rule.getIsCountInAmount();		
		boolean isCountInMember = rule.getIsCountInMember();
		boolean isRuleDeleted = rule.getRuleIsDeleted();
		int maxRuleAmount = rule.getRuleMaxAmount();
		//int minRuleAmount = rule.getRuleMinAmount();
		int maxRuleMember = rule.getRuleMaxMember();
		//int minRuleMember = rule.getRuleMinMember();
		int ruleTotalAmount = this.orderDao.findAmountCount(eventId, ruleId);
		int ruleTotalMember = this.orderDao.findMemberCount(eventId, ruleId);
		
		if( isRuleDeleted ){
			nbRet.setError(ReturnCode.RULE_IS_NOT_VALIABLE);
			return nbRet;
		}
		
		if( isTimeOver ){
			if( !isAccAfterDeadline ){
				nbRet.setError(ReturnCode.EVENT_IS_TIMEUP);
				return nbRet;
			}
		}
		
		if( isCountInMember && maxRuleMember != 0 && ruleTotalMember >= maxRuleMember){
			nbRet.setError(ReturnCode.EVENT_MEMBER_FULL);
			return nbRet;
		}
		
		if( isCountInAmount && maxRuleAmount != 0 && ruleTotalAmount >= maxRuleAmount ){
			nbRet.setError(ReturnCode.EVENT_AMOUNT_FULL);
			return nbRet;
		}
		
		
		return nbRet;
	}
	
	@Override
	public nbReturn loadWechatConfig(String wxUserOpenId, Integer eventId, boolean isLoadDefaultOnFalt) {
		nbReturn nbRet = new nbReturn();
		ZaFrontWxConfig wxCon = null;
		if( eventId != null && eventId == 0 )
			eventId = null;
		try{
			
			ZaFrontUserWx wxUser = this.userWxDao.findByOpenId(wxUserOpenId);
			PCCoreEvent event = this.eventDao.find(eventId);
			
			if( wxUser == null && event == null && !isLoadDefaultOnFalt){
				nbRet.setError(ReturnCode.PARAMETER_PHARSE_ERROR);
				return nbRet;
			}
			
			ZaFrontWxConfig wxConByUser = null;
			ZaFrontWxConfig wxConByEvent = null;
			
			if( event != null ){
				wxConByEvent = this.wxConfigDao.find(event.getWxConfigId());
			}
			
			if( wxUser != null){
				wxConByUser = this.wxConfigDao.findByAppId(wxUser.getWxAppId());
			}
			
			if( wxConByUser != null && wxConByEvent != null){
				if( wxConByUser.getId() != wxConByEvent.getId() ){
					nbRet.setError(ReturnCode.WXCON_NOT_MATCH);
					return nbRet;
				}
			}
			
			wxCon = wxConByUser == null ? wxConByEvent : wxConByUser;
			
			if( wxCon == null && !isLoadDefaultOnFalt){
				nbRet.setError(ReturnCode.PARAMETER_PHARSE_ERROR);
				return nbRet;
			}
			
			nbRet.setObject(DataWrapUtil.wrapWxConfigMap(wxCon, this));
			
		}catch(Exception e){
			nbRet.setError(ReturnCode.GENERAL_ERROR);
			e.printStackTrace();
		}
		
		if( wxCon == null  && isLoadDefaultOnFalt){
			//从app_config.properties文件里获取配置好的默认微信公众号ID
			String defaultWxAppId = CommonHelper.loadAppSpecifiedConfig("wxAppId");
			if( nbStringUtil.isNullEmpty(defaultWxAppId) ){
				wxCon = this.wxConfigDao.findByAppId(defaultWxAppId);
			}
			//如果从app_config.properties获取公众号失败
			if( wxCon == null ){
				wxCon = this.wxConfigDao.findByDefault();
			}
			nbRet.setObject(DataWrapUtil.wrapWxConfigMap(wxCon, this));
		}
		return nbRet;
		
	}

	@Override
	public boolean updatePageToken(int id, String pageToken, long pageTokenExpire) {
		return this.wxCommonService.doUpdatePageToken(id, pageToken, pageTokenExpire);
	}

	@Override
	public boolean updateJSTicket(int id, String jsTicket, long jsTicketExpire) {
		return this.wxCommonService.doUpdateJSTicket(id, jsTicket, jsTicketExpire);
		
	}

	@Override
	public int getEventAmountCount(Integer eventId) {

		return this.orderDao.findAmountCount(eventId);
	}

}
