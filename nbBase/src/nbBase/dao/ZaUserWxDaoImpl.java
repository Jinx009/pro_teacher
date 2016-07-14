package nbBase.dao;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import nbBase.database.models.ZaFrontUserWx;

@Transactional
@Repository("userWxDao")
public class ZaUserWxDaoImpl extends BaseDaoImpl<ZaFrontUserWx> implements ZaUserWxDao{

	@Override
	public ZaFrontUserWx findByOpenId(String openid) {
		
		if(openid == null ){
			return null;
		}
		
		String hql = "select a from ZaFrontUserWx a where a.wxOpenId=:wxOpenId";
		
		Query query = em.createQuery(hql);
		query.setParameter("wxOpenId", openid);
		
		@SuppressWarnings("unchecked")
		List<ZaFrontUserWx> resultList = query.getResultList();
		
		if( resultList != null ){
			if( resultList.size() > 0 )
				return resultList.get(0);
		}
		
		return null;
	}

}
