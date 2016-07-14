package pengchang.dao;

import java.util.List;

import nbBase.database.common.BaseDao;
import pengchang.database.models.PCCoreEventMsg;

public interface PCEventMsgDao extends BaseDao<PCCoreEventMsg>{

	List<PCCoreEventMsg> findByWxConfigId(Integer wxConfigId, Integer startIndex, Integer pageSize);

	List<PCCoreEventMsg> findByWxConfigIdAndOpenId(Integer wxConfigId, String openId);

	List<PCCoreEventMsg> findByEventId(Integer eventId, Integer startIndex, Integer pageSize, boolean isShowEmptyText);

	List<PCCoreEventMsg> findByEventIdAndOpenId(Integer eventId, String openId);

}
