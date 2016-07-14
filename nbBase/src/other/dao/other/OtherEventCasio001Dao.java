package other.dao.other;


import nbBase.database.common.BaseDao;
import other.database.other.OtherEventCasio001;

public interface OtherEventCasio001Dao extends BaseDao<OtherEventCasio001>{

	OtherEventCasio001 findByPicUrl(String picCode);

	
}
