package nbBase.service.front;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nbBase.dao.ZaSmsSendDao;
import nbBase.dao.ZaSmsTemplateDao;
import nbBase.dao.ZaUserDao;
import nbBase.dao.ZaUserWxDao;
import nbBase.dao.ZaWxPayUnionOrderDao;
import nbBase.dao.common._WxConfigDao;
import nbBase.database.models.ZaFrontUserWx;
import nbBase.database.models.ZaSmsSend;
import nbBase.database.models.ZaSmsTemplate;
import nbBase.database.models.ZaUser;
import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbStringUtil;
import nbBase.helper.common.nbReturn.ReturnCode;
import nbBase.service.common.DataWrapUtil;
import nbBase.service.common.If_ErrorCheckService;
import nbBase.service.common.WxCommonService;
import nbBase.service.wechat.WechatConfigure;
import nbBase.service.wechat.WechatPageSDK;
import nbBase.service.wechat.WechatTokenUserInfo;
import nbBase.service.wechat._WechatKeyDefine;



@Service("frontUserService")
public class UserInfoServiceImpl implements UserInfoService, If_ErrorCheckService{
	

	
	@Autowired
	ZaUserWxDao userWxDao;
	
	@Autowired
	ZaWxPayUnionOrderDao wxPayUnionOrderDao;
	
	@Autowired
	_WxConfigDao wxConfigDao;
	
	@Autowired
	ZaUserDao userDao;
	
	@Autowired
	WxCommonService wxCommonService;
	
	@Autowired
	ZaSmsSendDao smsSendDao;
	
	@Autowired
	ZaSmsTemplateDao smsTemplateDao;	
	
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
	public WechatTokenUserInfo checkAndUpdateWXUserInfo(String wechatCode, WechatConfigure wxCon) {
		
		WechatTokenUserInfo wechatToken = new WechatTokenUserInfo(wxCon);
		
		boolean ret = wechatToken.getInfoByCode(wechatCode);
		this.SetLastError(wechatToken.getLastErrorcode(), wechatToken.getLastErrormsg());		
		
		if( ret ){
			/**
			 * 放入数据库中
			 */
			ZaFrontUserWx userWx = userWxDao.findByOpenId(wechatToken.getOpenid());
			if( userWx == null){
				userWx = new ZaFrontUserWx();
			}
				userWx.setWxAppId(wxCon.wxappid);
				userWx.setWxNickname(wechatToken.getNickname());
				userWx.setWxOpenId(wechatToken.getOpenid());
				userWx.setWxSex(wechatToken.getSex().equals("male") ? 1 : 2);
				userWx.setWxUnionId(wechatToken.getUnionid());
				userWx.setHeadImgUrl(wechatToken.getHeadimgurl());
				if(userWx.getId() == 0 )
					userWx = userWxDao.save(userWx);
				else
					userWx = userWxDao.update(userWx);
				
				System.out.println("userId: "+userWx.getId()+" 已经保存");
			
			wechatToken.setCurrentIdInDB(userWx.getId());
			return wechatToken;
		}
		else
			return null;
	}

	@Override
	public nbReturn getJSSign(String url, WechatConfigure wxCon)
			throws Exception {
		
		WechatPageSDK wxPages = new WechatPageSDK(wxCon);
		
		
		nbReturn nbRet = new nbReturn();
		
		if(  wxPages.getJSSign(url) ){
			nbRet.setObject(wxPages);
			return nbRet;
		}
		
		nbRet.setError(Long.valueOf(wxPages.getLastErrorcode()), wxPages.getLastErrormsg());
		return nbRet;
		
	}

	@Override
	public nbReturn checkIfUserFollowed(String openId, WechatConfigure wxCon) throws Exception {
		
		nbReturn nbRet = new nbReturn();
		
		WechatPageSDK pageSDK = new WechatPageSDK(wxCon);
		
		if( pageSDK.ifOpenIdIsUser(wxCon.currentPageToken, openId) ){
			
			Map<String, Object> tmpRet = new HashMap<String, Object>();
			tmpRet.put(_WechatKeyDefine.isUser, pageSDK.getIsOpenIdUser());
			nbRet.setObject(tmpRet);
			
		}
		else{
			nbRet.setError(ReturnCode.GENERAL_ERROR);
		}
		
		pageSDK.cleanUserList();
		
		return nbRet;
	}

