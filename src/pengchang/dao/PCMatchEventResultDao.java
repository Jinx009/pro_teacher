package pengchang.dao;


import java.util.List;

import nbBase.database.common.BaseDao;
import nbBase.database.models.ZaFrontUserWx;
import pengchang.database.models.PCMatchEventResult;

public interface PCMatchEventResultDao extends BaseDao<PCMatchEventResult>{

	List<PCMatchEventResult> findByEventId(
			Integer eventId, ZaFrontUserWx playerB, Boolean onlyNotConfirmed);

	
}
