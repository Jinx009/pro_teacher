package nbBase.dao;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import nbBase.database.models.ZaPayWxUnionOrder;

@Transactional
@Repository("wxPayUnionOrderDao")
public class ZaWxPayUnionOrderDaoImpl extends BaseDaoImpl<ZaPayWxUnionOrder> implements ZaWxPayUnionOrderDao{

}
