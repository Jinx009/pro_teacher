package pengchang.dao;

import java.util.List;

import nbBase.database.common.BaseDao;
import pengchang.database.models.PCCoreEventRule;

public interface PCEventRuleDao extends BaseDao<PCCoreEventRule>{

	List<PCCoreEventRule> findByEventId(Integer eventId);

	
}
