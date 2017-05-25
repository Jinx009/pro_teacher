package nbBase.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import nbBase.database.models.ZaUser;

@Repository("userDao")
public class ZaUserDaoImpl extends BaseDaoImpl<ZaUser> implements ZaUserDao{


	@SuppressWarnings("unchecked")
	@Override
	public ZaUser verifyUser(String username, String password, String appId) {
		
//		Transaction trans = session.beginTransaction();
//	    String sql = "update User user set user.age=20 where user.name='admin'";
//	    Query query = session.createQuery(hql);
		
		String hql = "select a from ZaUser a where a.username=:username and a.password=:password and a.applicationId=:appId";
		Query query = em.createQuery(hql);
		query.setParameter("username", username);
		query.setParameter("password", password);
		query.setParameter("appId", appId);
		
		List<ZaUser> resultList = query.getResultList();
		if( resultList != null ){
			if( resultList.size() > 0 )
				return resultList.get(0);
		}
		
		return null;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ZaUser findByUsernameAndAppid(String username, String appID) {
		
		String hql = "select a from ZaUser a where a.username=:username and a.applicationId=:appId";
		Query query = em.createQuery(hql);
		query.setParameter("username", username);
		query.setParameter("appId", appID);
		
		
		List<ZaUser> resultList = query.getResultList();
		if( resultList != null ){
			if( resultList.size() > 0 )
				return resultList.get(0);
		}
		return null;
	}

}
