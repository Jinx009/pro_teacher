package pengchang.dao;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import nbBase.database.models.ZaFrontUserWx;
import pengchang.database.models.PCCoreOrder;

@Transactional
@Repository("orderDao")
public class PCOrderDaoImpl extends BaseDaoImpl<PCCoreOrder> implements PCOrderDao{

	@Override
	public int findMemberCount(int eventId) {

		String hql = "select count(distinct a.wxUserId) from ZaCoreOrder a where a.eventId=:eventId and a.wxUserId is not null and a.isPaySucceed=true";
		
		Query query = em.createQuery(hql);
		query.setParameter("eventId", eventId);
		
		@SuppressWarnings({"rawtypes" })
		List resultList = query.getResultList();
		Integer resultListSize = Integer.valueOf(resultList.get(0).toString());
		
		System.out.println("eventId = "+eventId+" 有 "+resultListSize+" 位投资人");
		
		return resultListSize;
	}

	@Override
	public int findAmountCount(int eventId) {
		String hql = "select sum(a.totalFee) from ZaCoreOrder a where a.eventId=:eventId and a.isPaySucceed=true";
		
		Query query = em.createQuery(hql);
		query.setParameter("eventId", eventId);
		
		@SuppressWarnings({"rawtypes" })
		List resultList = query.getResultList();
		
		if(resultList == null || resultList.get(0) == null )
			return 0;
		
		Integer resultListSize = Integer.valueOf(resultList.get(0).toString());
		
		System.out.println("eventId = "+eventId+" 有 "+resultListSize+" 元投资金额");
		
		return resultListSize;
	}

	@Override
	public PCCoreOrder findByPrePayId(String prepayId) {
		
		String hql = "select a from ZaCoreOrder a, ZaPayWxUnionOrder b where a.payWxUnionOrderId=b.id and b.wxPrepayId=:prepayId";
		
		Query query = em.createQuery(hql);
		query.setParameter("prepayId", prepayId);
		
		@SuppressWarnings({"unchecked" })
		List<PCCoreOrder> resultList = query.getResultList();
		
		if(resultList == null || resultList.size() != 1 )
			return null;
		
		
		System.out.println("prepay Id = "+prepayId+" 将被前端页面设置成支付成功");
		
		return resultList.get(0);
	}

	@Override
	public PCCoreOrder findByOutTradeCode(String outTradeNo) {
		if( outTradeNo == null ){
			return null;
		}
		
		String hql = "select a from ZaCoreOrder a where a.mchOrderCode=:outTradeNo";
		
		Query query = em.createQuery(hql);
		query.setParameter("outTradeNo", outTradeNo);
		
		@SuppressWarnings({"unchecked" })
		List<PCCoreOrder> resultList = query.getResultList();
		
		if(resultList == null || resultList.size() == 0 )
			return null;
		
		return resultList.get(0);
		
	}

	@Override
	public List<PCCoreOrder> findPayedSupporterByEventId(Integer eventId, Integer startIndex, Integer pageSize) {
		
		String hql = "select a from ZaCoreOrder a where a.eventId=:eventId and a.isPaySucceed=true ORDER BY a.paySucceedTime DESC";
		
		Query query = em.createQuery(hql);
		query.setParameter("eventId", eventId);
		query.setFirstResult(startIndex);
		if( pageSize != null && pageSize != 0)
			query.setMaxResults(pageSize);
		
		@SuppressWarnings({"unchecked" })
		List<PCCoreOrder> resultList = query.getResultList();
		
		return resultList;
	}

	@Override
	public List<ZaFrontUserWx> findUniquePayedSupporterByEventId(int id) {
		
		String hql = "select distinct a.wxUserId from ZaCoreOrder a where a.eventId=:eventId and a.isPaySucceed=true";
		
		Query query = em.createQuery(hql);
		query.setParameter("eventId", id);
		
		@SuppressWarnings({"unchecked" })
		List<Integer> resultList = query.getResultList();
		
		hql = "select a from ZaFrontUserWx a where a.id in :resultList";
		
		query = em.createQuery(hql);
		query.setParameter("resultList", resultList);
		
		@SuppressWarnings({"unchecked" })
		List<ZaFrontUserWx> wxUserList = query.getResultList();
		
		return wxUserList;
	}

	@Override
	public List<PCCoreOrder> findByWxUserIdAndEventId(int wxUserId, int eventId) {
		
		String hql = "select a from ZaCoreOrder a where a.eventId=:eventId and a.wxUserId=:wxUserId";
		
		Query query = em.createQuery(hql);
		query.setParameter("eventId", eventId);
		query.setParameter("wxUserId", wxUserId);
		
		@SuppressWarnings({"unchecked" })
		List<PCCoreOrder> resultList = query.getResultList();
		
		return resultList;
	}

	@Override
	public Integer findValidMemberCount(int eventId) {
		if( eventId == 0 )
			return null;
		
		String hql = "select count(distinct a.wxUserId) from ZaCoreOrder a, ZaCoreEventRule c where a.eventId=:eventId and a.wxUserId is not null and a.isPaySucceed=true "
				+ "and a.eventRuleId=c.id and c.isCountInMember=true";
		
		Query query = em.createQuery(hql);
		query.setParameter("eventId", eventId);
		
		@SuppressWarnings({"rawtypes" })
		List resultList = query.getResultList();
		Integer resultListSize = Integer.valueOf(resultList.get(0).toString());
		
		System.out.println("eventId = "+eventId+" 有 "+resultListSize+" 位有效投资人");
		
		return resultListSize;
		
		
	}

