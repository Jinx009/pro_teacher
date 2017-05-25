package nbBase.service.wechat;

public class WechatConfigure {

	public WechatConfigure( _WxIF_Configure If){
		this.dbIf = If;
	}
	
	/*捧场*/
	public String payKey = "0anglePengChang2016PengChang2016";
	public String mchId = "1295518101";
	public String wxappid = "wx9b3d1cb48fb48ff4";
	public String certFile_p12 = "/Users/yuhangs/Documents/WorkingStuff/zAnGle/捧场/捧场证书/apiclient_cert.p12";
	public String server_ip = "127.0.0.1";
	public String appSecret = "188be723d1bab5a44a7fdeeff06cc0f3";
	public Long orderDefaultExpireTime = 7200l;
	public String wxPayNotifyUrl = "http://pc.0angle.com/pay/wxPayCallback.html";
	public String encodingAESKey = "1iYFTRwrYWxAlOcxo9wdoWn38f7KGrMtegdvzV0gcqT";
	public Integer idInDB = 0;
	public String serverName = "http://pc.0angle.com";
	public String tplMsgPaySuccess = "";
	public String tplMsgMatchResultConfirm = "";
	public String resourcePath;
	public String resourceBrowsPath;
	public byte[] certFileBin;
	public String companyLogoUrl;
	public String configName;
	public String currentPageToken = null;
	public Long currentPageTokenExpire = null;
	public String currentJSTicket = null;
	public Long currentJSTicketExpire = null;
	public _WxIF_Configure dbIf = null;
	
//	
	/* NCDA*/
//	public static final String payKey = "CAIZHIDAO2016CAIZHIDAO2016CAIZHI";
//	public static final String mchId = "1304560401";
//	public static final String wxappid = "wxad7987ba40989a97";
//	public static final String certFile_p12 = "/Users/yuhangs/Documents/WorkingStuff/zAnGle/职业规划师项目/wechatPayCert/apiclient_cert.p12";
//	public static final String server_ip = "127.0.0.1";
//	public static final String appSecret = "b3937255dcc5e4953798146c547a4e92";
//	public static final Long orderDefaultExpireTime = 7200l;
//	public static final String wxPayNotifyUrl = "http://t03.0angle.com/test/nbBase/wxPayCallback.html";
	/**/
	
	
	public String sendAwardURL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	
	
	public String getPlaceUnionOrderUrl(){
		return "https://api.mch.weixin.qq.com/pay/unifiedorder";
	}
	
	public String getTransferMoneyUrl(){
		return "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	}
	
	public String getTokenFetchUrl(String code){
		return "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+wxappid+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code"; 
	}
	
	public String getTokenRefreshUrl(String token){
		return "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+wxappid+"&grant_type=refresh_token&refresh_token="+token;
	}
	
	public String getCheckTokenUrl(String token, String openId){
		return "https://api.weixin.qq.com/sns/auth?access_token="+token+"&openid="+openId;
	}
	
	public String getFetchUserInfoUrl(String token, String openId){
		return "https://api.weixin.qq.com/sns/userinfo?access_token="+token+"&openid="+openId+"&lang=zh_CN";
	}
	
	public String getJSTicketUrl(String accessToken){
		return "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
	}
	
	public String getFetchPageAccessTokenUrl(){
		return "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+wxappid+"&secret="+appSecret;
	}
	
	public String getFetchUserListUrl(String accessToken, String nextOpenId){
		return "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+ accessToken + (nextOpenId == null ? "" : "&next_openid="+nextOpenId);
	}
	
	public String getSendTemplateMsgUrl(String accessToken){
		return "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken;
	}
	
	public String getFetchWxImageUrl(String pageToken, String mediaId){
		return "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+pageToken+"&media_id="+mediaId;
	}
	
}
