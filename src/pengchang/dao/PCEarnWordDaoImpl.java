package pengchang.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import pengchang.database.models.PCEarnWord;



@Repository("earnWordDao")
public class PCEarnWordDaoImpl extends BaseDaoImpl<PCEarnWord> implements PCEarnWordDao{

	@Override
	public int countAll() {
		String hql = "select count(a) from ZaEarnWord a";
		
		Query query = em.createQuery(hql);
		
		@SuppressWarnings({"rawtypes" })
		List resultList = query.getResultList();
		Integer resultListSize = Integer.valueOf(resultList.get(0).toString());
		
		return resultListSize;
	}

	@Override
	public List<PCEarnWord> findByIdArray(Integer[] indexArray) {
		
		List<Integer> indexA = new ArrayList();
		for(int i = 0 ; i < indexArray.length; i++){
			indexA.add(indexArray[i]);
		}
		
		String hql="select a from ZaEarnWord a where a.id in (:indexArray)";  
		Query query = em.createQuery(hql);  
		query.setParameter("indexArray", indexA);  
		
		@SuppressWarnings({"unchecked" })
		List<PCEarnWord> resultList = query.getResultList();
		
		return resultList;
		
	}

}
