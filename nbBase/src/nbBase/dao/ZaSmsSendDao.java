package nbBase.dao;

import java.util.List;

import nbBase.database.common.BaseDao;
import nbBase.database.models.ZaSmsSend;

public interface ZaSmsSendDao extends BaseDao<ZaSmsSend>{

	List<ZaSmsSend> findByPhonenumberAndAppid(String phoneNumber,String appId, Long templateId);

	List<ZaSmsSend> findByIdAndCheckCode(int id, String phoneCheckCode);
	

	
}
