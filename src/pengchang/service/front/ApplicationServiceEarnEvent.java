package pengchang.service.front;


import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;

import nbBase.helper.common.nbReturn;
import nbBase.service.wechat.WechatConfigure;


public interface ApplicationServiceEarnEvent {


	/**
	 * 获取对赌项目列表
	 * @return
	 */
	nbReturn getEarnEventList(List<Integer> targetedEventIds);


	/**
	 * 获取某个用户在某个对赌活动中的状态
	 * @param eventId
	 * @param openId
	 * @return
	 */
	nbReturn checkEarnStatusOfUser(
			Integer eventId, 
			String openId);
	
	
	WordList generateRandomWordsList(
			int listLength,
			int orderId);
	
	void saveStatusToDB(
			WordList wordList);
	
	void putWordsTextToDB();

	nbReturn updateByTryTimestamp(
			Long tryTimestamp, JSONArray wordAnswerList);
	
	nbReturn getTryResult(Long tryTimestamp, Long orderId);


	nbReturn sendAward(Integer eventId, Integer ruleId, Integer orderId, Long tryTimestamp, String openId, Map<String, Object> sessionTokenUserInfo, WechatConfigure wxCon);


	nbReturn checkUserStatusOfThePlayWithOrder(String openId, Integer orderId);
	
}
