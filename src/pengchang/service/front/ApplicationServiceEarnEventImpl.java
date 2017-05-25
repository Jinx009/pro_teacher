package pengchang.service.front;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;

import nbBase.dao.ZaUserWxDao;
import nbBase.dao.ZaWxPayCallbackDao;
import nbBase.dao.ZaWxPayUnionOrderDao;
import nbBase.dao.common._SendAwardLogDao;
import nbBase.dao.common._WxConfigDao;
import nbBase.database.models.ZaFrontUserWx;
import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbReturn.ReturnCode;
import nbBase.service.common.If_ErrorCheckService;
import nbBase.service.common.WxCommonService;
import nbBase.service.wechat.WechatConfigure;
import nbBase.service.wechat._WechatKeyDefine;
import pengchang.dao.PCEarnWordDao;
import pengchang.dao.PCEarnWordTryHistoryDao;
import pengchang.dao.PCEventDao;
import pengchang.dao.PCEventMsgDao;
import pengchang.dao.PCEventMsgImgDao;
import pengchang.dao.PCEventRuleDao;
import pengchang.dao.PCOrderDao;
import pengchang.database.models.PCCoreEvent;
import pengchang.database.models.PCCoreEventRule;
import pengchang.database.models.PCCoreOrder;
import pengchang.database.models.PCEarnWord;
import pengchang.database.models.PCEarnWordTryHistory;

@Service("frontAppServiceEarnEvent")
public class ApplicationServiceEarnEventImpl implements ApplicationServiceEarnEvent, If_ErrorCheckService {
	
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
	PCEarnWordTryHistoryDao earnWordTryHistoryDao;
	
	@Autowired
	PCEarnWordDao earnWordDao;
	
	
	
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
	public nbReturn getEarnEventList(List<Integer> targetedEventIds) {
		nbReturn nbRet = new nbReturn();
		
		List<PCCoreEvent> events = 
				this.eventDao.find(
					null, 
					null, 
					null, 
					null, 
					targetedEventIds,
					_WechatKeyDefine.eventType.EARNEVENT);
		
		List<Map<String, Object>> eventList = 
				DataWrap.wrapDoGetEarnEvents(events);
		
		Map<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("eventList", eventList);
		
		List<Map<String, Object>> earnerList = new ArrayList<Map<String, Object>>();
		
		//这里暂时支持只有一个earnEvent的情况
		if( eventList.size() == 1){
			PCCoreEvent event = events.get(0);
			List<PCCoreOrder> orderList = this.orderDao.findPayedEarnOrderByEventId(event.getId(), 20);
			for( PCCoreOrder order : orderList ){
				Map<String, Object> earner = new HashMap<String, Object>();
				ZaFrontUserWx wxUser = this.userWxDao.find(order.getWxUserId());
				List<PCEarnWordTryHistory> ewthList = this.earnWordTryHistoryDao.findByOrderId(order.getId());
				earner.put("nickname", wxUser.getWxNickname());
				earner.put("headImgUrl", wxUser.getHeadImgUrl());
				
				//检查正确与否
				int correctNumber = 0;
				for( PCEarnWordTryHistory ewth : ewthList){
					if( ewth.getZaEarnWord().getChinese().equals(ewth.getUserAnswer()) ){
						correctNumber ++;
					}
				}

				earner.put("correctNumber", correctNumber);
				earner.put("award", Math.round(correctNumber*20));
				earnerList.add(earner);
			}
		}
		
		ret.put("earnerList", earnerList);
		
		nbRet.setObject(ret);
		
		return nbRet;
		
	}

