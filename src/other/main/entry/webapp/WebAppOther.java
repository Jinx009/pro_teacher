package other.main.entry.webapp;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.nbReturn;
import nbBase.service.front.UserInfoService;
import nbBase.service.weibo.sdk.weibo4j.Friendships;
import nbBase.service.weibo.sdk.weibo4j.Oauth;
import nbBase.service.weibo.sdk.weibo4j.Timeline;
import nbBase.service.weibo.sdk.weibo4j.http.AccessToken;
import nbBase.service.weibo.sdk.weibo4j.http.ImageItem;
import nbBase.service.weibo.sdk.weibo4j.model.Status;
import nbBase.service.weibo.sdk.weibo4j.model.User;
import nbBase.service.weibo.sdk.weibo4j.model.WeiboException;
import other.helper.other.casioEventHelper;
import other.service.other.OtherApplicationService;

/**
 * 这里是定义后台admin入口html的地方，前端功能用的入口html请移步WebAppMain.java
 * @author yuhangs
 *
 */

@Controller
public class WebAppOther {

	 @Autowired  
	 private UserInfoService userInfoService;
	 
	 @Autowired  
	 private OtherApplicationService otherApplicationService;
	 

	/**
	 * other的统一入口
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/other/{functionName}") 
	public ModelAndView wechatSendAward(HttpServletResponse response,HttpServletRequest request, @PathVariable String functionName) throws Exception{  
	    //创建模型跟视图，用于渲染页面。并且指定要返回的页面为home页面  
		nbReturn nbRet = new nbReturn();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("serverUrl", CommonHelper.getFullPathOfCurrentRequest(request));
		data.put("successToUrl", "/other/home.html");
		String modelViewPageName = "other/"+functionName;
		
		String userAgent = request.getHeader("User-Agent");
		System.out.println(userAgent);
		
		
		Map<String, String[]> param = request.getParameterMap();		
		
		switch(functionName){
		
		case "casio":
			modelViewPageName += "160531";
		case "casio160531":
			if( userAgent != null && userAgent.contains("MicroMessenger") ){
				modelViewPageName += "_wx";
			}else{
				modelViewPageName += "_wb";
			}
			break;
			
		case "casioPost":
				modelViewPageName = "other/casio160531_result";
				if( userAgent != null && userAgent.contains("MicroMessenger") ){
					modelViewPageName += "_wx";
				}else{
					modelViewPageName += "_wb";
				}
				String weiboAuthCode = param.get("code") == null ? null : param.get("code")[0];
				String picCode = param.get("state") == null ? null : param.get("state")[0];
				if( weiboAuthCode != null ){
					doSendWeiboText(weiboAuthCode, picCode);
					otherApplicationService.updateStatusRecordWeiboShareResult(picCode, true);
				}
				System.out.println("weiboAuthCode = " + weiboAuthCode);
			break;
//			
//		default:
//			data.put("retmessage", "你要的服务没找到哦，不要乱输地址哦！");
//			modelViewPageName = "retPage";
//			break;
		}
		
	    data.put("ret","nbReturn");
	    data.put("serverPWD",CommonHelper.getFullPathOfCurrentRequest(request));
	    ModelAndView mav = new ModelAndView(modelViewPageName,data);
	    return mav;  
	}
	
	private String doSendWeiboText(String weiboCode , String picCode){
		
		//Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		
		
		Oauth oauth = new Oauth();
		AccessToken accessToken = null;
//		BareBonesBrowserLaunch.openURL(oauth.authorize("code"));
//		System.out.println(oauth.authorize("code"));
//		System.out.print("Hit enter when it's done.[Enter]:");
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		String code = br.readLine();
		System.out.println("code: " + weiboCode);
		try{
			accessToken = oauth.getAccessTokenByCode(weiboCode);
			System.out.println(accessToken);
		} catch (WeiboException e) {
			if(401 == e.getStatusCode()){
				System.out.println("Unable to get the access token.");
			}else{
				e.printStackTrace();
			}
		}
		
		if( accessToken != null){
			System.out.println("weibo accessToken="+accessToken.getAccessToken());
			String text = "#30年，音为梦想# #当年的小可爱，现在变路人甲了么？晒对比照片，赢礼品！#， @中国CASIO电子乐器，活动链接：http://casio001.0angle.com/other/casio.html ←快来戳我，参加活动吧！";
			String filename = "/usr/local/www/pc-res.0angle.com/casio/"+picCode+".jpg";
			Timeline timeLine = new Timeline(accessToken.getAccessToken());
			//Status status = timeLine.updateStatus(text);
			ImageItem imageItem;
			Status status = null;
			try {
				imageItem = new ImageItem("pic",casioEventHelper.readFileImage(filename));
				text = URLEncoder.encode(text,"utf-8");
				status = timeLine.uploadStatus(text, imageItem);
				System.out.println(status.toString());
			} catch (WeiboException | IOException e1) {
				e1.printStackTrace();
			}
			
			
			
			
			try{
				Friendships friendShips = new Friendships(accessToken.getAccessToken());
				User user = friendShips.createFriendshipsByName("中国CASIO电子乐器");
				System.out.println(user.toString());
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println(e.getMessage());
				System.out.println(e.getCause());
				System.out.println("微博的关注API出错了");
			}
			
			accessToken.unregisterAccessToken();
			
		}
		
		return accessToken == null ? null : accessToken.getAccessToken();
		
	}
	 
}
