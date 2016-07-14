package yuexiang.dao;



import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import yuexiang.database.models.YuexiangVerbalArticle;



@Transactional
@Repository("yxVerbalArticleDao")
public class YxVerbalArticleDaoImpl extends BaseDaoImpl<YuexiangVerbalArticle> implements YxVerbalArticleDao{

}
