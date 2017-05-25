package nbBase.service.wechat;

import java.util.Map;
import java.util.TreeMap;

import nbBase.database.models.ZaPayWxUnionOrder;
import nbBase.database.models.ZaSendAwardLog;


public class WechatMchPay {
	
	private WechatConfigure wxConfig  =null;
	
	public WechatMchPay(WechatConfigure wxc){
		this.wxConfig = wxc;
	}
	
	public final static class parmaterKeys{
		public final static String nonce_str = "nonce_str"; // max 32
		public final static String sign = "sign"; // max 32
		public final static String mch_billno = "mch_billno";
		public final static String mch_id = "mch_id";
		public final static String wxappid = "wxappid";
		public final static String send_name = "send_name"; //商户名称 32
		public final static String re_openid = "re_openid"; //接受红包的用户 用户在wxappid下的openid
		public final static String total_amount = "total_amount";
		public final static String total_num = "total_num";
		public final static String wishing = "wishing"; //红包祝福语 128
		public final static String client_ip = "client_ip"; //调用接口的机器Ip地址
		public final static String act_name = "act_name"; //活动名称 32
		public final static String remark = "remark"; //备注信息 256
		
		public final static String appid = "appid"; //微信分配的公众账号ID（企业号corpid即为此appId） 32
		public final static String device_info = "device_info"; //终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB" 32
		public final static String body = "body"; //商品或支付单简要描述 128
		public final static String detail = "detail"; //商品名称明细列表 192
		public final static String attach = "attach"; //附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据 127
		public final static String out_trade_no = "out_trade_no"; //商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号 32
		public final static String fee_type = "fee_type"; //符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
		public final static String total_fee = "total_fee"; //订单总金额，单位为分，详见支付金额 int
		public final static String spbill_create_ip = "spbill_create_ip"; //APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。 16
		public final static String time_start = "time_start"; //订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则 14
		public final static String time_expire = "time_expire"; //订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则 注意：最短失效时间间隔必须大于5分钟
		public final static String goods_tag = "goods_tag"; //(WXG)商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠 32
		public final static String notify_url = "notify_url"; //接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。 256
		public final static String trade_type = "trade_type"; //取值如下：JSAPI，NATIVE，APP，详细说明见参数规定 
		public final static String product_id = "product_id"; //trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
		public final static String limit_pay = "limit_pay"; //no_credit--指定不能使用信用卡支付
		public final static String openid = "openid"; //trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
		
		public final static String appId = "appId"; 
		public final static String prepay_id = "prepay_id";
		public final static String timeStamp = "timeStamp";
		public final static String nonceStr = "nonceStr";
		public final static String _package = "_package";
		public final static String paySign = "paySign";
		public final static String signType = "signType";
	}

	
	
	private TreeMap<String, Object> sendAwardParam = new TreeMap<String, Object>();
	private TreeMap<String, Object> wxPayParam = new TreeMap<String, Object>();
	private TreeMap<String, Object> transferMoneyParam = new TreeMap<String, Object>();
	private String lastErrcode = null;
	private String lastErrmsg = null;
	
	
	/**
	 * 得到参数表
	 * @return
	 */
	public TreeMap<String, Object> getSendAwardParam() {
		return sendAwardParam;
	}
	
	/**
	 * 得到参数表
	 * @return
	 */
	public TreeMap<String, Object> getWxPayParam() {
		return wxPayParam;
	}
	
