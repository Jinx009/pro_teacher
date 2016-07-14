package nbBase.service.front;

import nbBase.helper.common.nbReturn;
import nbBase.service.wechat.WechatConfigure;
import nbBase.service.wechat.WechatTokenUserInfo;

public interface UserInfoService {

	public WechatTokenUserInfo checkAndUpdateWXUserInfo(String wechatCode, WechatConfigure wxCon);

	public nbReturn getJSSign(	String url,
								WechatConfigure wxCon) throws Exception;

	public nbReturn checkIfUserFollowed(
			String openId, 
			WechatConfigure wxCon) throws Exception;

	public nbReturn verifyUser(String username, String password, String appId, String clientUUID);

	public nbReturn checkPhoneCode(Integer phoneCodeId, String phoneCode);

	public nbReturn RegisterUser(String username, String password, String mobile, String email, String appId,
			String realname);

	public nbReturn sendPhoneCode(String appId, String username, String sendReasonCommnets);

	public nbReturn resetPassword(String appId, String username, String password);
 
}