	@Override
	public Integer findValidAmountCount(int eventId) {
		if( eventId == 0 )
			return null;
		String hql = "select sum(a.totalFee) from ZaCoreOrder a, ZaCoreEventRule c where a.eventId=:eventId and a.isPaySucceed=true "
				+ "and a.eventRuleId=c.id and c.isCountInAmount=true";
		
		Query query = em.createQuery(hql);
		query.setParameter("eventId", eventId);
		
		@SuppressWarnings({"rawtypes" })
		List resultList = query.getResultList();
		
		if(resultList == null || resultList.get(0) == null )
			return 0;
		
		Integer resultListSize = Integer.valueOf(resultList.get(0).toString());
		
		System.out.println("eventId = "+eventId+" 有 "+resultListSize+" 元有效投资金额");
		
		return resultListSize;
	}

	@Override
	public List<PCCoreOrder> findByOpenId(String openId) {
		
		if( openId == null )
			return null;
		
		String hql = "select a from ZaCoreOrder a where a.wxSupporter.wxOpenId=:openId and a.isPaySucceed=true ORDER BY a.paySucceedTime DESC";
		
		Query query = em.createQuery(hql);
		query.setParameter("openId", openId);
		
		@SuppressWarnings({"unchecked" })
		List<PCCoreOrder> resultList = query.getResultList();
		
		return resultList;
	}

	@Override
	public List<PCCoreOrder> findByWxUserIdAndEventRuleId(int wxUserId, int eventRuleId) {
		
		String hql = "select a from ZaCoreOrder a where a.eventRuleId=:eventRuleId and a.wxUserId=:wxUserId";
		
		Query query = em.createQuery(hql);
		query.setParameter("eventRuleId", eventRuleId);
		query.setParameter("wxUserId", wxUserId);
		
		@SuppressWarnings({"unchecked" })
		List<PCCoreOrder> resultList = query.getResultList();
		
		return resultList;
	}

	@Override
	public PCCoreOrder findEarnEventByOpenIdAndEventIdAndRuleId(String openId, Integer eventId, Integer ruleId, Integer orderDeliverStatus) {
		
		String hql = "select a from ZaCoreOrder a, ZaFrontUserWx b "
				+ "where a.wxUserId=b.id and b.wxOpenId=:openId and a.orderDeliverStatus=:orderDeliverStatus ";
		if( eventId != null)
			hql += "and a.eventId=:eventId ";
		if( ruleId != null)
			hql += "and a.eventRuleId=:ruleId ";
		
		Query query = em.createQuery(hql);
		
		if( ruleId != null)
			query.setParameter("ruleId", ruleId);
		
		query.setParameter("openId", openId);
		
		if( eventId != null)
			query.setParameter("eventId", eventId);
		
		query.setParameter("orderDeliverStatus", orderDeliverStatus);
		
		@SuppressWarnings({"unchecked" })
		List<PCCoreOrder> resultList = query.getResultList();
		if( resultList.size() <= 0)
			return null;
		
		return resultList.get(0);
	}

	@Override
	public List<PCCoreOrder> findPayedEarnOrderByEventId(int eventId, int maxpagesize) {
		if( eventId == 0)
			return null;
		
		String hql = "select a from ZaCoreOrder a where a.eventId=:eventId and a.isPaySucceed=true ORDER BY a.paySucceedTime DESC";
		
		Query query = em.createQuery(hql);
		query.setParameter("eventId", eventId);
		query.setMaxResults(maxpagesize);
		
		@SuppressWarnings({"unchecked" })
		List<PCCoreOrder> resultList = query.getResultList();
		
		
		return resultList;
	}

	@Override
	public Integer findAmountCount(Integer eventId, Integer ruleId) {
		String hql = "select sum(a.totalFee) from ZaCoreOrder a where a.eventId=:eventId and a.eventRuleId=:ruleId and a.isPaySucceed=true";
		
		Query query = em.createQuery(hql);
		query.setParameter("eventId", eventId);
		query.setParameter("ruleId", ruleId);
		
		@SuppressWarnings({"rawtypes" })
		List resultList = query.getResultList();
		
		if(resultList == null || resultList.get(0) == null )
			return 0;
		
		Integer resultListSize = Integer.valueOf(resultList.get(0).toString());
		
		System.out.println("eventId = "+eventId+" 有 "+resultListSize+" 元投资金额");
		
		return resultListSize;
	}

	@Override
	public Integer findMemberCount(Integer eventId, Integer ruleId) {
		
		String hql = "select count(distinct a.wxUserId) from ZaCoreOrder a where a.eventId=:eventId and a.eventRuleId=:ruleId and a.wxUserId is not null and a.isPaySucceed=true";
		
		Query query = em.createQuery(hql);
		query.setParameter("eventId", eventId);
		query.setParameter("ruleId", ruleId);
		
		@SuppressWarnings({"rawtypes" })
		List resultList = query.getResultList();
		Integer resultListSize = Integer.valueOf(resultList.get(0).toString());
		
		System.out.println("eventId = "+eventId+" 有 "+resultListSize+" 位投资人");
		
		return resultListSize;
		
	}

}
