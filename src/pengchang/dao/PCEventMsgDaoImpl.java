package pengchang.dao;


import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import pengchang.database.models.PCCoreEventMsg;


@Transactional
@Repository("eventMsgDao")
public class PCEventMsgDaoImpl extends BaseDaoImpl<PCCoreEventMsg> implements PCEventMsgDao{

	@Override
	public List<PCCoreEventMsg> findByWxConfigId(Integer wxConfigId, Integer startIndex, Integer pageSize) {
		
		if( startIndex == null)
			startIndex = 0;
		if( pageSize == null)
			pageSize = 120;
		
		String hql = "select a from ZaCoreEventMsg a where a.zaFrontWxConfig.id=:wxConfigId ORDER BY a.messageDate DESC";
		
		Query query = em.createQuery(hql);
		query.setParameter("wxConfigId", wxConfigId);
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		
		@SuppressWarnings({"unchecked" })
		List<PCCoreEventMsg> resultList = query.getResultList();
		
		return resultList;
	}

	@Override
	public List<PCCoreEventMsg> findByWxConfigIdAndOpenId(Integer wxConfigId, String openId) {
		String hql = "select a from ZaCoreEventMsg a where a.zaFrontWxConfig.id=:wxConfigId and a.zaFrontUserWx.wxOpenId=:openId ORDER BY a.messageDate DESC";
		
		Query query = em.createQuery(hql);
		query.setParameter("wxConfigId", wxConfigId);
		query.setParameter("openId", openId);
		
		@SuppressWarnings({"unchecked" })
		List<PCCoreEventMsg> resultList = query.getResultList();
		
		return resultList;
	}

	@Override
	public List<PCCoreEventMsg> findByEventId(Integer eventId, Integer startIndex, Integer pageSize, boolean isShowEmptyText) {
		
		if( startIndex == null)
			startIndex = 0;
		if( pageSize == null)
			pageSize = 120;
		System.out.println("isShowEmptyText"+isShowEmptyText);
		String hql = "select a from ZaCoreEventMsg a where a.zaCoreEvent.id=:eventId"
				+ (isShowEmptyText ? "" : " and a.message!=''")
				+ " ORDER BY a.messageDate DESC";
		
		Query query = em.createQuery(hql);
		query.setParameter("eventId", eventId);
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		
		@SuppressWarnings({"unchecked" })
		List<PCCoreEventMsg> resultList = query.getResultList();
		
		return resultList;
	}

	@Override
	public List<PCCoreEventMsg> findByEventIdAndOpenId(Integer eventId, String openId) {
		
		String hql = "select a from ZaCoreEventMsg a where a.zaCoreEvent.id=:eventId and a.zaFrontUserWx.wxOpenId=:openId ORDER BY a.messageDate DESC";
		
		Query query = em.createQuery(hql);
		query.setParameter("eventId", eventId);
		query.setParameter("openId", openId);
		
		@SuppressWarnings({"unchecked" })
		List<PCCoreEventMsg> resultList = query.getResultList();
		
		return resultList;
	}


}
