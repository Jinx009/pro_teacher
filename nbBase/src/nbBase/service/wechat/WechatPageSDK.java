package nbBase.service.wechat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;

public class WechatPageSDK {

	private WechatConfigure wxConfig  =null;
	
	public WechatPageSDK(WechatConfigure wxc){
		this.wxConfig = wxc;
	}
	
	
	private String signature = null;
	private Integer lastErrorcode = null;
	private String lastErrormsg = null;
	private String noncestr = null;
	private String url = null;
	private Long timestamp = null;
	
	
	private Long totalUserNumber = null;
	private List<String> userList = null;
	private Boolean isOpenIdUser = null;
	
	
	public Map<String, Object> getValueMap(){
		Map<String, Object> value = new TreeMap<String, Object>();
		value.put("jsapi_ticket", this.wxConfig.currentJSTicket);
		value.put("signature", signature);
		value.put("lastErrorcode", lastErrorcode);
		value.put("lastErrormsg", lastErrormsg);
		value.put("noncestr", noncestr);
		value.put("url", url);
		value.put("timestamp", timestamp);
		value.put("jsTicketExpireTime", this.wxConfig.currentJSTicketExpire);
		value.put("wxappid", wxConfig.wxappid);
		value.put("page_access_token", wxConfig.currentPageToken);
		value.put("page_token_expireTime", wxConfig.currentPageTokenExpire);
		value.put("totalUserNumber", totalUserNumber);
		value.put("userList", userList);
		value.put("isOpenIdUser", isOpenIdUser);
		return value;
		
	}
	
	/**
	 * 发送模板消息
	 * @param wtlv
	 * @return
	 * @throws Exception 
	 */
	public boolean postTemplateMsg(WechatTemplateMsgValueSet wtlv) throws Exception{
		
		if( ! wtlv.checkParamter() ){
			this.lastErrorcode = wtlv.getLastErrorCode();
			this.lastErrormsg = wtlv.getLastErrorMsg();
			return false;
		}
		
		if( !getPageAccessToken() ){
			return false;
		}
		
		String retString = 
				_WechatUtils.httpPostSend(wtlv.formJSONString(), wxConfig.getSendTemplateMsgUrl(this.wxConfig.currentPageToken));
		Map<String, Object> retMap = JSONObject.parseObject(retString);
		return checkLastError(retMap);
	}
	
	/**
	 * 获取所有用户的openId列表
	 * @param pageAccessToken
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private boolean getUserList(String pageAccessToken) throws Exception{
		String nextOpenId = null;
		
		this.userList = new ArrayList<String>();
		
		do{
			
			String url = wxConfig.getFetchUserListUrl(pageAccessToken, nextOpenId);
			String retData = _WechatUtils.httpGet(url);
			
			Map<String, Object> retValues = (Map<String, Object>) JSONObject.parseObject(retData);
			if( !checkLastError(retValues) ){
				return false;
			}
			this.totalUserNumber = Long.valueOf(((Integer)retValues.get("total")).toString()); 
			Map<String, Object> data = (Map<String, Object>)retValues.get("data");
			if( data != null)
				this.userList.addAll((List<String>)data.get("openid"));
			nextOpenId = (String) retValues.get("next_openid");
			
		}while( nextOpenId != null && nextOpenId.length() > 0);
		
		return true;
	}
	
	/**
	 * 为了节省内存，需要经常释放用户表单数据
	 */
	public void cleanUserList(){
		this.userList = null;
		this.totalUserNumber = null;
		this.isOpenIdUser = null;
	}
	
	/**
	 * 查看用户是否已关注的用户
	 * @param accessToken
	 * @param openId 如果参数为空则只是获取列表
	 * @return
	 * @throws Exception
	 */
	public boolean ifOpenIdIsUser(String accessToken, String openId) throws Exception{
		
		this.isOpenIdUser = false;
		
		if( !getUserList(accessToken) )
			return false;
		
		if( openId == null ) //如果参数为空则只是获取列表
			return true;
		
		for( String it : this.userList){
			if( it.equals(openId) ){
				this.isOpenIdUser = true;
			}
		}
		
		return true;
		
	}
	
	
	/**
	 * 获取前端页面使用的accessToken，与后台用的token是不同的 
	 * @param pageToken
	 * @param pageTokenExpire
	 * @return
	 * @throws Exception
	 */
	public boolean getPageAccessToken() throws Exception{
		
		boolean result = true;
		
		String pageToken = this.wxConfig.currentPageToken;
		Long pageTokenExpire = this.wxConfig.currentPageTokenExpire;
		
		if( pageToken != null && pageTokenExpire != null){
			Long currentTime = System.currentTimeMillis()/1000;
			if( pageTokenExpire < currentTime - 1){
				pageToken = null;
			}
		}
		
		if( pageToken == null){
			
			String url = wxConfig.getFetchPageAccessTokenUrl();
			String httpRet = _WechatUtils.httpGet(url);

			Map<String, Object> retValues = (Map<String, Object>) JSONObject.parseObject(httpRet);
			if( checkLastError(retValues) ){
				this.wxConfig.currentPageToken = (String) retValues.get("access_token");
				this.wxConfig.currentPageTokenExpire = System.currentTimeMillis()/1000+Long.valueOf(((Integer)retValues.get("expires_in")).toString());
				result = true;
			}else{
				this.wxConfig.currentPageToken = null;
				this.wxConfig.currentPageTokenExpire = null;
				result = false;
			}
			
			if( this.wxConfig.dbIf != null) //如果已经实现了wxConfig和数据库之间的接口，就调用更新之
				this.wxConfig.dbIf.updatePageToken(
						this.wxConfig.idInDB, 
						this.wxConfig.currentPageToken, 
						this.wxConfig.currentPageTokenExpire );
			
		}
		
		return result;
		
	}
	
