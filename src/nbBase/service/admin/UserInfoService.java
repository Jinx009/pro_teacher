package nbBase.service.admin;

import nbBase.database.models.ZaAdminUser;
import nbBase.helper.common.nbReturn;

public interface UserInfoService {

	ZaAdminUser verifyUser(String username, String password)  throws Exception ;

	ZaAdminUser createNewUser(ZaAdminUser userInfo);

	nbReturn getUserMenu(ZaAdminUser user);

}