	/**
	 * 用于验证用户名及密码
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @param appID    商家ID
	 * @param clientUuid 终端的UUID，如果是web调用可填写IP地址，可为空，但是请谨慎使用
	 * @return 返回nbReturn类型的返回值。object为UserInfoVo
	 * 
	 */
	@Override
	public nbReturn verifyUser(String username, 
			                   String password, 
			                   String appID, 
			                   String clientUuid) {
		
		nbReturn nbRet = new nbReturn();
		if( appID == null ) {
			appID = "syhAppID";
		}
		if( clientUuid == null ) clientUuid = "none";
		
		
		ZaUser user = null;
		try {
			user = userDao.verifyUser(username, nbStringUtil.encryptMD5(password), appID);
		} catch (Exception e) {
			nbRet.setError(ReturnCode.GENERAL_ERROR);
			e.printStackTrace();
		}
		
		if( user == null ){ //用户认证失败
			if( nbRet.isSuccess() )
				nbRet.setError(ReturnCode.USERNAME_PASSWORD_ERROR);
			
		}else{
			nbRet.setObject(user);
		}
		return nbRet;
	}

	/**
     * 发行一个手机验证码
     * @param phoneCheckAffairid 验证码的记录id
     * @param phoneCheckCode 验证码
     * @return retCode
     * @return retMessage
     * @return retContent{
     * @return }
     */
	@Override
	public nbReturn checkPhoneCode(Integer phoneCheckAffairid, String phoneCheckCode) {
		
		List<ZaSmsSend> pccList = smsSendDao.findByIdAndCheckCode(phoneCheckAffairid.intValue(), phoneCheckCode);
		
		nbReturn nbRet = new nbReturn();
		
		if( pccList == null || pccList.size() == 0){
			nbRet.setError(ReturnCode.REQUESTED_PHONE_CODE_NOT_FOUND);
			return nbRet;
		}
		for( ZaSmsSend pcc : pccList){
			
			if( pcc.getSendStatus() == 3 || pcc.getSendStatus() == 4){
				nbRet.setError(ReturnCode.REQUESTED_PHONE_CODE_EXPIRED);
			}
			
			if( pcc.getSendStatus() == 1 ){//发送成功
				Calendar cal = Calendar.getInstance();
				int lifecycle = pcc.getLifecycle();
				Date date = pcc.getRequestedTime();
				
				if( cal.getTime().getTime() > ( date.getTime()+lifecycle*1000 ) ){ //过期了
					pcc.setSendStatus(4);
					smsSendDao.update(pcc);
				}else{ // 没过期
					nbRet.setError(ReturnCode._SUCCESS);
					pcc.setSendStatus(3);
					smsSendDao.update(pcc);
				}
			}
		}
		
		return nbRet;
	}

	/**
	 * 用于注册新用户
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @param mobile 电话号码 可为空
	 * @param email 邮件地址 可为空
	 * @param appID 商家ID
	 * @return 返回成功失败信息，以及成功时候的用户信息，并没有返回token
	 * 
	 */
	@Override
	public nbReturn RegisterUser(String username, String password,
			String mobile, String email, String appID, String realName){
		
		nbReturn nbRet = new nbReturn();
		
		ZaUser checkUser = userDao.findByUsernameAndAppid(username, appID);
		
		if( checkUser != null ){ //用户名已经存在
			nbRet.setError(ReturnCode.USERNAME_ALREADY_EXIST);
			return nbRet;
		}
		//用户名可以用，开始注册用户
		ZaUser user = new ZaUser();
		try {
			user.setEmail(email);
			user.setRealname(realName);
			user.setMobilePhone(mobile);
			user.setPassword(nbStringUtil.encryptMD5(password));
			user.setUsername(username);
			user.setApplicationId(appID);
			user.setDescAsLeader("");
			Calendar cal = Calendar.getInstance();
			user.setUserOpenCode(nbStringUtil.encryptMD5(	appID
																+username
																+String.valueOf(cal.getTimeInMillis())
																));
		} catch (Exception e) {
			e.printStackTrace();
			nbRet.setError(ReturnCode.GENERAL_ERROR);
		}
		if( nbRet.isSuccess() ){
			user = userDao.save(user);
			nbRet.setObject(user);
		}
		return nbRet;
	}
	
