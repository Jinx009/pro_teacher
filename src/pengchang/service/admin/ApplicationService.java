package pengchang.service.admin;


import java.util.Map;

import nbBase.database.models.ZaAdminUser;
import nbBase.helper.common.nbReturn;

public interface ApplicationService {

	nbReturn getWxConfigList(ZaAdminUser adminUser);

	nbReturn getWxConfigDetail(Integer configId);

	nbReturn saveWxConfigure(Map<String, Object> jsonObject);

	nbReturn getWxUserList(int start, int pageSize);

	nbReturn updateOneEvent(Map<String, Object> jsonMap);

	nbReturn getEventDetailInformation(int eventId);

	nbReturn getEventList();

	
	
}
