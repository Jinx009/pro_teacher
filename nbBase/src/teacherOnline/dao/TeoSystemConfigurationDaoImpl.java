package teacherOnline.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import teacherOnline.database.models.TeoSystemConfiguration;

@Repository("systemConfiguration")
public class TeoSystemConfigurationDaoImpl extends BaseDaoImpl<TeoSystemConfiguration> implements TeoSystemConfigurationDao {

	@SuppressWarnings("unchecked")
	@Override
	public String getConfigByCode(int configCode) {
		
		String hql = "select a from NbSystemConfiguration a where a.configCode=:configCode";
		Query query = em.createQuery(hql);
		query.setParameter("configCode", configCode);
		
		List<TeoSystemConfiguration> resultList = query.getResultList();
		if( resultList == null || resultList.size() == 0){
			return null;
		}
		return resultList.get(0).getConfigValue();
	}

	
}
