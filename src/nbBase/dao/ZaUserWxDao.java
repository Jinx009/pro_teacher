package nbBase.dao;

import nbBase.database.common.BaseDao;
import nbBase.database.models.ZaFrontUserWx;

public interface ZaUserWxDao extends BaseDao<ZaFrontUserWx>{

	ZaFrontUserWx findByOpenId(String openid);

	
}