	/**
	 * 发行一个手机验证码，SMS事件Code是1
	 * @param appId
	 * @param PhoneNumber
	 * @param SendReasonCommnets
	 * @return
	 * @throws Exception
	 */
	@Override
	public nbReturn sendPhoneCode(String appId, String PhoneNumber, String SendReasonCommnets) {
		
		ZaSmsSend smsSend = null;
		Long smsAffairId = 1l; // 事件code 1表示发送手机验证码
		/***如果需要通过数据库来管理已经发送的验证码，可以去掉注释部分
		 */
		nbReturn nbRet = checkSmsSendRecord(appId, PhoneNumber, 1l/*事件代码1表示发送手机验证码*/, SendReasonCommnets);
		
		if( nbRet.isSuccess() || nbRet.getRetCode() == ReturnCode.NO_RECORDS_CAN_BE_REUSE ){
			smsSend = (ZaSmsSend) nbRet.getObject();
			return createNewRecordAndSendSms(appId, PhoneNumber, smsAffairId, SendReasonCommnets, smsSend);
		}
		return nbRet;
		
	}
	
	/**
	 * 当用户要求发送SMS的时候，先看数据库里面是否有可用的记录条来使用，尽量减少数据库爆炸式增长
	 * @param appId
	 * @param PhoneNumber
	 * @param templateId
	 * @param SendReasonCommnets
	 * @return
	 * @throws Exception
	 */
	private nbReturn checkSmsSendRecord(
			String appId, 
			String PhoneNumber, 
			Long templateId,
			String SendReasonCommnets){
		
		nbReturn nbRet = new nbReturn();
		nbRet.setError(ReturnCode.NO_RECORDS_CAN_BE_REUSE);
		
		List<ZaSmsSend> smsSendList = smsSendDao.findByPhonenumberAndAppid(PhoneNumber,  appId, templateId);

		for( ZaSmsSend pcc: smsSendList){
			if( pcc.getSendStatus() == 3 ||//已经用掉了，没作用的记录
				pcc.getSendStatus() == 4 ){//已经过期了，没作用的记录
				continue;
			}
			
			if( pcc.getSendStatus() <= 2 ){//发送失败了,未发送，发送成功，看看是否过期，如果过期了就更新状态，如果没有过期，且发送用途sendReasonCode和参数里是一致的话就再发送一次
				
				int templateIdStored = 0;
				if( pcc.getNbSmsTemplate() != null){
					templateIdStored = pcc.getNbSmsTemplate().getId();
				}
				
				if( templateIdStored == templateId ){//如果发送原因是一直的，哪怕都是空也算一致的
					
					Calendar cal = Calendar.getInstance();
					long allowToSendInterval = pcc.getContinouseTryCycle();
					long allowToSendDateTime = pcc.getLatestEventTime().getTime()+allowToSendInterval*1000;
					
					if( cal.getTimeInMillis() < allowToSendDateTime){
						pcc.setLatestEventTime(cal.getTime());
						smsSendDao.update(pcc);
						nbRet.setError(ReturnCode.PHONECODE_REQUESTED_TOO_OFTEN);
						return nbRet;
					}
					
					long lifeCycle = pcc.getLifecycle();
					long codeToBeExpired = pcc.getRequestedTime().getTime()+lifeCycle*1000;
					if( cal.getTime().getTime() > codeToBeExpired ){//已经过期了
						pcc.setSendStatus(4);//设置成过期了
						smsSendDao.update(pcc);
					}else{//如果没有过期
						nbRet.setError(ReturnCode._SUCCESS);
						nbRet.setObject(pcc);
						return nbRet;
					}
				}
			}
		}
		return nbRet;
		
	}
	
