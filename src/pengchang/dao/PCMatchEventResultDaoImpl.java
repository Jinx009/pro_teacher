package pengchang.dao;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import nbBase.database.models.ZaFrontUserWx;
import pengchang.database.models.PCMatchEventResult;



@Transactional
@Repository("matchEventResultDao")
public class PCMatchEventResultDaoImpl extends BaseDaoImpl<PCMatchEventResult> implements PCMatchEventResultDao{

	@Override
	public List<PCMatchEventResult> findByEventId(Integer eventId,ZaFrontUserWx playerB, Boolean onlyNotConfirmed) {
		String hql = "select a from ZaMatchEventResult a where a.event.id=:eventId";
		
		if( playerB != null && onlyNotConfirmed){
			hql += " and a.playerB.id = :playerBId and a.playBConfirmed = false";
		}
		
		hql += " ORDER BY a.id DESC";
		
		Query query = em.createQuery(hql);
		query.setParameter("eventId", eventId);
		if( playerB != null && onlyNotConfirmed){
			query.setParameter("playerBId", playerB.getId());
		}
		
		
		@SuppressWarnings({"unchecked" })
		List<PCMatchEventResult> resultList = query.getResultList();
		
		return resultList;
	}

}
