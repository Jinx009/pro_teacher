package yuexiang.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import yuexiang.database.models.YuexiangTag;

@Transactional
@Repository("yxTagsDao")
public class YxTagsDaoImpl extends BaseDaoImpl<YuexiangTag> implements YxTagsDao{

	@Override
	public List<String> findCatalogList() {
		String hql = "select distinct a.catalog from YuexiangTag a";
		
		Query query = em.createQuery(hql);
		
		@SuppressWarnings({"unchecked" })
		List<String> resultList = query.getResultList();
		
		return resultList;
	}

	@Override
	public List<YuexiangTag> findByIds(List<Integer> tagFilterList) {
		if( tagFilterList == null )
			return null;
		
		String hql = "select a from YuexiangTag a where a.id in :tagList";
		
		Query query = em.createQuery(hql);
		query.setParameter("tagList", tagFilterList);
		
		@SuppressWarnings({"unchecked" })
		List<YuexiangTag> resultList = query.getResultList();
		
		return resultList;
	}

	@Override
	public List<YuexiangTag> findIn(List<YuexiangTag> tags) {
		List<Integer> ids = new ArrayList<Integer>();
		
		for( YuexiangTag tag : tags){
			ids.add(tag.getId());
		}
		
		return findInIds(ids);
	}

	@Override
	public List<YuexiangTag> findInIds(List<Integer> tags) {
		
		String hql = "select a from YuexiangTag a where a.id in :tags";
		
		Query query = em.createQuery(hql);
		query.setParameter("tags", tags);
		
		@SuppressWarnings({"unchecked" })
		List<YuexiangTag> resultList = query.getResultList();
		
		return resultList;
	}

	@Override
	public YuexiangTag findByName(String tagName) {
		String hql = "select a from YuexiangTag a where a.tag like :tags";
		
		Query query = em.createQuery(hql);
		query.setParameter("tags", tagName + "%");
		
		@SuppressWarnings({"unchecked" })
		List<YuexiangTag> resultList = query.getResultList();
		if( resultList != null && resultList.size() > 0)
			return resultList.get(0);
		return null;
	}

}
