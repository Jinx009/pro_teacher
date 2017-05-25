package pengchang.service.front;

import java.util.List;
import java.util.Map;

import nbBase.helper.common.nbReturn;
import nbBase.service.wechat.WechatConfigure;

public interface ApplicationService {

	/**
	 * 创建一个统一订单
	 * @param orderBody
	 * @param orderDetail
	 * @param attach
	 * @param totalFee
	 * @param clientIp
	 * @param eventId
	 * @param eventRuleId
	 * @param copies
	 * @param userName
	 * @param userPhone
	 * @param userAddress
	 * @param wxCon
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	nbReturn createUnionOrder(
			String orderBody, 
			String orderDetail, 
			String attach, 
			Integer totalFee, 
			String clientIp, 
			Integer eventId, 
			Integer eventRuleId, 
			Integer copies,
			String userName,
			String userPhone,
			String userAddress,
			WechatConfigure wxCon,
			Map<String, Object> userInfo) throws Exception;

	
	/**
	 * 获取活动列表
	 * @param startIndex
	 * @param pageSize
	 * @param orderMode
	 * @param recomEventId
	 * @param createrOpendId
	 * @param wxCon
	 * @return
	 */
	nbReturn getEventList(
			Integer startIndex, 
			Integer pageSize, 
			Integer orderMode, 
			Integer recomEventId,
			String createrOpendId,
			WechatConfigure wxCon
			);

	/**
	 * 都得某个event
	 * @param eventId
	 * @param wxCon
	 * @return
	 */
	@Deprecated
	nbReturn getEvent(
			Integer eventId, 
			WechatConfigure wxCon);

	/**
	 * 返回wrap过的map内容
	 * @param eventId
	 * @param eventRuleId
	 * @param wxCon
	 * @return
	 */
	nbReturn getEventDetail(
			Integer eventId, 
			Integer eventRuleId, 
			WechatConfigure wxCon);
	

	/**
	 * 获取某个创建者名下的某个活动的细节，数据库穿透获取
	 * @param eventId
	 * @param eventRuleId
	 * @param openId creater wx openid
	 * @return
	 */
	nbReturn getEventDetailByWxCreater(
			Integer eventId, 
			Integer ruleId,
			String openId
			);

	/**
	 * 设置前端支付成功
	 * @param prepayId
	 * @param wxCon
	 * @return
	 */
	nbReturn setFrontPaySucceed(
			String prepayId, 
			WechatConfigure wxCon);

	/**
	 * 设置支付的后台callback结果
	 * @param param
	 * @return
	 */
	Map<String, Object> savePayCallback(Map<String, Object> param);

	/**
	 * 获取order的详情
	 * @param orderId
	 * @param openId
	 * @return
	 */
	nbReturn getOrderDetail(Integer orderId, String openId);

	
	/**
	 * 获取event的状态
	 * @param eventId
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	nbReturn getEventStatus(
			Integer eventId, 
			Integer startIndex, 
			Integer pageSize);

	/**
	 * 获取event的留言板
	 * @param eventId
	 * @param sessionTokenUserInfo
	 * @param wxConfigId
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	nbReturn getEventMsg(
			Integer eventId, 
			Map<String, Object> sessionTokenUserInfo,
			Integer wxConfigId,
			Integer startIndex,
			Integer pageSize);

	/**
	 * 保存留言
	 * @param eventId
	 * @param sessionTokenUserInfo
	 * @param wxAppId
	 * @param msgText
	 * @param imgList
	 * @return
	 */
	nbReturn saveEventMsg(
			Integer eventId, 
			Map<String, Object> sessionTokenUserInfo, 
			String wxAppId, 
			String msgText,
			List<Map<String, Object>> imgList);

	/**
	 * 扫描众筹项目并更新状态
	 * @return
	 */
	nbReturn scanAndUpdateEvents();


	/**
	 * 创建或修改某个event
	 * @param jsonMap
	 * @param wxCon
	 * @param openid
	 * @return
	 */
	nbReturn updateEvents(
			Map<String, Object> jsonMap,
			WechatConfigure wxCon,
			String openid);


	/**
	 * 获取我捧场过的项目列表
	 * @param openId
	 * @return
	 */
	nbReturn getMyPengList(
			String openId);


	nbReturn getEventInfo(
			Integer eventId, 
			WechatConfigure wxCon,
			String currentUserOpenId,
			boolean isShowEmptyText);


	nbReturn getMatchEventResult(
			Integer eventId, 
			String openId, 
			WechatConfigure wxCon,
			Boolean onlyNotConfirmed);


	nbReturn saveMatchEventResult(Integer eventId, String openId, Integer rivalId, Integer result,
			WechatConfigure wxCon);


	/**
	 * 确认一个比赛结果
	 * @param eventId	活动ID
	 * @param openId	确认者的openID
	 * @param resultId	确认的动作ID
	 * @param wxCon		WechatConfigure
	 * @return
	 */
	nbReturn confirmMatchEventResult(
			Integer eventId, 
			String openId,
			Integer resultId,
			WechatConfigure wxCon);


	/**
	 * 检查是否支付窗口还开方，是否任然可以报名
	 * @param objectInteger
	 * @param objectInteger2
	 * @return
	 */
	nbReturn checkIfPayWindowOpen(
			Integer eventId, 
			Integer ruleId);
	
	public nbReturn loadWechatConfig(String wxUserOpenId, Integer eventId, boolean isLoadDefaultOnFalt);


	int getEventAmountCount(Integer eventId);
	
}
