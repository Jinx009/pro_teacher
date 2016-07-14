package pengchang.dao;

import java.util.List;

import nbBase.database.common.BaseDao;
import pengchang.database.models.PCEarnWordTryHistory;


public interface PCEarnWordTryHistoryDao extends BaseDao<PCEarnWordTryHistory>{

	Integer findAndDeactiveByOrderId(int orderId);

	List<PCEarnWordTryHistory> findByTryTimestamp(Long tryTimestamp);

	List<PCEarnWordTryHistory> findActivesByOrderId(Long orderId);

	boolean deleteByOrderId(int orderId);

	List<PCEarnWordTryHistory> findByOrderId(int orderId);

	
}
