package nbBase.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import nbBase.database.models.ZaSmsSend;


@Repository("smsSendDao")
public class ZaSmsSendDaoImpl extends BaseDaoImpl<ZaSmsSend> implements ZaSmsSendDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<ZaSmsSend> findByPhonenumberAndAppid(String phoneNumber, String appId, Long templateId) {
		
		String hql = "select a from ZaSmsSend a where a.targetPhoneNumber=:phoneNumber and a.applicationid=:appId and a.nbSmsTemplate.id = :templateId";
		Query query = em.createQuery(hql);
		query.setParameter("phoneNumber", phoneNumber);
		query.setParameter("appId", appId);
		query.setParameter("templateId", templateId.intValue());
		
		List<ZaSmsSend> resultList = query.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ZaSmsSend> findByIdAndCheckCode(int id, String phoneCheckCode) {
		
		String hql = "select a from ZaSmsSend a where a.id=:id and a.phoneCode=:phoneCheckCode";
		Query query = em.createQuery(hql);
		query.setParameter("phoneCheckCode", phoneCheckCode);
		query.setParameter("id", id);
		
		List<ZaSmsSend> resultList = query.getResultList();
		return resultList;
	}

}
