package nbBase.main.entry.webapp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import nbBase.database.models.ZaAdminUser;
import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbStringUtil;
import nbBase.service.admin.UserInfoService;

/**
 * 这里是定义后台admin入口html的地方，前端功能用的入口html请移步WebAppMain.java
 * @author yuhangs
 *
 */

@Controller
public class WebAppAdmin {

	 @Autowired  
	 private UserInfoService userInfoService;
	 
	 String subProjectPath = null;
	 
	 public WebAppAdmin(){
		 subProjectPath = CommonHelper.loadAppSpecifiedConfig("subAppRootPath");
		 if( nbStringUtil.isNullEmpty(subProjectPath))
			 subProjectPath = "/pengchang";
		 else
			 if( !subProjectPath.startsWith("/") ){
				 subProjectPath = "/" + subProjectPath;
			 }
	 }
	 
	 
	/**
	 * 登录页面入口
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/index") 
	public ModelAndView index(HttpServletResponse response,HttpServletRequest request) throws Exception{  
	    //创建模型跟视图，用于渲染页面。并且指定要返回的页面为home页面  
		nbReturn nbRet = new nbReturn();
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("serverUrl", CommonHelper.getFullPathOfCurrentRequest(request));
		
	    data.put("ret","nbReturn");
	    data.put("successToUrl", "/admin/home.html");
	    data.put("serverPWD",CommonHelper.getFullPathOfCurrentRequest(request));
	    ModelAndView mav = new ModelAndView(subProjectPath+"/admin/index",data);
	    
//	    CommonHelper.mergeImgFiles("/usr/local/www/pc-res.0angle.com/2.pic(5).jpg", "/usr/local/www/pc-res.0angle.com/shen.1.jpg");
	    
	    
	    return mav;  
	}
	
	/**
	 * 登录操作action; todo: 需要考虑移到restful(main.entry.webapp.REST.UserInfoAndSessionMrg.java)
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/loginAction") 
	public ModelAndView login(HttpServletResponse response,HttpServletRequest request) throws Exception{  
	    //创建模型跟视图，用于渲染页面。并且指定要返回的页面为home页面  
		nbReturn nbRet = new nbReturn();
		Map<String,Object> data = new HashMap<String,Object>();
		Map<String, String[]> params = request.getParameterMap();
		data.put("serverUrl", CommonHelper.getFullPathOfCurrentRequest(request));
		String toUrl = "";
		String username = params.get("inputUsername")[0];
		String password = params.get("inputPassword")[0];
		
		ZaAdminUser userInfo =  userInfoService.verifyUser(username, password) ;
		String retmessage = "成功！";
		if( userInfo == null){
			retmessage = "用户名密码错误";
		}else{
			toUrl = params.get("toUrl")[0];
			HttpSession session = request.getSession();
			session.setAttribute("currentUserInfo", userInfo);
		}
		
		
		data.put("retmessage", retmessage);
		data.put("toUrl", toUrl);
		
		data.put("serverPWD",CommonHelper.getFullPathOfCurrentRequest(request));
	    ModelAndView mav = new ModelAndView("retPage",data);
	    return mav;  
	}
	
	/**
	 * 后台admin注册页面的入口
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/signup") 
    public ModelAndView signup(HttpServletResponse response,HttpServletRequest request) throws Exception{  
        //创建模型跟视图，用于渲染页面。并且指定要返回的页面为home页面  
		nbReturn nbRet = new nbReturn();

    	Map<String,Object> data = new HashMap<String,Object>();
    	Map<String, String[]> params = request.getParameterMap();
    	data.put("toUrl", "index.html");
    	data.put("serverUrl", CommonHelper.getFullPathOfCurrentRequest(request));
    	
    	data.put("serverPWD",CommonHelper.getFullPathOfCurrentRequest(request));
        ModelAndView mav = new ModelAndView(subProjectPath+"/admin/signup",data);
        return mav;  
	}
	
	/**
	 * 后台admin注册操作action；todo: 需要考虑移到restful(main.entry.webapp.REST.UserInfoAndSessionMrg.java)
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/signupAction")
    public ModelAndView signupAction(HttpServletResponse response,HttpServletRequest request) throws Exception{  
        //创建模型跟视图，用于渲染页面。并且指定要返回的页面为home页面  
		nbReturn nbRet = new nbReturn();
    	Map<String,Object> data = new HashMap<String,Object>();
    	Map<String, String[]> params = request.getParameterMap();
    	data.put("serverUrl", CommonHelper.getFullPathOfCurrentRequest(request));
    	
    	String toUrl = "";//params.get("toUrl")[0];
    	String username = params.get("username")[0];
    	String password = params.get("password")[0];
    	String password2 = params.get("password2")[0];
    	String realname = params.get("realname")[0];

    	
    	
    	String retmessage ="成功";
    
    	if( password2.equals(password) ){
    		
    		ZaAdminUser userInfo = new ZaAdminUser();
    		userInfo.setUsername(username);
    		userInfo.setRealname(realname);
    		userInfo.setPassword(nbStringUtil.encryptMD5(password));
    		
    		userInfo = userInfoService.createNewUser(userInfo) ;
    		
    		if( userInfo == null ){
    			retmessage = "用户已经存在！";
    		}else{
    			toUrl = params.get("toUrl")[0];
    		}
  
    	}else{
    		retmessage = "两次密码输入不同！";
    	}
    	
    	data.put("retmessage", retmessage);
    	data.put("toUrl", toUrl);
    	
        data.put("ret","nbReturn");
        data.put("serverPWD",CommonHelper.getFullPathOfCurrentRequest(request));
        ModelAndView mav = new ModelAndView("retPage",data);
        return mav;  
    }
	 
	/**
	 * 授权进入后的后台主页面入口
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/admin/home")
	 public ModelAndView main_home(HttpServletResponse response,HttpServletRequest request) throws Exception{  
		//创建模型跟视图，用于渲染页面。并且指定要返回的页面为home页面  
		nbReturn nbRet = new nbReturn();
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("serverUrl", CommonHelper.getFullPathOfCurrentRequest(request));
		HttpSession session = request.getSession();
		ZaAdminUser user = (ZaAdminUser) session.getAttribute("currentUserInfo");
		Map<String, String[]> params = request.getParameterMap();
		    	
		 
		data.put("ret","nbReturn");
		data.put("projectList", null);
		data.put("realname", user.getRealname() );
		data.put("userId", user.getId());
		data.put("serverPWD",CommonHelper.getFullPathOfCurrentRequest(request));
		ModelAndView mav = new ModelAndView(subProjectPath+"/admin/home",data);
		return mav;
	}
	 
	 

	/**
	 * admin的统一入口
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/{functionName}") 
	public ModelAndView wechatSendAward(HttpServletResponse response,HttpServletRequest request, @PathVariable String functionName) throws Exception{  
	    //创建模型跟视图，用于渲染页面。并且指定要返回的页面为home页面  
		nbReturn nbRet = new nbReturn();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("serverUrl", CommonHelper.getFullPathOfCurrentRequest(request));
		data.put("successToUrl", subProjectPath+"/admin/home.html");
		String modelViewPageName = subProjectPath+"/admin/"+functionName;

		
	    data.put("ret","nbReturn");
	    data.put("serverPWD",CommonHelper.getFullPathOfCurrentRequest(request));
	    ModelAndView mav = new ModelAndView(modelViewPageName,data);
	    return mav;  
	}
	 
}
