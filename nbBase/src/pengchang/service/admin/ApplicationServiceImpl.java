package pengchang.service.admin;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nbBase.dao.ZaAdminUserDao;
import nbBase.dao.ZaUserWxDao;
import nbBase.dao.common._WxConfigDao;
import nbBase.database.common.QueryParam;
import nbBase.database.models.ZaAdminUser;
import nbBase.database.models.ZaFrontUserWx;
import nbBase.database.models.ZaFrontWxConfig;
import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbReturn.ReturnCode;
import nbBase.helper.common.nbStringUtil;
import nbBase.service.common.DataWrapUtil;
import pengchang.dao.PCEventDao;
import pengchang.dao.PCEventRuleDao;
import pengchang.database.models.PCCoreEvent;
import pengchang.database.models.PCCoreEventRule;
import pengchang.service.front.DataWrap;

@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {
	
	
	@Autowired
	ZaAdminUserDao adminUserDao;
	
	@Autowired
	 _WxConfigDao wxConfigDao;
	
	@Autowired
	ZaUserWxDao userWxDao;
	
	@Autowired
	PCEventDao eventDao;
	
	@Autowired
	PCEventRuleDao eventRuleDao;

	
	@Override
	public nbReturn getWxConfigList(ZaAdminUser adminUser) {
		
		nbReturn nbRet = new nbReturn();
		
		List<ZaFrontWxConfig> wxConfigList = this.wxConfigDao.findAll();
		
		if( wxConfigList == null ){
			wxConfigList = new ArrayList<ZaFrontWxConfig>();
		}
		
		nbRet.setObject(
				DataWrap.adminWrapDoGetWxConfigList(wxConfigList) );
		
		return nbRet;
	}


	@Override
	public nbReturn getWxConfigDetail(Integer configId) {
		
		nbReturn nbRet = new nbReturn();
		
		ZaFrontWxConfig wxConfig = this.wxConfigDao.find(configId);
		
		if( wxConfig == null ){
			nbRet.setError(ReturnCode.GENERAL_ERROR);
			return nbRet;
		}
		
		nbRet.setObject(
				DataWrap.adminWrapDoGetWxConfigDetail(wxConfig) );
		
		
		return nbRet;
	}


	@Override
	public nbReturn saveWxConfigure(Map<String, Object> jsonObject) {
		nbReturn nbRet = new nbReturn();
		Integer id = CommonHelper.getObjectInteger(jsonObject.get("id"));
		ZaFrontWxConfig wxConfig = null;
		if( id != 0 ){
			wxConfig = this.wxConfigDao.find(id);
			if(wxConfig == null ){
				nbRet.setError(ReturnCode.WXCON_NOT_MATCH);
				return nbRet;
			}
		}
		
		if( wxConfig == null )
			wxConfig = new ZaFrontWxConfig();											

		wxConfig.setAppSecret(CommonHelper.getObjectString(jsonObject.get("appSecret")));
		wxConfig.setPayKey(CommonHelper.getObjectString(jsonObject.get("payKey")));
		wxConfig.setMchId(CommonHelper.getObjectString(jsonObject.get("mchId")));
		wxConfig.setWxappid(CommonHelper.getObjectString(jsonObject.get("wxappid")));
		wxConfig.setCertFile_p12(CommonHelper.getObjectString(jsonObject.get("certFileP12")));
		wxConfig.setServerDefaultIp(CommonHelper.getObjectString(jsonObject.get("serverDefaultIp")));
		wxConfig.setOrderDefaultExpireTime(CommonHelper.getObjectLong(jsonObject.get("orderDefault")));
		wxConfig.setWxPayNotifyUrl(CommonHelper.getObjectString(jsonObject.get("wxPayNotifyUrl")));
		wxConfig.setEncodingAesKey(CommonHelper.getObjectString(jsonObject.get("encodingAesKey")));
		wxConfig.setConfigName(CommonHelper.getObjectString(jsonObject.get("configName")));
		wxConfig.setIsActive(CommonHelper.getObjectBoolean(jsonObject.get("isActive"),false));
		wxConfig.setIsDefault(CommonHelper.getObjectBoolean(jsonObject.get("isDefault"),false));
		wxConfig.setServerName(CommonHelper.getObjectString(jsonObject.get("serverName")));
		wxConfig.setTplmsgPaySuccess(CommonHelper.getObjectString(jsonObject.get("tplmsgPaySuccess")));
		wxConfig.setResourcePath(CommonHelper.getObjectString(jsonObject.get("resourcePath")));
		wxConfig.setResourceBrowsPath(CommonHelper.getObjectString(jsonObject.get("resourceBrowsPath")));
		wxConfig.setCompanyName(CommonHelper.getObjectString(jsonObject.get("companyName")));
		wxConfig.setCompanyLogoUrl(CommonHelper.getObjectString(jsonObject.get("companyLogoUrl")));
		wxConfig.setTplmsgCfResult(CommonHelper.getObjectString(jsonObject.get("tplmsgCfResult")));
		wxConfig.setTplmsgMatchResultConfirm(CommonHelper.getObjectString(jsonObject.get("tplmsgMatchResultConfirm")));
		
		if( wxConfig.getId() == 0 ){
			wxConfig = this.wxConfigDao.save(wxConfig);
		}else{
			wxConfig = this.wxConfigDao.update(wxConfig);
		}
		
		return nbRet;
		
	}


	@Override
	public nbReturn getWxUserList(int start, int pageSize) {
		
		nbReturn nbRet = new nbReturn();
		
		QueryParam param = new QueryParam();
		List<ZaFrontUserWx> wxUsers = this.userWxDao.findByCriteria(param, start, pageSize);
		List<ZaFrontWxConfig> wxConfigs = this.wxConfigDao.findAll();
		int totalUserNumber = this.userWxDao.countByCriteria(param);
		Map<String, Object> retData = 
				DataWrapUtil.wrapDoGetWxUserList(wxUsers, wxConfigs, totalUserNumber);
		
		nbRet.setObject(retData);
		return nbRet;
		
	}

	

	@Transactional
	@Override
	public nbReturn updateOneEvent(Map<String, Object> jsonMap) {
		
		nbReturn nbRet = new nbReturn();
		
		Integer e_id = CommonHelper.getObjectInteger(jsonMap.get("id"));
		String deadLineDate = CommonHelper.getObjectString(jsonMap.get("deadLineDate"));
		String eventTitle = CommonHelper.getObjectString(jsonMap.get("eventTitle"));
		String eventDesc = CommonHelper.getObjectString(jsonMap.get("eventDesc"));
		String eventHeadImg = CommonHelper.getObjectString(jsonMap.get("eventHeadImg"));
		Date eventCreateDate = Calendar.getInstance().getTime(); 
		String eventDetailUrl = CommonHelper.getObjectString(jsonMap.get("eventDetailUrl"));
		Integer targetMoney = CommonHelper.getObjectInteger(jsonMap.get("targetMoney"));
		Integer targetMember = CommonHelper.getObjectInteger(jsonMap.get("targetMember"));
		String wxCardTitle = CommonHelper.getObjectString(jsonMap.get("wxCardTitle"));
		String wxCardImgUrl = CommonHelper.getObjectString(jsonMap.get("wxCardImgUrl"));
		String wxCardDesc = CommonHelper.getObjectString(jsonMap.get("wxCardDesc"));
		String eventTime = CommonHelper.getObjectString(jsonMap.get("eventTime"));
		String eventAddress = CommonHelper.getObjectString(jsonMap.get("eventAddress"));
		Integer wxConfigId = CommonHelper.getObjectInteger(jsonMap.get("wxConfigId")); 
		Integer eventCreaterWxId = CommonHelper.getObjectInteger(jsonMap.get("eventCreaterWxId"));
		Boolean isActive = CommonHelper.getObjectBoolean(jsonMap.get("isActive"), true);
		Integer cfEventType = CommonHelper.getObjectInteger(jsonMap.get("cfEventType"));
		Integer eventType = CommonHelper.getObjectInteger(jsonMap.get("eventType"));
		Integer eventSubType = CommonHelper.getObjectInteger(jsonMap.get("eventSubType"));
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rules = (List<Map<String, Object>>) jsonMap.get("rules");
		
		PCCoreEvent event = null;
		if( e_id != 0 )
			event = this.eventDao.find(e_id);
		if( event == null ){
			event = new PCCoreEvent();
			event.setEventStatus(eventType==2 ? 1 : 0 ); //0 ：不是众筹项目，肯定执行； 1：众筹中； 2：众筹成功； 3：众筹失败； 4：活动方取消
		}
		
		event.setEventDeadlineDate(nbStringUtil.String2DateTime(deadLineDate+":00"));
		event.setEventTitle(eventTitle);
		event.setEventShortDesc(eventDesc);
		event.setEventHeadImg(eventHeadImg);
		event.setEventCreateDate(eventCreateDate);
		event.setEventDetailUrl(eventDetailUrl);
		event.setTargetAmount(targetMoney);
		event.setTargetMember(targetMember);
		event.setWxCardTitle(wxCardTitle);
		event.setWxCardImgUrl(wxCardImgUrl);
		event.setWxCardDesc(wxCardDesc);
		event.setEventTime(nbStringUtil.String2DateTime(eventTime+":00"));
		event.setEventAddress(eventAddress);
		event.setWxConfigId(wxConfigId);
		event.setWxCreater(eventCreaterWxId == 0 ? this.userWxDao.find(1) : this.userWxDao.find(eventCreaterWxId));
		event.setIsEventActive(isActive);
		event.setCfEventType(cfEventType);
		event.setEventType(eventType);
		event.setEventSubType(eventSubType);
		//event.setIsEventSucceed(false);
		//event.setIsEventSample(false);
		event.setIsEventPassedReview(false);
		
		if( event.getId() == 0 ){
			event = this.eventDao.save(event);
		}else{
			event = this.eventDao.update(event);
		}
		
		for(Map<String, Object> aRule : rules){
			Integer r_id = CommonHelper.getObjectInteger(aRule.get("id"));
			Integer eventId = CommonHelper.getObjectInteger(aRule.get("eventId"));
			Integer unitPrice = CommonHelper.getObjectInteger(aRule.get("unitPrice"));
			String ruleDesc = CommonHelper.getObjectString(aRule.get("ruleDesc"));
			String ruleTitle = CommonHelper.getObjectString(aRule.get("ruleTitle"));
			Boolean isNeedAddress = CommonHelper.getObjectBoolean(aRule.get("isNeedAddress"), false);
			Boolean isNeedBarcode = CommonHelper.getObjectBoolean(aRule.get("isNeedBarcode"), false);
			Integer ruleMaxAmount = CommonHelper.getObjectInteger(aRule.get("ruleMaxAmount"));
			Integer ruleMinAmount = CommonHelper.getObjectInteger(aRule.get("ruleMinAmount"));
			Integer ruleMaxMember = CommonHelper.getObjectInteger(aRule.get("ruleMaxMember"));
			Integer ruleMinMember = CommonHelper.getObjectInteger(aRule.get("ruleMinMember"));
			Boolean isCanManyCopy = CommonHelper.getObjectBoolean(aRule.get("isCanManyCopy"), false);
			Boolean r_isActive = CommonHelper.getObjectBoolean(aRule.get("isActive"), false);
			Boolean isToDeleted = CommonHelper.getObjectBoolean(aRule.get("isToDeleted"), false);
			Boolean isCountInMember = CommonHelper.getObjectBoolean(aRule.get("isCountInMember"), false);
			Boolean isCountInAmount = CommonHelper.getObjectBoolean(aRule.get("isCountInAmount"), false);
			Boolean isAcceptableAfterDeadline = CommonHelper.getObjectBoolean(aRule.get("isAcceptableAfterDeadline"), false);
			
			PCCoreEventRule rule = null;
			if( r_id != 0 )
				rule = this.eventRuleDao.find(r_id);
			if( rule == null )
				rule = new PCCoreEventRule();
			rule.setEventId(eventId == 0 ? event.getId() : eventId);
			rule.setRuleUnitFee(unitPrice);
			rule.setRuleAwardLongDesc(ruleDesc);
			rule.setRuleShortDesc(ruleTitle);
			rule.setRuleIsNeedAddress(isNeedAddress);
			rule.setRuleIsNeedBarcode(isNeedBarcode);
			rule.setRuleMaxAmount(ruleMaxAmount == 0 ? event.getTargetAmount() : ruleMaxAmount);
			rule.setRuleMinAmount(ruleMinAmount);
			rule.setRuleMaxMember(ruleMaxMember == 0 ? event.getTargetMember() : ruleMaxMember);
			rule.setRuleMinMember(ruleMinMember);
			rule.setRuleIsCanManyCopy(isCanManyCopy);
			rule.setRuleIsActive(r_isActive);
			rule.setRuleIsDeleted(isToDeleted);
			rule.setIsCountInMember(isCountInMember);
			rule.setIsCountInAmount(isCountInAmount);
			rule.setIsAcceptableAfterDeadline(isAcceptableAfterDeadline);
			
			if( rule.getId() == 0 ){
				rule = this.eventRuleDao.save(rule);
			}else{
				rule = this.eventRuleDao.update(rule);
			}
		}
		
		nbRet = 
				this.getEventDetailInformation(event.getId());
		
		return nbRet;
	}
	
	
	@Transactional
	@Override
	public nbReturn getEventDetailInformation(int eventId){
		nbReturn nbRet = new nbReturn();
		
		PCCoreEvent event = this.eventDao.find(eventId);
		if( event == null)
		{
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("id", event.getId());
		ret.put("deadLineDate", nbStringUtil.DateTime2StrinF04(event.getEventDeadlineDate()));
		ret.put("eventTitle", event.getEventTitle());
		ret.put("eventDesc", event.getEventShortDesc());
		ret.put("eventHeadImg", event.getEventHeadImg());
		ret.put("eventCreateDate", nbStringUtil.DateTime2StrinF04(event.getEventCreateDate())); 
		ret.put("eventDetailUrl", event.getEventDetailUrl());
		ret.put("targetMoney", event.getTargetAmount());
		ret.put("targetMember", event.getTargetMember());
		ret.put("wxCardTitle", event.getWxCardTitle());
		ret.put("wxCardImgUrl", event.getWxCardImgUrl());
		ret.put("wxCardDesc", event.getWxCardDesc());
		ret.put("eventTime", nbStringUtil.DateTime2StrinF04(event.getEventTime()));
		ret.put("eventAddress", event.getEventAddress());
		ret.put("wxConfigId", event.getWxConfigId()); 
		ret.put("eventCreaterWxId", event.getWxCreater() == null ? null : event.getWxCreater().getId());
		ret.put("isActive", event.getIsEventActive());
		ret.put("cfEventType", event.getCfEventType());
		ret.put("eventType", event.getEventType());
		ret.put("eventSubType", event.getEventSubType());
		
		List<Map<String, Object>> rules = new ArrayList<Map<String, Object>>();
		List<PCCoreEventRule> rulesInDb = this.eventRuleDao.findByEventId(eventId);
		for( PCCoreEventRule ruleX : rulesInDb){
			if( ruleX.getRuleIsDeleted() )
				continue;
			Map<String , Object> aRule = new HashMap<String, Object>();
			aRule.put("id",ruleX.getId());
			aRule.put("eventId",ruleX.getEventId());
			aRule.put("unitPrice",ruleX.getRuleUnitFee());
			aRule.put("ruleDesc",ruleX.getRuleAwardLongDesc());
			aRule.put("ruleTitle",ruleX.getRuleShortDesc());
			aRule.put("isNeedAddress",ruleX.getRuleIsNeedAddress());
			aRule.put("isNeedBarcode",ruleX.getRuleIsNeedBarcode());
			aRule.put("ruleMaxAmount",ruleX.getRuleMaxAmount());
			aRule.put("ruleMinAmount",ruleX.getRuleMinAmount());
			aRule.put("ruleMaxMember",ruleX.getRuleMaxMember());
			aRule.put("ruleMinMember",ruleX.getRuleMinMember());
			aRule.put("isCanManyCopy",ruleX.getRuleIsCanManyCopy());
			aRule.put("isActive",ruleX.getRuleIsActive());
			aRule.put("isToDeleted",ruleX.getRuleIsDeleted());
			aRule.put("isCountInMember",ruleX.getIsCountInMember());
			aRule.put("isCountInAmount",ruleX.getIsCountInAmount());
			aRule.put("isAcceptableAfterDeadline",ruleX.getIsAcceptableAfterDeadline());
			rules.add(aRule);
		}
		
		ret.put("rules", rules);
		
		ZaFrontWxConfig wxCon = this.wxConfigDao.find(event.getWxConfigId());
		Map<String , Object> wxc = new HashMap<String, Object>();
		wxc.put("wxappid", wxCon.getWxappid());
		wxc.put("serverUrl", wxCon.getServerName());
		
		ret.put("wxConfig", wxc);
		
		
		nbRet.setObject(ret);
		
		return nbRet;
		
	}


	@Transactional
	@Override
	public nbReturn getEventList() {
		nbReturn nbRet = new nbReturn();
		List<PCCoreEvent> eventList = this.eventDao.findAll();
		List<Map<String,Object>> ret = new ArrayList<Map<String, Object>>();
		for(PCCoreEvent event : eventList){
			Map<String,Object> obj = new HashMap<String,Object>();
			obj.put("id", event.getId());
			obj.put("eventTitle", event.getEventTitle());
			obj.put("eventDesc", event.getEventShortDesc());
			obj.put("eventHeadImgUrl", event.getEventHeadImg());
			obj.put("eventWxConfigName", event.getZaFrontWxConfig().getConfigName());
			ret.add(obj);
		}
		nbRet.setObject(ret);
		return nbRet;
	}

}
