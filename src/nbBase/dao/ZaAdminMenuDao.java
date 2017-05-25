package nbBase.dao;

import java.util.List;

import nbBase.database.common.BaseDao;
import nbBase.database.models.ZaAdminMenu;
import nbBase.database.models.ZaAdminUser;


public interface ZaAdminMenuDao extends BaseDao<ZaAdminMenu>{

	List<ZaAdminMenu> findByUser(ZaAdminUser user);

	
}
