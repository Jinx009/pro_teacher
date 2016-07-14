package nbBase.dao;



import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import nbBase.database.models.ZaWxPayCallback;


@Transactional
@Repository("wxPayCallbackDao")
public class ZaWxPayCallbackDaoImpl extends BaseDaoImpl<ZaWxPayCallback> implements ZaWxPayCallbackDao{

	@Override
	public ZaWxPayCallback findByTransactionId(String transactionId) {
		if( transactionId == null )
			return null;
		
		String hql = "select a from ZaWxPayCallback a where a.transactionId=:transactionId";
		
		Query query = em.createQuery(hql);
		query.setParameter("transactionId", transactionId);
		
		@SuppressWarnings({"unchecked" })
		List<ZaWxPayCallback> resultList = query.getResultList();
		
		if( resultList != null && resultList.size() > 0){
			return resultList.get(0);
		}
		
		return null;
	}



}
