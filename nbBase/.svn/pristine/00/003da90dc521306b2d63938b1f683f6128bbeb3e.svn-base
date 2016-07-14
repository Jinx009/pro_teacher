package pengchang.dao;

import java.util.List;

import nbBase.database.common.BaseDao;
import pengchang.database.models.PCCoreEvent;

public interface PCEventDao extends BaseDao<PCCoreEvent>{

	List<PCCoreEvent> find(
			Integer startIndex, 
			Integer pageSize, 
			Integer orderMode,
			String createrOpenId,
			List<Integer> targetedEventIds, 
			Integer eventType);

	Integer findAllCount(Integer eventType);

	List<PCCoreEvent> findByEventStatusCode(
			Integer statusCode,
			Integer eventType);

	
}