	@Transactional
	@Override
	public nbReturn checkEarnStatusOfUser(Integer eventId, String openId) {
		nbReturn nbRet = new nbReturn();
		
		ZaFrontUserWx wxUser = this.userWxDao.findByOpenId(openId);
		PCCoreEvent event = this.eventDao.find(eventId);
		
		if( event == null || event.getEventType() != 3 ){ // 3是earnEvent的标志
			nbRet.setError(ReturnCode.REQUESTED_EVENT_OR_RULE_WRONG);
			return nbRet;
		}
		
		List<PCCoreEventRule> ruleList = event.getZaCoreEventRules();
		
		if( ruleList.size() <= 0 ){
			nbRet.setError(ReturnCode.REQUESTED_EVENT_OR_RULE_WRONG);
			return nbRet;
		}
		
		PCCoreEventRule rule = ruleList.get(0); //每个对赌项目都只能有一个支付规则
		
		List<PCCoreOrder> orderList = this.orderDao.findByWxUserIdAndEventRuleId( wxUser.getId(), rule.getId());
	
		PCCoreOrder order = null;
		List<PCEarnWordTryHistory> ewthList = null;
		
		for( PCCoreOrder orderIt : orderList){
			
			if( orderIt.getIsPaySucceed() && orderIt.getOrderDeliverStatus() == 1 ){
				
				order = orderIt;
				ewthList = 
						this.earnWordTryHistoryDao.findActivesByOrderId(Long.valueOf(order.getId()));
				break;
				
			}
			
		}
		
		if( order != null && order.getEarnPlayTimes() != null && order.getEarnPlayTimes() > 5){
			nbRet.setError(ReturnCode.TRY_ENOUGH);
			order.setOrderDeliverStatus(4);//算结束了
			order = this.orderDao.update(order);
		}
		
		Map<String, Object> ret = DataWrap.wrapDoCheckUserStatusOfThePlay(order, event, rule);
		
		int correctNumber = 0;
		if( ewthList != null){
			//检查正确与否
			for( PCEarnWordTryHistory ewth : ewthList){
				
				if( ewth.getZaEarnWord().getChinese().equals(ewth.getUserAnswer()) ){
					correctNumber ++;
				}
			}
			
			ret.put(_WechatKeyDefine.getEarnEvents.correctNumber, correctNumber);
			ret.put(_WechatKeyDefine.getEarnEvents.totalNumber, ewthList.size());
		}
		
		nbRet.setObject(ret);
		
		return nbRet;
	}

	@Transactional
	@Override
	public WordList generateRandomWordsList(int listLength, int orderId) {
		int totalWordSize = this.earnWordDao.countAll();
		
		WordList wordList = new WordList();
		wordList.tryTimestamp = Calendar.getInstance().getTime().getTime()*1000+orderId%1000;
		
		PCCoreOrder order = this.orderDao.find(orderId);		
		List<PCEarnWordTryHistory> ewthList = new ArrayList<PCEarnWordTryHistory>();
		
		for( int i = 0 ; i < listLength; i++){
			
			//先随机找到一个对象单词
			int index = CommonHelper.generateRandom(totalWordSize, 1, 0);
			while( wordList.has(index) ){
				index = CommonHelper.generateRandom(totalWordSize, 1, 0);;
			}
			
			//从词库里找出混淆用的单词
			Integer[] massIndex = new Integer[5];
			for( int j = 0 ; j < 4 ; j++){
				massIndex[j] = CommonHelper.generateRandom(totalWordSize, 1, index);;
				while( wordList.has(massIndex[j]) ){
					//如果混淆用的单词正好和对象单词一样，或和之前的对象单词一样，就重现找混淆用的单词
					massIndex[j] = CommonHelper.generateRandom(totalWordSize, 1, index);;
				}
 			}
			massIndex[4] = index; //把对象单词也加入到数组中，然后放到数据库里去一起取出来
			
			
			//这一共找出5个单词，其中4个是混淆用的
			List<PCEarnWord> wordsTmp = this.earnWordDao.findByIdArray(massIndex);
			PCEarnWord[] wordsArray = wordsTmp.toArray(new PCEarnWord[0]);
			for(int k = 0 ; k < wordsArray.length ; k ++){
				//通过产生随机数对换位置的方式，打乱顺序
				int target = CommonHelper.generateRandom(wordsArray.length-1, 0, k);
				PCEarnWord wordT = wordsArray[target];
				wordsArray[target] = wordsArray[k];
				wordsArray[k] = wordT;
			}
			
			OneWord oneWord = new OneWord(wordsArray, index);
			
				PCEarnWordTryHistory ewth = new PCEarnWordTryHistory();
				ewth.setIsActive(true);
				ewth.setIsAwarded(false);
				ewth.setTryTimestamp(wordList.tryTimestamp);
				ewth.setUserAnswer("");
				ewth.setZaCoreOrder(this.orderDao.find(orderId));
				ewth.setZaEarnWord(this.earnWordDao.find(index));
				ewthList.add(ewth);
				
			wordList.add(oneWord);
		}
		
		//到这里，所有的新建history已经在ewthList中了，需要保存到数据库，但是在保存之前需要把之前的全部作废掉
		//if( this.earnWordTryHistoryDao.findAndDeactiveByOrderId(orderId) != null){
		if( this.earnWordTryHistoryDao.deleteByOrderId(orderId) ){
			this.earnWordTryHistoryDao.save(ewthList);
		}
		if( order != null){
			Integer currentTimes = order.getEarnPlayTimes();
			if( currentTimes == null )
				currentTimes = 0;
			order.setEarnPlayTimes(currentTimes + 1);
		}
		
		return wordList;
	}
	

