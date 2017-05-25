package nbBase.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nbBase.dao.ZaAdminMenuDao;
import nbBase.dao.ZaAdminUserDao;
import nbBase.dao.common._WxConfigDao;
import nbBase.database.models.ZaAdminMenu;
import nbBase.database.models.ZaAdminUser;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbReturn.ReturnCode;
import nbBase.helper.common.nbStringUtil;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService{
	
	@Autowired
	ZaAdminUserDao adminUserDao;
	
	@Autowired
	ZaAdminMenuDao adminMenuDao;
	
	@Autowired
	 _WxConfigDao wxConfigDao;
	

	@Override
	public ZaAdminUser verifyUser(String username, String password) throws Exception {
		password = nbStringUtil.encryptMD5(password);
		ZaAdminUser zaUser = adminUserDao.find(username, password);
		return zaUser;
	}

	@Override
	public ZaAdminUser createNewUser(ZaAdminUser userInfo) {
		if( adminUserDao.findByUsername(userInfo.getUsername()) == null ){
			return adminUserDao.save(userInfo);
		}
		return null;
	}

	@Override
	public nbReturn getUserMenu(ZaAdminUser user) {
		
		nbReturn nbRet = new nbReturn();
		
		List<ZaAdminMenu> adminMenuList = 
				this.adminMenuDao.findByUser(user);
		
		if( adminMenuList == null){
			nbRet.setError(ReturnCode.SESSION_LOST);
			return nbRet;
		}
		
		nbRet.setObject(makeMenuData(adminMenuList, 0));
		
		return nbRet;
		
	}
	
	private List<Map<String,Object>> makeMenuData(List<ZaAdminMenu> menuList, int parent){
		
		List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
		
		for( ZaAdminMenu menu : menuList){
			if( menu.getMenuParent() == parent ){
				Map<String, Object> menuItem = new HashMap<String, Object>();
				menuItem.put("id", menu.getId());
				menuItem.put("icon", menu.getMenuIcon());
				menuItem.put("text", menu.getMenuName());
				menuItem.put("url", menu.getMenuUrl());
				menuItem.put("code", menu.getMenuCode());
				menuItem.put("child", makeMenuData(menuList, menu.getId())); //递归查找
				ret.add(menuItem);
			}
		}
		
		return ret;
	}

	
}
