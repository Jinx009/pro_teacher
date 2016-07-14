package nbBase.service.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nbBase.database.models.ZaFrontUserWx;
import nbBase.database.models.ZaFrontWxConfig;
import nbBase.database.models.ZaUser;
import nbBase.service.wechat.WechatConfigure;
import nbBase.service.wechat._WxIF_Configure;

public class DataWrapUtil {

	/**
	 * 
	 * @param wxc
	 * @return
	 */
	public static WechatConfigure wrapWxConfigMap(ZaFrontWxConfig wxc, _WxIF_Configure wxConIf) {
			if( wxc == null ) return null;
			
			WechatConfigure wxCon = new WechatConfigure(wxConIf);
			wxCon.payKey = wxc.getPayKey();
			wxCon.mchId = wxc.getMchId();
			wxCon.wxappid = wxc.getWxappid();
			wxCon.certFile_p12 = wxc.getCertFile_p12();
			wxCon.server_ip = wxc.getServerDefaultIp();
			wxCon.appSecret = wxc.getAppSecret();
			wxCon.orderDefaultExpireTime = wxc.getOrderDefaultExpireTime();
			wxCon.wxPayNotifyUrl = wxc.getWxPayNotifyUrl();
			wxCon.encodingAESKey = wxc.getEncodingAesKey();
			wxCon.idInDB = wxc.getId();
			wxCon.serverName = wxc.getServerName();
			wxCon.tplMsgPaySuccess = wxc.getTplmsgPaySuccess();
			wxCon.resourcePath = wxc.getResourcePath();
			wxCon.companyLogoUrl = wxc.getCompanyLogoUrl();
			wxCon.configName = wxc.getConfigName();
			wxCon.tplMsgMatchResultConfirm = wxc.getTplmsgMatchResultConfirm();
			if( wxc.getResourceBrowsPath().toLowerCase().startsWith("http") )
				wxCon.resourceBrowsPath = wxc.getResourceBrowsPath();
			else{
				if( wxc.getResourceBrowsPath() != null && wxc.getResourceBrowsPath().length() > 0)
					wxCon.resourceBrowsPath = wxc.getServerName() + "/" + wxc.getResourceBrowsPath();
				else
					wxCon.resourceBrowsPath = wxc.getServerName();
			}
			
			wxCon.certFileBin = wxc.getCertP12Bin();
			
			wxCon.currentPageToken = wxc.getCurrentPageToken();
			wxCon.currentPageTokenExpire = wxc.getCurrentPageTokenExpire();
			wxCon.currentJSTicket = wxc.getCurrentJsTicket();
			wxCon.currentJSTicketExpire = wxc.getCurrentJsTicketExpire();
			
			return wxCon;
	}


	public static Map<String, Object> wrapDoGetWxUserList(List<ZaFrontUserWx> wxUsers, List<ZaFrontWxConfig> wxConfigs,
			int totalUserNumber) {
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("totalUserNumber", totalUserNumber);
		List<Map<String,Object>> userList = new ArrayList<Map<String,Object>>(); 
		for( ZaFrontUserWx wxUser : wxUsers){
			Map<String, Object> oneUser = new HashMap<String, Object>();
			oneUser.put("id", wxUser.getId());
			oneUser.put("userInfoId", wxUser.getUserInfoId());
			oneUser.put("wxOpenId", wxUser.getWxOpenId());
			oneUser.put("wxNickName", wxUser.getWxNickname());
			oneUser.put("wxSex", wxUser.getWxSex() == 1 ? "男" : (wxUser.getWxSex() == 2 ? "女" : "未知") );
			oneUser.put("headImgUrl", wxUser.getHeadImgUrl());
			oneUser.put("wxAppName", getWxNameByAppId(wxUser.getWxAppId(), wxConfigs));
			userList.add(oneUser);
		}
		ret.put("users", userList);
		
		return ret;
	}

	private static String getWxNameByAppId(String wxAppId, List<ZaFrontWxConfig> wxConfigs) {
		
		for( ZaFrontWxConfig wxConfig : wxConfigs){
			if( wxConfig.getWxappid().equals(wxAppId) ){
				return wxConfig.getConfigName();
			}
		}
		
		return null;
	}


	public static Map<String, Object> wrapUserInfo(ZaUser user) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("userId", user == null ? null : user.getId());
		ret.put("username", user == null ? null : user.getUsername() );
		ret.put("isAuthed", user == null ? false : true);
		ret.put("realName", user == null ? null : user.getRealname() );
		
		return ret;
	}

}
