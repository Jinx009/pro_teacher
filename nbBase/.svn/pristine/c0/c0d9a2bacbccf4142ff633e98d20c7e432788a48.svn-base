package pengchang.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import pengchang.database.models.PCCoreEventMsgImg;


@Transactional
@Repository("eventMsgImgDao")
public class PCEventMsgImgDaoImpl extends BaseDaoImpl<PCCoreEventMsgImg> implements PCEventMsgImgDao{

	@Override
	public List<PCCoreEventMsgImg> findByEventId(Integer eventId, Integer startIndex, Integer pageSize) {
		
		if( startIndex == null)
			startIndex = 0;
		if( pageSize == null)
			pageSize = 120;
		if( eventId == null || eventId == 0)
			return new ArrayList<PCCoreEventMsgImg>();
		
		
		String hql = "select a from ZaCoreEventMsgImg a, ZaCoreEventMsg b where b.zaCoreEvent.id=:eventId and a.zaCoreEventMsg.id = b.id ORDER BY b.messageDate DESC";

		Query query = em.createQuery(hql);
		query.setParameter("eventId", eventId);
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		
		@SuppressWarnings({"unchecked" })
		List<PCCoreEventMsgImg> resultList = query.getResultList();

		return resultList;
		}

}