	/**
	 * 创建统一下单记录
	 * @param orderBody
	 * @param orderDetail
	 * @param attach
	 * @param totalFee
	 * @param clientIp
	 * @param productId
	 * @param openId
	 * @return
	 * @throws Exception 
	 */
	public boolean createUnionOrder(	String orderBody, 
										String orderDetail, 
										String attach, 
										Integer totalFee, 
										String clientIp, 
										String productId, 
										String openId) throws Exception{
		
		wxPayParam = new TreeMap<String, Object>();
		
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.appid, wxConfig.wxappid, false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.mch_id, wxConfig.mchId, false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.device_info, "WEB", false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.nonce_str, _WechatUtils.createUniqueString(), false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.body, orderBody, false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.detail, orderDetail, false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.attach, attach, false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.out_trade_no, _WechatUtils.createBillNumber(wxConfig.mchId), false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.fee_type, "CNY", false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.total_fee, totalFee , false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.spbill_create_ip, clientIp, false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.time_start, _WechatUtils.getTimeString(0l), false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.time_expire, _WechatUtils.getTimeString(wxConfig.orderDefaultExpireTime), false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.goods_tag, "WXG", false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.notify_url, wxConfig.wxPayNotifyUrl, false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.trade_type, _WechatKeyDefine.wxPayType.JSAPI, false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.product_id, productId, false);
		//WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.limit_pay, WechatConfigure.wxappid, false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.openid, openId, false);
		
		
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.sign, _WechatUtils.createSinature(getWxPayParam(), wxConfig.payKey), false);
		
		
		String xmlDataString = _WechatUtils.Map2XMLString(wxPayParam);
		System.out.println(xmlDataString);
		
		String ret = 
				_WechatUtils.httpPostSend(xmlDataString, wxConfig.getPlaceUnionOrderUrl());
		
		Map<String, Object> retMessage = _WechatUtils.parseXML(ret, new String().getClass());
		
		lastErrcode = (String) retMessage.get("return_code");
		lastErrmsg = (String) retMessage.get("return_msg");;
		
		if( lastErrcode.toUpperCase().equals("SUCCESS")){
			wxPayParam.put(WechatMchPay.parmaterKeys.prepay_id, (String)retMessage.get("prepay_id"));
			TreeMap<String, Object> forIntialPay = new TreeMap<String, Object>();
			
			forIntialPay.put(WechatMchPay.parmaterKeys.appId, wxConfig.wxappid);
			forIntialPay.put(WechatMchPay.parmaterKeys.signType, "MD5");
			forIntialPay.put(WechatMchPay.parmaterKeys.timeStamp, _WechatUtils.getTimeString(0l));
			forIntialPay.put(WechatMchPay.parmaterKeys.nonceStr, _WechatUtils.createUniqueString());
			forIntialPay.put("package", WechatMchPay.parmaterKeys.prepay_id+"="+wxPayParam.get(WechatMchPay.parmaterKeys.prepay_id));
			forIntialPay.put(WechatMchPay.parmaterKeys.paySign, _WechatUtils.createSinature(forIntialPay, wxConfig.payKey));
			wxPayParam.putAll(forIntialPay);
			wxPayParam.put(WechatMchPay.parmaterKeys._package, (String)forIntialPay.get("package"));
			return true;
		}
		else
			return false;
	}
	
	/**
	 * 通过一个公众号发送一个红包给某个人
	 * @param send_name 商户名称
	 * @param re_openid 发送对象的openID
	 * @param total_amount 发送金额，分 为单位
	 * @param wishing 祝福语
	 * @param act_name 活动名称
	 * @param remark 备注
	 * @param mch_billno 订单号，可为空，为空的时候，函数会自动生成
	 * @param nonce_str 唯一字符串，可为空，为空的时候，函数会自动生成
	 * @param client_ip 客户端IP，可为空
	 * @throws Exception
	 */
	public Map<String, Object>  sendAward(	String send_name, 
											String re_openid, 
											Integer total_amount, 
											String wishing, 
											String act_name, 
											String remark, 
											String mch_billno,
											String nonce_str,
											String client_ip) throws Exception{
		//"o5hTUviNXw7dSavHzC1NNJd2tV7w"
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.mch_billno, mch_billno == null ? _WechatUtils.createBillNumber(wxConfig.mchId) : mch_billno, false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.send_name, send_name, false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.re_openid, re_openid, false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.total_amount, total_amount, false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.wishing, wishing, false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.client_ip, client_ip == null ? wxConfig.server_ip : client_ip, false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.act_name, act_name, false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.remark, remark, false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.nonce_str, nonce_str == null ? _WechatUtils.createUniqueString() :nonce_str, false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.mch_id, wxConfig.mchId, false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.wxappid, wxConfig.wxappid, false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.total_num, 1, false);
		
		
		
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.sign, _WechatUtils.createSinature(getSendAwardParam(), wxConfig.payKey), false);
		
		
		String xmlDataString = _WechatUtils.Map2XMLString(sendAwardParam);
		System.out.println(xmlDataString);
		
		String ret = wxConfig.certFileBin == null ?
				_WechatUtils.httpsPostSend(xmlDataString, wxConfig.sendAwardURL, wxConfig.certFile_p12, wxConfig.mchId) :
				_WechatUtils.httpsPostSend(xmlDataString, wxConfig.sendAwardURL, wxConfig.certFileBin, wxConfig.mchId)  ;
		
		Map<String, Object> retMessage = _WechatUtils.parseXML(ret, new String().getClass());
		
		return retMessage;
    }
	
	/**
	 * 
	 * @param sal
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object>  sendAward(ZaSendAwardLog sal) throws Exception{
		
		//"o5hTUviNXw7dSavHzC1NNJd2tV7w"
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.mch_billno, sal.getMchBillno(), false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.send_name, sal.getSendName(), false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.re_openid, sal.getReOpenid(), false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.total_amount, sal.getTotalAmount(), false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.wishing, sal.getWishing(), false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.client_ip, sal.getClientIp(), false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.act_name, sal.getActName(), false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.remark, sal.getRemark(), false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.nonce_str, sal.getNonceStr(), false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.mch_id, sal.getMchId(), false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.wxappid, sal.getWxappid(), false);
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.total_num, sal.getTotalNum(), false);
		
		
		
		_WechatUtils.prepareParameter(sendAwardParam, WechatMchPay.parmaterKeys.sign, _WechatUtils.createSinature(getSendAwardParam(), wxConfig.payKey), false);
		
		
		String xmlDataString = _WechatUtils.Map2XMLString(sendAwardParam);
		System.out.println(xmlDataString);
		
		String ret = wxConfig.certFileBin == null ?
				_WechatUtils.httpsPostSend(xmlDataString, wxConfig.sendAwardURL, wxConfig.certFile_p12, wxConfig.mchId) :
				_WechatUtils.httpsPostSend(xmlDataString, wxConfig.sendAwardURL, wxConfig.certFileBin, wxConfig.mchId)  ;
		
		Map<String, Object> retMessage = _WechatUtils.parseXML(ret, new String().getClass());
		
		return retMessage;
	}

	public String getLastErrcode() {
		return lastErrcode;
	}

	public String getLastErrmsg() {
		return lastErrmsg;
	}

	public boolean createUnionOrder(ZaPayWxUnionOrder wxUnionOrder, Map<String, Object> userInfo) throws Exception {
		
		wxPayParam = new TreeMap<String, Object>();
		
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.appid, wxConfig.wxappid, false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.mch_id, wxConfig.mchId, false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.device_info, wxUnionOrder.getWxDeviceInfo(), false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.nonce_str, wxUnionOrder.getWxNonceStr(), false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.body, wxUnionOrder.getWxOrderBody(), false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.detail, wxUnionOrder.getWxOrderDetail(), false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.attach, wxUnionOrder.getWxOrderAttach(), false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.out_trade_no, wxUnionOrder.getWxOutTradeNo(), false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.fee_type, wxUnionOrder.getWxFeeType(), false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.total_fee, wxUnionOrder.getWxTotalFee() , false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.spbill_create_ip, wxUnionOrder.getWxSpbillCreateIp(), false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.time_start, _WechatUtils.getTimeString(wxUnionOrder.getWxTimeStart()), false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.time_expire, _WechatUtils.getTimeString(wxUnionOrder.getWxTimeExpire()), false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.goods_tag, wxUnionOrder.getWxGoodsTag(), false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.notify_url, wxConfig.wxPayNotifyUrl, false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.trade_type, wxUnionOrder.getWxTradeType(), false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.product_id, wxUnionOrder.getWxProductId(), false);
		//WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.limit_pay, WechatConfigure.wxappid, false);
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.openid, userInfo.get(_WechatKeyDefine.wxUserInfo.openid), false);
		
		
		_WechatUtils.prepareParameter(wxPayParam, WechatMchPay.parmaterKeys.sign, _WechatUtils.createSinature(getWxPayParam(), wxConfig.payKey), false);
		
		
		String xmlDataString = _WechatUtils.Map2XMLString(wxPayParam);
		System.out.println(xmlDataString);
		
		String ret = 
				_WechatUtils.httpPostSend(xmlDataString, wxConfig.getPlaceUnionOrderUrl());
		
		Map<String, Object> retMessage = _WechatUtils.parseXML(ret, new String().getClass());
		
		lastErrcode = (String) retMessage.get("return_code");
		lastErrmsg = (String) retMessage.get("return_msg");;
		
		if( lastErrcode.toUpperCase().equals("SUCCESS")){
			wxPayParam.put(WechatMchPay.parmaterKeys.prepay_id, (String)retMessage.get("prepay_id"));
			TreeMap<String, Object> forIntialPay = new TreeMap<String, Object>();
			
			forIntialPay.put(WechatMchPay.parmaterKeys.appId, wxConfig.wxappid);
			forIntialPay.put(WechatMchPay.parmaterKeys.signType, "MD5");
			forIntialPay.put(WechatMchPay.parmaterKeys.timeStamp, _WechatUtils.getTimeString(0l));
			forIntialPay.put(WechatMchPay.parmaterKeys.nonceStr, _WechatUtils.createUniqueString());
			forIntialPay.put("package", WechatMchPay.parmaterKeys.prepay_id+"="+wxPayParam.get(WechatMchPay.parmaterKeys.prepay_id));
			forIntialPay.put(WechatMchPay.parmaterKeys.paySign, _WechatUtils.createSinature(forIntialPay, wxConfig.payKey));
			wxPayParam.putAll(forIntialPay);
			wxPayParam.put(WechatMchPay.parmaterKeys._package, (String)forIntialPay.get("package"));
			return true;
		}
		else
			return false;
	}

	public Map<String, Object> transferMoney(String openId, int amount, String reason, String clientIp) throws Exception {
		
		transferMoneyParam.put("mch_appid", wxConfig.wxappid);
		transferMoneyParam.put("mchid", wxConfig.mchId);
		transferMoneyParam.put("nonce_str", _WechatUtils.createUniqueString());
		transferMoneyParam.put("partner_trade_no",  _WechatUtils.createBillNumber(wxConfig.mchId));
		transferMoneyParam.put("openid", openId);
		transferMoneyParam.put("check_name", "NO_CHECK");
		transferMoneyParam.put("amount", amount);
		transferMoneyParam.put("desc", reason);
		transferMoneyParam.put("spbill_create_ip", clientIp);
		
		transferMoneyParam.put("sign", _WechatUtils.createSinature(transferMoneyParam, wxConfig.payKey));
		
		
		String xmlDataString = _WechatUtils.Map2XMLString(transferMoneyParam);
		System.out.println(xmlDataString);
		
		String ret = wxConfig.certFileBin == null ?
				_WechatUtils.httpsPostSend(xmlDataString, wxConfig.getTransferMoneyUrl(), wxConfig.certFile_p12, wxConfig.mchId) :
				_WechatUtils.httpsPostSend(xmlDataString, wxConfig.getTransferMoneyUrl(), wxConfig.certFileBin, wxConfig.mchId)  ;
		
		Map<String, Object> retMessage = _WechatUtils.parseXML(ret, new String().getClass());
		
		return retMessage;
	}

}
