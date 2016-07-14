package pengchang.dao;

import java.util.List;

import nbBase.database.common.BaseDao;
import nbBase.database.models.ZaFrontUserWx;
import pengchang.database.models.PCCoreOrder;

public interface PCOrderDao extends BaseDao<PCCoreOrder>{

	int findMemberCount(int eventId);

	int findAmountCount(int eventId);

	PCCoreOrder findByPrePayId(String prepayId);

	PCCoreOrder findByOutTradeCode(String outTradeNo);

	List<PCCoreOrder> findPayedSupporterByEventId(
			Integer eventId, 
			Integer startIndex, 
			Integer pageSize);

	List<ZaFrontUserWx> findUniquePayedSupporterByEventId(int id);

	List<PCCoreOrder> findByWxUserIdAndEventId(int wxUserId, int eventId);

	Integer findValidAmountCount(int id);

	Integer findValidMemberCount(int id);

	List<PCCoreOrder> findByOpenId(
			String openId);

	List<PCCoreOrder> findByWxUserIdAndEventRuleId(int wxUserId, int eventRuleId);

	PCCoreOrder findEarnEventByOpenIdAndEventIdAndRuleId(String openId, Integer eventId, Integer ruleId, Integer orderDeliverStatus);

	List<PCCoreOrder> findPayedEarnOrderByEventId(int eventId, int maxpagesize);

	Integer findAmountCount(Integer eventId, Integer ruleId);

	Integer findMemberCount(Integer eventId, Integer ruleId);

	
}
