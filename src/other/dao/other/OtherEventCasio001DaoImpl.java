package other.dao.other;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import other.database.other.OtherEventCasio001;



@Transactional
@Repository("otherEventCasio001Dao")
public class OtherEventCasio001DaoImpl extends BaseDaoImpl<OtherEventCasio001> implements OtherEventCasio001Dao{

	@Override
	public OtherEventCasio001 findByPicUrl(String picCode) {
		
		
		String hql = "select a from OtherEventCasio001 a where a.picUrl=:picUrl";

		Query query = em.createQuery(hql);
		query.setParameter("picUrl", picCode);
		
		@SuppressWarnings({"unchecked" })
		List<OtherEventCasio001> resultList = query.getResultList();

		if( resultList.size() == 0 )
			return null;
		
		return resultList.get(0);
	}



}
