package yuexiang.dao;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import nbBase.database.common.SimpleHQLStringBuilder;
import nbBase.helper.common.CommonHelper;
import yuexiang.database.models.YuexiangBooksTag;

@Transactional
@Repository("yxBooksTagsDao")
public class YxBooksTagsDaoImpl extends BaseDaoImpl<YuexiangBooksTag> implements YxBooksTagsDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getBookIdByTagIdList(List<Integer> tagFilterList) {
		if(CommonHelper.isListNullOrEmpty(tagFilterList))
			return null;
		
		String hql = "select distinct a.bookId from YuexiangBooksTag a";
		SimpleHQLStringBuilder shsb = new SimpleHQLStringBuilder();
		shsb.addCondition("a.tagId in ("+tagFilterList.get(0)+")");
		//shsb.setActionString("select a.bookId from YuexiangBooksTag a");
		for(int i = 1 ; i < tagFilterList.size() ; i++){
			hql += ",YuexiangBooksTag "+ ("a"+i);
			shsb.addCondition("a.bookId="+("a"+i)+".bookId and "+("a"+i)+".tagId in ("+tagFilterList.get(i)+")");
		}
		shsb.setActionString(hql);
		hql = shsb.getFullHqlString();
		
		Query query = em.createQuery(hql);
		List<?> resultList = query.getResultList();
		
		return (List<Integer>)resultList;
	}


}