	/**
	 * 
	 * @param ret
	 * @return
	 */
	private boolean checkLastError(Map<String, Object> ret){
		if( ret.get(_WechatKeyDefine.errcode) == null )
			return true;
		else{
			lastErrorcode = (Integer) ret.get(_WechatKeyDefine.errcode);
			lastErrormsg = (String) ret.get(_WechatKeyDefine.errmsg);
			System.out.println("errcode:("+lastErrorcode+") errmsg:["+lastErrormsg+"]");
			if( lastErrorcode == 0){
				return true;
			}
			return false;
		}
	}
	
	/**
	 * 
	 * @param pageToken
	 * @param pageTokenExpire
	 * @param jsTicket
	 * @param jsTicketExpire
	 * @return
	 * @throws Exception
	 */
	private boolean checkAccessTokenAndJSTicket() throws Exception{
		boolean ret = true;
		ret = getPageAccessToken();
		ret = getJSTicket() ;
		return ret;
	
	}
	
	/**
	 * 拿到jssdk的signature
	 * @param pageoken 可以为空，但是不能喝 jsTicket jsTicketExpire同时为空
	 * @param pageTokenExpire 可以为空
	 * @param jsTicket 可以为空
	 * @param jsTicketExpire 可以为空
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public boolean getJSSign(  String url) throws Exception{
		
		System.out.println("pageToken="+this.wxConfig.currentPageToken+"; pageTokenExpire="+this.wxConfig.currentPageTokenExpire+"; jsTicket="+this.wxConfig.currentJSTicket+"; jsTicketExpire="+this.wxConfig.currentJSTicketExpire);
		
		if( !checkAccessTokenAndJSTicket() ){
			return false;
		}
		
		
		//System.out.println("==>>pageToken="+pageToken+"; pageTokenExpire="+pageTokenExpire+"; jsTicket="+jsTicket+"; jsTicketExpire="+jsTicketExpire);
			TreeMap<String, Object> values = new TreeMap<String, Object>();
			
			this.url = url;
			noncestr = _WechatUtils.createUniqueString();
			Calendar cal = Calendar.getInstance();
			this.timestamp = cal.getTime().getTime()/1000;
			
			values.put("noncestr", noncestr);
			values.put("timestamp", timestamp);
			values.put("url", url);
			values.put("jsapi_ticket", this.wxConfig.currentJSTicket);
			
			this.signature = _WechatUtils.sha1Sign(values);
			
			return true;
	}

	/**
	 * 用accessToken来获取jsTicket, 也可放入已有的jsTick信息来验证是否有效
	 * @param accessToken 前端页面用的AccessToken
	 * @param jsTicket 已有的jsTicket信息，可以为空
	 * @param jsTicketExpire
	 * @return
	 * @throws Exception
	 */
	private boolean getJSTicket() throws Exception{
		boolean result = true;
		
		String accessToken = wxConfig.currentPageToken;
		String jsTicket = wxConfig.currentJSTicket;
		Long jsTicketExpire = wxConfig.currentJSTicketExpire;
		
		if( jsTicket != null && jsTicketExpire != null){
			Long currentTime = System.currentTimeMillis()/1000;
			if( jsTicketExpire < currentTime - 1){
				jsTicket = null;
			}
		}
		
		if( jsTicket == null){
		
			String apiUrl = wxConfig.getJSTicketUrl(accessToken);
			String httpRet = _WechatUtils.httpGet(apiUrl);
			
			Map<String, Object> ret = (Map<String, Object>)JSONObject.parseObject(httpRet);
			
			if( checkLastError(ret) ){
				this.wxConfig.currentJSTicket = (String) ret.get("ticket");
				this.wxConfig.currentJSTicketExpire = System.currentTimeMillis()/1000+Long.valueOf(((Integer)ret.get("expires_in")).toString());
				
				result = true;
			}
			else{
				this.wxConfig.currentJSTicket = null;
				this.wxConfig.currentJSTicketExpire = null;
				result = false;
			}
			
			if( this.wxConfig.dbIf != null) //如果已经实现了wxConfig和数据库之间的接口，就调用更新之
				this.wxConfig.dbIf.updateJSTicket(
						this.wxConfig.idInDB, 
						this.wxConfig.currentJSTicket, 
						this.wxConfig.currentJSTicketExpire );

		}
		
		return result;
	}
	
	public String getJsapi_ticket() {
		return this.wxConfig.currentJSTicket;
	}

	public String getSignature() {
		return signature;
	}

	public Integer getLastErrorcode() {
		return lastErrorcode;
	}

	public String getLastErrormsg() {
		return lastErrormsg;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public String getUrl() {
		return url;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public Long getJsTicketExpireTime() {
		return this.wxConfig.currentJSTicketExpire;
	}

	public String getPage_access_token() {
		return this.wxConfig.currentPageToken;
	}

	public Long getPage_token_expireTime() {
		return this.wxConfig.currentPageTokenExpire;
	}

	public Long getTotalUserNumber() {
		return totalUserNumber;
	}

	public List<String> getUserList() {
		return userList;
	}

	public Boolean getIsOpenIdUser() {
		return isOpenIdUser;
	}

}
