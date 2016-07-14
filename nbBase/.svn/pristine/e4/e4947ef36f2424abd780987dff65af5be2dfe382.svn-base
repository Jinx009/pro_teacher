package nbBase.service.wechat;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;

public class WechatTokenUserInfo {

	private WechatConfigure wxConfig  =null;
	
	public WechatTokenUserInfo(WechatConfigure wxc){
		this.wxConfig = wxc;
	}
	
	
	private String access_token = null;
	private String refresh_token = null;
	private String scope = null;
	private String openid = null;//	用户的唯一标识
	private String nickname = null;//	用户昵称
	private String sex = null;//	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private String province = null;//	用户个人资料填写的省份
	private String city = null;//	普通用户个人资料填写的城市
	private String country = null;//	国家，如中国为CN
	private String headimgurl = null;	//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	private Integer headImgSize = null;
	private List<String> privilege = null;	//用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
	private String unionid = null;
	private Integer lastErrorcode = null;
	private String lastErrormsg = "";
	private Integer currentIdInDB = 0; //在数据库里面的用户ID，这个包作为工具用的时候，可以无视这个参数；
	
	public Map<String, Object> getValueMap(){
		Map<String, Object> value = new TreeMap<String, Object>();
		value.put("access_token", access_token);
		value.put("refresh_token", refresh_token);
		value.put("scope", scope);
		value.put("openid", openid);
		value.put("nickname", nickname);
		value.put("sex", sex);
		value.put("province", province);
		value.put("city", city);
		value.put("country", country);
		value.put("headimgurl", headimgurl);
		value.put("headImgSize", headImgSize);
		value.put("privilege", privilege);
		value.put("unionid", unionid);
		value.put("lastErrorcode", lastErrorcode);
		value.put("lastErrormsg", lastErrormsg);
		value.put("currentIdInDB", currentIdInDB);
		value.put("wxappid", wxConfig.wxappid);
		value.put("serverName", wxConfig.serverName);
		
		return value;
		
	}
	
	
	/**
	 * 通过code来获取用户信息, code 可以为null，这样方法会判断是否已经预存的access_token，并通过刷新来进行获取token和个人信息的操作
	 * @param code
	 * @return
	 */
	public boolean getInfoByCode(String code){
		
		if( !fetchToken(code) )
			return false;
		
		getUserInfo(access_token, openid);
		
		return true;
		
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
	 * 用微信code获取access_token和refresh_token
	 * @param code
	 * @return
	 */
	private boolean fetchToken(String code){
		
		if( access_token != null && refresh_token != null && openid != null){
			
		
			if( !checkToken(access_token, openid) ){
				
				//刷新token
				if( refreshToken(refresh_token) ){
					return true;
				}
				//到这里说明刷新token失败了
			}
		}
			
		
		String url = wxConfig.getTokenFetchUrl(code);
		String ret = null;
		try {
			ret = _WechatUtils.httpGet(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, Object> httpRet = (Map<String, Object>)JSONObject.parseObject(ret);
		if( checkLastError(httpRet) ){
			this.access_token = (String) httpRet.get("access_token");
			this.refresh_token = (String) httpRet.get("refresh_token");
			this.openid = (String) httpRet.get("openid");
			this.scope = (String) httpRet.get("scope");
			if( this.scope == _WechatKeyDefine.scope_snsapi_userinfo ){
				getUserInfo(access_token, openid);
			}
			return true;
		
		}
		return false;
	}
	
	/**
	 * 通过 scope user info 获取用户信息
	 * @param access_token
	 * @param openId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean getUserInfo(String access_token, String openId){
		String url = wxConfig.getFetchUserInfoUrl(access_token, openId);
		
		String ret = null;
		try {
			ret = _WechatUtils.httpGet(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, Object> httpRet = (Map<String, Object>)JSONObject.parseObject(ret);
		if( checkLastError(httpRet)){
		
			this.openid = (String) httpRet.get("openid");
			this.nickname = (String) httpRet.get("nickname");
			this.sex = ((Integer) httpRet.get("sex")) == 1 ? "male" : "female";
			this.province = (String) httpRet.get("province");
			this.city = (String) httpRet.get("city");
			this.country = (String) httpRet.get("country");
			this.headimgurl = (String) httpRet.get("headimgurl");
			if( this.headimgurl != null && this.headimgurl.length() > 0){
				String[] tmp = headimgurl.split("/");
				this.headImgSize = Integer.valueOf(tmp[tmp.length-1]);
			}
			this.privilege = (List<String>) httpRet.get("privilege");
			this.unionid = (String) httpRet.get("unionid");			
			return true;
		}
		
		return false;
	}
	
	/**
	 * 用refresh token来刷新token
	 * @param refresh_token
	 * @return
	 */
	private boolean refreshToken(String refresh_token){
		String url = wxConfig.getTokenRefreshUrl(refresh_token);
		String ret = null;
		try {
			ret = _WechatUtils.httpGet(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, Object> httpRet = (Map<String, Object>)JSONObject.parseObject(ret);
		
		if( checkLastError(httpRet) ){

			this.access_token = (String) httpRet.get("access_token");
			this.refresh_token = (String) httpRet.get("refresh_token");
			this.openid = (String) httpRet.get("openid");
			this.scope = (String) httpRet.get("scope");
			return true;
			
		}
		
		return false;
	}
	
	/**
	 * 检查access token是否有效
	 * @param token
	 * @param openId
	 * @return
	 */
	private boolean checkToken(String token, String openId){
		String url = wxConfig.getCheckTokenUrl(token, openId);
		String ret = null;
		try {
			ret = _WechatUtils.httpGet(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> httpRet =  (Map<String, Object>)JSONObject.parseObject(ret);
		
		Long errcode = (Long)httpRet.get(_WechatKeyDefine.errcode);
		String errmsg = (String)httpRet.get(_WechatKeyDefine.errmsg);
		
		if(  errcode== null || errcode != 0l  ){
			System.out.println(errmsg);
			return false;
		}
		return true;
	}
	
	public String getAccess_token() {
		return access_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public String getOpenid() {
		return openid;
	}

	public String getNickname() {
		return nickname;
	}

	public String getSex() {
		return sex;
	}

	public String getProvince() {
		return province;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public Integer getHeadImgSize() {
		return headImgSize;
	}

	public List<String> getPrivilege() {
		return privilege;
	}

	public String getUnionid() {
		return unionid;
	}
	
	public String getCurrent_scope() {
		return scope;
	}

	public void setCurrent_scope(String current_scope) {
		this.scope = current_scope;
	}
	
	public Integer getLastErrorcode() {
		return lastErrorcode;
	}

	public String getLastErrormsg() {
		return lastErrormsg;
	}


	public Integer getCurrentIdInDB() {
		return currentIdInDB;
	}


	public void setCurrentIdInDB(Integer currentIdInDB) {
		this.currentIdInDB = currentIdInDB;
	}
	
}
