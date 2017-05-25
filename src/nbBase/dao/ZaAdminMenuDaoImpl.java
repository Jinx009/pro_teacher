package nbBase.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import nbBase.database.models.ZaAdminMenu;
import nbBase.database.models.ZaAdminUser;




@Repository("adminMenuDao")
public class ZaAdminMenuDaoImpl extends BaseDaoImpl<ZaAdminMenu> implements ZaAdminMenuDao{

	@Override
	public List<ZaAdminMenu> findByUser(ZaAdminUser user) {
		
		String hql="select a from ZaAdminMenu a ";//where a.id in (:indexArray)";  
		Query query = em.createQuery(hql);  
		//query.setParameter("userId", user.getId());  
		
		@SuppressWarnings({"unchecked" })
		List<ZaAdminMenu> resultList = query.getResultList();
		
		return resultList;
		
	}

}
