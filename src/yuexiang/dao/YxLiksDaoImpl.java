package yuexiang.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import nbBase.database.common.QueryParam;
import nbBase.database.models.ZaFrontUserWx;
import yuexiang.database.models.YuexiangComment;
import yuexiang.database.models.YuexiangLike;


@Transactional
@Repository("yxLikesDao")
public class YxLiksDaoImpl extends BaseDaoImpl<YuexiangLike> implements YxLikesDao{

	@Override
	public YuexiangLike findByCommentAndWxUser(YuexiangComment yxComment, ZaFrontUserWx wxUser) {
		
		QueryParam qp = new QueryParam();
		qp.addParam("yuexiangComment", yxComment);
		qp.addParam("wxUser", wxUser);
		List<YuexiangLike> retList = this.findByCriteria(qp);
		if( retList != null && retList.size() > 0){
			return retList.get(0);
		}
		return null;
	}

}
