package nbBase.dao;


import nbBase.database.common.BaseDao;
import nbBase.database.models.ZaWxPayCallback;

public interface ZaWxPayCallbackDao extends BaseDao<ZaWxPayCallback>{

	ZaWxPayCallback findByTransactionId(String transactionId);
	
}
