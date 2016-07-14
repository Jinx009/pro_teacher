package pengchang.dao;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import pengchang.database.models.PCCoreEventRule;

@Transactional
@Repository("eventRuleDao")
public class PCEventRuleDaoImpl extends BaseDaoImpl<PCCoreEventRule> implements PCEventRuleDao{

	@Override
	public List<PCCoreEventRule> findByEventId(Integer eventId) {
		
		String hql = "select a from ZaCoreEventRule a where a.eventId=:eventId";

		Query query = em.createQuery(hql);
		query.setParameter("eventId", eventId);
		
		@SuppressWarnings({"unchecked" })
		List<PCCoreEventRule> resultList = query.getResultList();

		return resultList;
	}

}