	@Override
	public void saveStatusToDB(WordList wordList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putWordsTextToDB() {
		
		try {
			FileReader fr = new FileReader("/Users/yuhangs/Downloads/gre词汇txt版.txt");
			
			BufferedReader br=new BufferedReader(fr);
	        String line="";
	        int lineNumber = 0;
	        String[] content = new String[2];
	        while ((line=br.readLine())!=null) {
	        	content[lineNumber%2] = line.trim();
	        	if( (lineNumber % 2) == 1){
	        		PCEarnWord ow = new PCEarnWord();
	        		ow.setEnglish(content[0]);
	        		ow.setChinese(content[1]);
	        		ow.setCorrectTimes(0);
	        		ow.setLoadTimes(0);
	        		this.earnWordDao.save(ow);
	        	}
	        	lineNumber ++;
	        }
	        br.close();
	        fr.close();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

	@Transactional
	@Override
	public nbReturn updateByTryTimestamp(Long tryTimestamp, JSONArray wordAnswerList) {
		nbReturn nbRet = new nbReturn();
		List<PCEarnWordTryHistory> ewthList = this.earnWordTryHistoryDao.findByTryTimestamp(tryTimestamp);
		
		for(Object object : wordAnswerList){
			
			@SuppressWarnings("unchecked")
			Map<String, Object> oneAnswer = (Map<String, Object>) object;
			
			Integer wordId = oneAnswer.get("wordId") == null ? 0 : Integer.valueOf(oneAnswer.get("wordId").toString());
			String answer = oneAnswer.get("answer") == null ? "" : oneAnswer.get("answer").toString();
			
			for( PCEarnWordTryHistory ewth : ewthList){
				if( ewth.getZaEarnWord().getId() == wordId ){
					ewth.setUserAnswer(answer);
				}
			}
		}
		
		this.earnWordTryHistoryDao.update(ewthList);
		
		if( nbRet.isSuccess()){
			return getTryResult(tryTimestamp, null);
		}
		
		return nbRet;
	}

	@Transactional
	@Override
	public nbReturn getTryResult(Long tryTimestamp, Long orderId) {
		nbReturn nbRet = new nbReturn();
		
		if( tryTimestamp == null && orderId == null){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		List<PCEarnWordTryHistory> ewthList;
		
		if(tryTimestamp != null ){
			ewthList = this.earnWordTryHistoryDao.findByTryTimestamp(tryTimestamp);
		}
		else{
			ewthList = this.earnWordTryHistoryDao.findActivesByOrderId(orderId);
		}
		
		//检查正确与否
		int correctNumber = 0;
		for( PCEarnWordTryHistory ewth : ewthList){
			
			if( ewth.getZaEarnWord().getChinese().equals(ewth.getUserAnswer()) ){
				correctNumber ++;
			}
		}
		
		Map<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("correctNumber", correctNumber);
		ret.put("totalNumber", ewthList.size());
		ret.put("tryTimestamp", ewthList.get(0).getTryTimestamp());
		ret.put("orderId", ewthList.get(0).getZaCoreOrder().getId());
		
		nbRet.setObject(ret);
		
		return nbRet;
		
	}

	@Transactional
	@Override
	public nbReturn sendAward(Integer eventId, Integer ruleId, Integer orderId, Long tryTimestamp, String openId,Map<String, Object> sessionTokenUserInfo, WechatConfigure wxCon) {
		int correctNumber = 0;
		List<PCEarnWordTryHistory> ewthList = null;
		PCCoreOrder order = null;
		
		nbReturn nbRet = new nbReturn();
		
		System.out.print("tryTimestamp : "+tryTimestamp);
		System.out.print("orderId : "+orderId);
		if( tryTimestamp != null ){ //优先使用tryTimestamp
			ewthList = 
					this.earnWordTryHistoryDao.findByTryTimestamp(tryTimestamp);
		}
		else{		
			if( orderId != null){ //然后看 orderId
				ewthList = this.earnWordTryHistoryDao.findActivesByOrderId(Long.valueOf(orderId));
			}
			else{ //最后使用openId + eventId + ruleId 的方式
				if( openId != null && (eventId != null || ruleId != null) ){
					order = this.orderDao.findEarnEventByOpenIdAndEventIdAndRuleId(openId, eventId, ruleId, 1); //null 交付状态不可用； 0 等待支付； 1 支付成功等待交付 ； 2 已经发货 ； 4 用户收到货物 并关闭订单； 6 用户收到货物并退货； 7 退货过程结束关闭订单；
				}
				if( order != null ){
					orderId = order.getId();
					ewthList = this.earnWordTryHistoryDao.findActivesByOrderId(Long.valueOf(orderId));
				}
			}
		}
		
		System.out.print("ewthList : "+ewthList.size());
		
		if( ewthList == null || ewthList.size() <= 0 ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		if( orderId == null)
			orderId = ewthList.get(0).getZaCoreOrder().getId();
		
		
		eventId = ewthList.get(0).getZaCoreOrder().getEventId();
		
		//检查正确的数字
		for( PCEarnWordTryHistory ewth : ewthList){
			if( ewth.getZaEarnWord().getChinese().equals(ewth.getUserAnswer()) ){
				correctNumber ++;
			}
		}
		
		if( correctNumber * 20 < 100 ){
			nbRet.setError(ReturnCode.REDPACK_TOO_SMALL);
			return nbRet;
		}
		
		try {
			nbRet = 
			this.wxCommonService.sendAward(
					"捧场", 
					"背单词获奖红包", 
					"再接再厉", 
					"127.0.0.1", 
					"最终答对 "+correctNumber+" 题", 
					openId, 
					correctNumber*20, //每题奖励0.2元 
					1, 
					sessionTokenUserInfo, 
					"EVENTID: "+eventId,
					wxCon);
		} catch (Exception e) {
			nbRet.setError(ReturnCode.GENERAL_ERROR);
			e.printStackTrace();
		}
		
		if( nbRet.isSuccess() ){
			
			order = this.orderDao.find(orderId);
			if( order != null){
				order.setOrderDeliverStatus(4);//null 交付状态不可用； 0 等待支付； 1 支付成功等待交付 ； 2 已经发货 ； 4 用户收到货物 并关闭订单； 6 用户收到货物并退货； 7 退货过程结束关闭订单； 
				this.orderDao.update(order);
			}
			
			for( PCEarnWordTryHistory ewth : ewthList ){
				ewth.setIsActive(false);
				ewth.setIsAwarded(true);
			}
			this.earnWordTryHistoryDao.update(ewthList);
			
		}
		
		return nbRet;
	}

	@Override
	public nbReturn checkUserStatusOfThePlayWithOrder(String openId, Integer orderId) {
		nbReturn nbRet = new nbReturn();
		
		PCCoreOrder order = 
		this.orderDao.find(orderId);
		
		
		if( order == null){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		if( order.getEarnPlayTimes() != null && order.getEarnPlayTimes() > 5){
			order.setOrderDeliverStatus(4);//这个订单算是结束了
			order = this.orderDao.update(order);
			nbRet.setError(ReturnCode.TRY_ENOUGH);
			return nbRet;
		}
		
		ZaFrontUserWx wxUser = this.userWxDao.findByOpenId(openId);
		
		if( wxUser == null){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		if( order.getWxUserId() != wxUser.getId() ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		if( order.getOrderDeliverStatus() != 1){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		return nbRet;
	}

}
