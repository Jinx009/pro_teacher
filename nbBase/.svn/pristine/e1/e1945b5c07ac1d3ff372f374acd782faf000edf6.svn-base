package yuexiang.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import nbBase.database.common.SimpleHQLStringBuilder;
import nbBase.database.models.ZaFrontUserWx;
import nbBase.helper.common.nbStringUtil;
import yuexiang.database.models.YuexiangComment;
import yuexiang.database.models.YuexiangTopic;


@Transactional
@Repository("yxCommentsDao")
public class YxCommentsDaoImpl extends BaseDaoImpl<YuexiangComment> implements YxCommentsDao{

	@Autowired
	YxCommentImageDao yxCommentImageDao;
	
	@Override
	public List<YuexiangComment> findByBookId(Integer bookId, Integer startIndex, Integer pageSize, boolean isFromWarehouse) {
		
		String hql = "select a from YuexiangComment a where a.book.id=:bookId";
		
		Query query = em.createQuery(hql);
		query.setParameter("bookId", bookId);
		query.setMaxResults(pageSize);
		query.setFirstResult(startIndex);
		
		@SuppressWarnings({"unchecked" })
		List<YuexiangComment> resultList = query.getResultList();
		
		return resultList;
	}

	@Override
	public List<YuexiangComment> findByMyCriteria01(YuexiangTopic topic, Integer startIndex, Integer pageSize,
			ZaFrontUserWx wxUser, Boolean isPublic) {
		
		if( isPublic ){
			return this.findPublicByCriteria(topic, startIndex, pageSize, null);
		}
		else{
			List<YuexiangComment> levelOneList = this.findPublicByCriteria(topic, null, null, wxUser);
			List<YuexiangComment> levelTwoList = this.findByCommentorInfo(wxUser);
			if( levelOneList.size() == 0  && levelTwoList.size() == 0)
				return new ArrayList<YuexiangComment>();
			SimpleHQLStringBuilder shsb = new SimpleHQLStringBuilder(); 
			shsb.setActionString("select distinct a from YuexiangComment a");
			String tmp = "";
			if( levelOneList.size() != 0 )
				tmp += " a in (:levelOneList)";
			if( levelTwoList.size() != 0 ){
				if( !nbStringUtil.isNullEmpty(tmp) )
					tmp += " or";
				tmp += " a in (:levelTwoList)";
			}
			
			shsb.addCondition("("+tmp+")");
			shsb.addCondition("a.isDeleted=false");
			if( topic != null ){
				shsb.addCondition("a.topic=:topic");
			}else{
				shsb.addCondition("a.topic is not null");
			}
			shsb.addOrder(SimpleHQLStringBuilder.OrderType.DESC, "a.id");
			
			String hql = shsb.getFullHqlString();
			Query query = em.createQuery(hql);
			if( levelOneList.size() != 0 )
				query.setParameter("levelOneList", levelOneList);
			if( levelTwoList.size() != 0 )
				query.setParameter("levelTwoList", levelTwoList);
			if( pageSize != null)
				query.setMaxResults(pageSize);
			if( startIndex != null)
				query.setFirstResult(startIndex);
			
			@SuppressWarnings({"unchecked" })
			List<YuexiangComment> resultList = query.getResultList();
			return resultList;
		}
	}

	private List<YuexiangComment> findPublicByCriteria(YuexiangTopic topic, Integer startIndex, Integer pageSize, ZaFrontUserWx wxUser){
		SimpleHQLStringBuilder shsb = new SimpleHQLStringBuilder(); 
		shsb.setActionString("select distinct a from YuexiangComment a");
		if( topic != null ){
			shsb.addCondition("a.topic=:topic");
		}else{
			shsb.addCondition("a.topic is not null");
		}
		if( wxUser != null){
			shsb.addCondition("a.createrUserWx=:wxUser");
		}
		
		shsb.addCondition("a.isDeleted=false");
		shsb.addOrder(SimpleHQLStringBuilder.OrderType.DESC, "a.id");
		
		String hql = shsb.getFullHqlString();
		Query query = em.createQuery(hql);
		if( topic != null)
			query.setParameter("topic", topic);
		if( wxUser != null){
			query.setParameter("wxUser", wxUser);
		}
		
		if( pageSize != null)
			query.setMaxResults(pageSize);
		if( startIndex != null)
			query.setFirstResult(startIndex);
		
		@SuppressWarnings({"unchecked" })
		List<YuexiangComment> resultList = query.getResultList();
		
		return resultList;
		
	}
	
	private List<YuexiangComment> findByCommentorInfo(ZaFrontUserWx wxCommentor){
		
		SimpleHQLStringBuilder shsb = new SimpleHQLStringBuilder(); 
		shsb.setActionString("select distinct b.parent from YuexiangComment b");
		shsb.addCondition("b.createrUserWx=:wxCommentor");
		shsb.addCondition("b.isDeleted=false");
		shsb.addOrder(SimpleHQLStringBuilder.OrderType.DESC, "b.id");
		
		String hql = shsb.getFullHqlString();
		Query query = em.createQuery(hql);
		query.setParameter("wxCommentor", wxCommentor);
		
		@SuppressWarnings({"unchecked" })
		List<YuexiangComment> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public int countGotLikes(ZaFrontUserWx wxUser) {
		if( wxUser == null )
			return 0;
		
//		String hqlkk= "select b,d from YuexiangBooks b "
//				   + "where "
//				   + "     (select a.book, a.topic, a.parent, a.id, a.isDeleted, a.createrUserWx, c.yuexiangComment, "
//				   + "     from YuexiangComment a join YuexiangLike c on a.id=c.yuexiangComment.id "
//				   + "     where a.isDeleted=false) d  "
//				   + "on b.id=d.book_id "
//				   + "where d.createrUserWx=:wxUser "
//				   + "and "
//				   + "(d.book is null or (b.isWarehouse=true and b.isVerbalOnly=true))";
		
		String hql = "select a "
				   + "     from YuexiangComment a "
				   + "     join a.yuexiangLikes c "
				   + "     where a.isDeleted=false and a.createrUserWx=:wxUser";
		
		Query query = em.createQuery(hql);
		query.setParameter("wxUser", wxUser);
		
		@SuppressWarnings({"unchecked" })
		List<YuexiangComment> resultList = query.getResultList();
		int count = 0;
		for( YuexiangComment comment:resultList){
			if( comment.getBook() != null && (comment.getBook().isVerbalOnly() || !comment.getBook().getIsWarehouse() )){
				continue;
			}
			count ++;
		}
		
		return count;
	}
}
