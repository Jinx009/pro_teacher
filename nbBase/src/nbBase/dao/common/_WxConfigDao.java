package nbBase.dao.common;

import nbBase.database.common.BaseDao;
import nbBase.database.models.ZaFrontWxConfig;

public interface _WxConfigDao extends BaseDao<ZaFrontWxConfig>{

	ZaFrontWxConfig findByAppId(String appId);

	ZaFrontWxConfig findByEventId(
			Integer eventId);

	ZaFrontWxConfig findByDefault();
	
	ZaFrontWxConfig updateCurrentPageToken(int configId, String pageToken, Long pageTokenExpire);

	
}
