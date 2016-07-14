package yuexiang.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import nbBase.database.common.SimpleHQLStringBuilder;
import nbBase.helper.common.CommonHelper;
import yuexiang.database.models.YuexiangBook;


@Transactional
@Repository("yxBookDao")
public class YxBookDaoImpl extends BaseDaoImpl<YuexiangBook> implements YxBookDao{

	@Autowired
	YxBooksTagsDao yxBooksTagsDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public Object findByCertainConditions(
			Integer startIndex, 
			Integer pageSize,
			List<Integer> tagFilterList, 
			String searchKeyWord, 
			Boolean isWarehouse,
			Boolean isVerbalOnly,
			Boolean queryCount
			) {
		
		SimpleHQLStringBuilder shsb = new SimpleHQLStringBuilder();
		try {
			if( queryCount){
				shsb.setActionString("select count(a) from YuexiangBook a");
			}else{
				shsb.setActionString("select a from YuexiangBook a");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Integer> bookIdList = null;
		if( !CommonHelper.isListNullOrEmpty(tagFilterList) ){
			bookIdList = yxBooksTagsDao.getBookIdByTagIdList(tagFilterList);
			if( bookIdList.isEmpty() ){ //tag就把所有的可能性都过滤了
				if( queryCount )
					return 0l;
				else
					return new ArrayList<YuexiangBook>();
			}
		}
		if( bookIdList != null ){
			shsb.addCondition("a.id in :bookIdList");
		}
//		if( isVerbalOnly != null){
//			//shsb.addCondition("a.isVerbalOnly=:isVerbalOnly");
//			shsb.addCondition("a.verbalUrl is not null");
//		}
		if( searchKeyWord != null ){
			shsb.addCondition("(a.bookName like :searchKeyWord or a.bookPublisher like :searchKeyWord or a.bookWriter like :searchKeyWord)");
		}
		
		if( isWarehouse != null ){
			shsb.addCondition("a.isWarehouse=:isWarehouse");
		}
		if( isVerbalOnly != null ){
			shsb.addCondition("a.isVerbalOnly=:isVerbalOnly");
		}
		
		shsb.addOrder(SimpleHQLStringBuilder.OrderType.DESC, "a.id");
		String hql = shsb.getFullHqlString();
		
		
		Query query = em.createQuery(hql);
		if( startIndex != null && !queryCount )
			query.setFirstResult(startIndex);
		if( pageSize != null && !queryCount )
			query.setMaxResults(pageSize);
		if( bookIdList != null){
			query.setParameter("bookIdList", bookIdList);
		}
		if( searchKeyWord != null )
			query.setParameter("searchKeyWord", "%"+searchKeyWord+"%");
		if( isWarehouse != null ){
			query.setParameter("isWarehouse", isWarehouse);
		}
		if( isVerbalOnly != null){
			query.setParameter("isVerbalOnly", isVerbalOnly);
		}
			
		
		
		List<?> resultList = query.getResultList();
		if( queryCount ){
			return resultList.get(0);
		}
		
		return (List<YuexiangBook>) resultList;
	}

}
