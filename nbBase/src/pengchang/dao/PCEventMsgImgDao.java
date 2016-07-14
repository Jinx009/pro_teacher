package pengchang.dao;

import java.util.List;

import nbBase.database.common.BaseDao;
import pengchang.database.models.PCCoreEventMsgImg;

public interface PCEventMsgImgDao extends BaseDao<PCCoreEventMsgImg>{

	List<PCCoreEventMsgImg> findByEventId(Integer eventId, Integer startIndex, Integer pageSize);

	
}
