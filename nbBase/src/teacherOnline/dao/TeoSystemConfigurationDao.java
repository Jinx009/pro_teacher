package teacherOnline.dao;

import nbBase.database.common.BaseDao;
import teacherOnline.database.models.TeoSystemConfiguration;

public interface TeoSystemConfigurationDao extends BaseDao<TeoSystemConfiguration>{

	public String getConfigByCode(int configCode);
}

