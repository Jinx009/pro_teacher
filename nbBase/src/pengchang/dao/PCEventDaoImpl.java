package pengchang.dao;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import nbBase.database.common.SimpleHQLStringBuilder;
import pengchang.database.models.PCCoreEvent;



@Transactional
@Repository("eventDao")
public class PCEventDaoImpl extends BaseDaoImpl<PCCoreEvent> implements PCEventDao{

	@Override
	public List<PCCoreEvent> find(
			Integer startIndex, 
			Integer pageSize, 
			Integer orderMode,
			String createrOpenId,
			List<Integer> targetedEventIds,
			Integer eventType) {
		
		if(startIndex == null){
			startIndex = 0;
		}
		if( pageSize == null){
			pageSize = 120;
		}
		
		SimpleHQLStringBuilder shsb = new SimpleHQLStringBuilder();
		shsb.setActionString("select a from ZaCoreEvent a");
		
		if(createrOpenId != null){
			if( createrOpenId.length() < 2) 
				 createrOpenId = null;
		}
		if( createrOpenId != null )
			shsb.addCondition("a.wxCreater.wxOpenId=:createrOpenId");
		
		/**
		 * displayMode : EDCBA  
		 * 				subType-A : 1 其他众筹 2：比赛众筹  
		 * 				eventType-B 0 :无效 1：普通支付 2：众筹项目 3：单人对赌项目
		 * 				isSample-C 0:不要显示sample 1：只显示sample 2：可显示sample
		 * 				isAcive-D 0:不显示无效的 1：只显示无效的 2：都显示
		 * 				eventStatus-E  0 ：所有（不是众筹项目，肯定执行）； 1：众筹中； 2：众筹成功； 3：众筹失败； 4：活动方取消
		 */
		if( orderMode == null ) orderMode = 0;
		int displayMode_subType = orderMode%10;
		int displayMode_eventType = (orderMode/10)%10;
		int displayMode_isSample = (orderMode/100)%10;
		int displayMode_isActive = (orderMode/1000)%10;
		int displayMode_eventStatus = (orderMode/10000)%10;
		
		if( displayMode_isActive == 0 )
			shsb.addCondition("a.isEventActive=true");// and a.isEventSample=false");
		if( displayMode_isActive == 1 )
			shsb.addCondition("a.isEventActive=false");
		
		if( displayMode_isSample == 0 )
			shsb.addCondition("a.isEventSample=false");
		if( displayMode_isSample == 1 )
			shsb.addCondition("a.isEventSample=true");
		if( displayMode_subType != 0 )
			shsb.addCondition("a.eventSubType=:subType");
		if( displayMode_eventType != 0 )
			eventType = displayMode_eventType;
		if( displayMode_eventStatus != 0 )
			shsb.addCondition("a.eventStatus=:eventStatus");
		
		if( eventType != null )
			shsb.addCondition("a.eventType=:eventType");
		
		if( targetedEventIds != null && targetedEventIds.size() > 0){
			shsb.addCondition("a.id in (:targetedEventIds)");
		}
		
		String hql = shsb.getFullHqlString() + " ORDER BY a.eventCreateDate DESC";
		System.out.println(hql);
		
		Query query = em.createQuery(hql);
		if( eventType != null ){
			query.setParameter("eventType", eventType);
		}
		if( createrOpenId != null ){
			query.setParameter("createrOpenId", createrOpenId);
		}
		if( targetedEventIds != null && targetedEventIds.size() > 0){
			query.setParameter("targetedEventIds", targetedEventIds);
		}
		if( displayMode_subType != 0 ){
			query.setParameter("subType", displayMode_subType);
		}
		if( displayMode_eventType != 0 ){
			query.setParameter("eventType", displayMode_eventType);
		}
		if( displayMode_eventStatus != 0 ){
			query.setParameter("eventStatus", displayMode_eventStatus);
		}
		
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		
		@SuppressWarnings({"unchecked" })
		List<PCCoreEvent> resultList = query.getResultList();
		
		return resultList;
	}

	
	@Override
	public Integer findAllCount(Integer eventType) {
		
		String hql = "select count(a) from ZaCoreEvent a";
		
		if( eventType != null ){
			hql += " where a.eventType=:eventType";
		}
		
		Query query = em.createQuery(hql);
		
		if( eventType != null ){
			query.setParameter("eventType", eventType);
		}
		
		@SuppressWarnings({"rawtypes" })
		List resultList = query.getResultList();
		Integer resultListSize = Integer.valueOf(resultList.get(0).toString());
		
		return resultListSize;
	}

	@Override
	public List<PCCoreEvent> findByEventStatusCode(Integer statusCode, Integer eventType) {
		
		SimpleHQLStringBuilder shsb = new SimpleHQLStringBuilder();
		shsb.setActionString("select a from ZaCoreEvent a");
		shsb.addCondition("a.eventStatus=:statusCode");
		
		if( eventType != null )
			shsb.addCondition("a.eventType=:eventType");
		
		String hql = shsb.getFullHqlString();
		
		Query query = em.createQuery(hql);
		if( eventType != null ){
			query.setParameter("eventType", eventType);
		}
		query.setParameter("statusCode", statusCode);
		
		
		@SuppressWarnings({"unchecked" })
		List<PCCoreEvent> resultList = query.getResultList();
		
		return resultList;
	}

}