	/**
	 * 创建一个新的发送
	 * @param appId
	 * @param PhoneNumber
	 * @param SendReasonCode
	 * @param SendReasonCommnets
	 * @param phoneCheckCode 如果为空的话，就表示新建一个发送记录
	 * @return
	 * @throws Exception 
	 */
	private nbReturn createNewRecordAndSendSms(
			String appId, 
			String PhoneNumber, 
			Long SendReasonCode,
			String SendReasonCommnets,
			ZaSmsSend nbSmsSend){
		
		nbReturn nbRet = new nbReturn();
		
		String verifyCode = CommonHelper.generateRandomDigit(6);//生成6位随机数;
		Calendar cal = Calendar.getInstance();
		
		if( nbSmsSend == null ){
			nbSmsSend = new ZaSmsSend();
		
			nbSmsSend.setContinouseTryCycle(60);//60秒内不可再发送
			nbSmsSend.setLifecycle(300); //300秒内有效
			ZaSmsTemplate smsTemplate = smsTemplateDao.find(SendReasonCode.intValue());
			if( smsTemplate == null){
				nbRet.setError(ReturnCode.SMS_TEMPLATE_NOT_FOUND);
				return nbRet;
			}//在数据库中找到事件所需要的消息模板。
			
			nbSmsSend.setNbSmsTemplate(smsTemplate);
			nbSmsSend.setRequestReasonComment(SendReasonCommnets);
			nbSmsSend.setSendStatus(0);//创建发送
			nbSmsSend.setTargetPhoneNumber(PhoneNumber);
			nbSmsSend.setApplicationid(appId);//商户的ID
			nbSmsSend.setRequestedTime(cal.getTime());//申请发送的时间
			nbSmsSend.setLatestEventTime(cal.getTime());
			nbSmsSend.setPhoneCode(verifyCode);
			
			//生成要发送的text文本
			Map<String, String> replacementList = new HashMap<String, String>();
			replacementList.put("phoneVerifyCode", verifyCode);
			replacementList.put("availableTime", String.valueOf((int)(nbSmsSend.getLifecycle()/60)));
			
			nbSmsSend.setSmsText(CommonHelper.replaceTheKeyWordForSMSSend(smsTemplate.getRequestTemplate(), replacementList));
			//text文本done
			
			smsSendDao.save(nbSmsSend);
			//以上创建发送请求成功，先保存到数据库
		}
		
		nbRet = CommonHelper.sendSMSNotification(PhoneNumber, nbSmsSend.getSmsText());
		//实际发送出去
		
		cal = Calendar.getInstance();
		nbSmsSend.setLatestEventTime(cal.getTime());
		if( !nbRet.isSuccess() ){
			nbSmsSend.setSendStatus(2); //发送失败
		}
		else{
			nbSmsSend.setSendStatus(1); //发送成功
		}
		nbSmsSend = smsSendDao.update(nbSmsSend);
		//以上把发送结果更新到数据库中
		
		if( nbRet.isSuccess() )
			nbRet.setObject(nbSmsSend);
		
		return nbRet;
	}
	
	/**
	 * 用于更新用户密码
	 * 
	 * @param appID 
	 * @param username 
	 * @param password 
	 * @return 返回成功失败信息
	 * 
	 */
	@Override
	public nbReturn resetPassword(String appID, String username, String password) {
		
		nbReturn nbRet = new nbReturn();
		ZaUser checkUser = userDao.findByUsernameAndAppid(username, appID);
		if( checkUser == null ){ //用户不存在
			nbRet.setError(ReturnCode.NO_SUCH_USER);
			return nbRet;
		}
		try {
			checkUser.setPassword(nbStringUtil.encryptMD5(password));
		} catch (Exception e) {
			e.printStackTrace();
			nbRet.setError(ReturnCode.GENERAL_ERROR);
		}
		if( nbRet.isSuccess() )
			nbRet.setObject(userDao.update(checkUser));
		return nbRet;
	}

}
