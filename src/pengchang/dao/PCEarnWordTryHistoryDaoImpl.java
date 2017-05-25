package pengchang.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import pengchang.database.models.PCEarnWordTryHistory;



@Repository("earnWordTryHistoryDao")
public class PCEarnWordTryHistoryDaoImpl extends BaseDaoImpl<PCEarnWordTryHistory> implements PCEarnWordTryHistoryDao{

	@Override
	public Integer findAndDeactiveByOrderId(int orderId) {
		String hql = "update ZaEarnWordTryHistory a set a.isActive=false where a.order.id=:orderId";
		
		Query query = em.createQuery(hql);
		query.setParameter("orderId", orderId);
		
		return query.executeUpdate();
	}

	@Override
	public List<PCEarnWordTryHistory> findByTryTimestamp(Long tryTimestamp) {
		
		String hql="select a from ZaEarnWordTryHistory a where a.tryTimestamp=:tryTimestamp";  
		Query query = em.createQuery(hql);  
		query.setParameter("tryTimestamp", tryTimestamp);  
		
		@SuppressWarnings({"unchecked" })
		List<PCEarnWordTryHistory> resultList = query.getResultList();
		
		return resultList;
	}

	@Override
	public List<PCEarnWordTryHistory> findActivesByOrderId(Long orderId) {
		
		String hql="select a from ZaEarnWordTryHistory a where a.order.id=:orderId and a.isActive=true";  
		Query query = em.createQuery(hql);  
		query.setParameter("orderId", Integer.valueOf(orderId.intValue()));  
		
		@SuppressWarnings({"unchecked" })
		List<PCEarnWordTryHistory> resultList = query.getResultList();
		
		return resultList;
	}

	@Override
	public boolean deleteByOrderId(int orderId) {
		
		String hql="delete ZaEarnWordTryHistory a where a.order.id=:orderId";  
		Query query = em.createQuery(hql);  
		query.setParameter("orderId", Integer.valueOf(orderId));  
		int affactedRec = query.executeUpdate();
		
		return true;
	}

	@Override
	public List<PCEarnWordTryHistory> findByOrderId(int orderId) {

		String hql="select a from ZaEarnWordTryHistory a where a.order.id=:orderId";  
		Query query = em.createQuery(hql);  
		query.setParameter("orderId", orderId);  
		
		@SuppressWarnings({"unchecked" })
		List<PCEarnWordTryHistory> resultList = query.getResultList();
		
		return resultList;
	}

}
