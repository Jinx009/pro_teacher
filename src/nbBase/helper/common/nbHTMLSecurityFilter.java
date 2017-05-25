package nbBase.helper.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nbBase.database.models.ZaAdminUser;
import nbBase.database.models.ZaUser;

public class nbHTMLSecurityFilter implements Filter{

	private List<String> excludedPages = new ArrayList<String>();
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		Boolean isExcludedPath = false;
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String servletPath = httpServletRequest.getServletPath();
		
		for( String excludePath : excludedPages){
			if( checkIfThePath(servletPath, excludePath) ){
				isExcludedPath = true;
			}
			if( checkIfTheFile(servletPath, excludePath) ){
				isExcludedPath = true;
			}
		}
		
		System.out.println("nbHTMLSecurityFilter: "+servletPath+" isExcludedPath:"+isExcludedPath);
		System.out.println("当前session中的Front用户openid是: ["+CommonHelper.getOpenIdFromSession(httpServletRequest)+"]");
		
		ZaAdminUser adminUser = CommonHelper.getAdminUserFromSession(httpServletRequest);
		System.out.println("当前session中的Admin用户id是: ["+  (adminUser == null ? null : adminUser.getDisplayName()) +"]");
		
		ZaUser user = CommonHelper.getCurrentUserInfoFromSession(httpServletRequest);
		System.out.println("当前session中的注册用户id是: ["+  (user == null ? null : user.getDisplayName()) +"]");
		
		//需要拦截的
		if( !isExcludedPath ){
			//拦截admin
			if( adminUser == null && CommonHelper.getUsePerpose(servletPath).toLowerCase().equals("admin") ){
				String loginUrl = CommonHelper.getFullPathOfCurrentRequest(httpServletRequest)+"/admin/index.html";
				System.out.println(loginUrl);
				//request.getRequestDispatcher( loginUrl ).forward(request,response);
				((HttpServletResponse)response).sendRedirect(loginUrl);
				
				return;
			}
			//拦截非Admin
			if( user == null && !CommonHelper.getUsePerpose(servletPath).toLowerCase().equals("admin") ){
				String loginUrl = CommonHelper.getFullPathOfCurrentRequest(httpServletRequest)+"/login.html";
				System.out.println(loginUrl);
				//request.getRequestDispatcher( loginUrl ).forward(request,response);
				((HttpServletResponse)response).sendRedirect(loginUrl);
				
				return;
			}
		}
		
		chain.doFilter(request, response);
	}

	private boolean checkIfThePath(String servletPath, String predefinedPath) {
		predefinedPath = predefinedPath.replace("\\", "/");
		servletPath = servletPath.replace("\\", "/");
		
		while( servletPath.contains("//") ){ //去掉重复的/
			servletPath = servletPath.replace("//", "/");
		}
		
		while( predefinedPath.contains("//") ){ //去掉重复的/
			predefinedPath = predefinedPath.replace("//", "/");
		}
		
		if( ! (predefinedPath.endsWith("/") || predefinedPath.endsWith("*")) ){
			return false;
		}
		
		
		while( predefinedPath.endsWith("/")){ //去掉最后结尾的/
			predefinedPath = predefinedPath.substring(0, predefinedPath.length()-1);
		}
		
		String[] servletPathList = servletPath.split("/");
		String[] predefinedPathList = predefinedPath.split("/");
		
		boolean isAllSubpath = false;
		if( predefinedPathList[predefinedPathList.length-1].startsWith("**") )
			isAllSubpath = true;
		
		//如果是**结尾的话，那么字段个数要少一个，**不用参与比较
		for(int i = 0 ; i < (isAllSubpath ? predefinedPathList.length-1:predefinedPathList.length) ; i++){
			if( i == servletPathList.length - 1)
				return false;
			
			if( !servletPathList[i].equals(predefinedPathList[i]) ){
				return false;
			}
		}
		
		if( isAllSubpath ) return true;
		
		if( servletPathList.length -1 == predefinedPathList.length ) return true;

			
		return false;
	}
	
	private boolean checkIfTheFile(String servletPath, String predefinedPath) {
		predefinedPath = predefinedPath.replace("\\", "/");
		servletPath = servletPath.replace("\\", "/");
		
		while( servletPath.contains("//") ){ //去掉重复的/
			servletPath = servletPath.replace("//", "/");
		}
		
		while( predefinedPath.contains("//") ){ //去掉重复的/
			predefinedPath = predefinedPath.replace("//", "/");
		}
		
		if( predefinedPath.endsWith( "/" ) ){
			return false;
		}
		
		if( predefinedPath.endsWith(servletPath) ){
			return true;
		}
		else
			return false;
	}
	
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String excludedPage = filterConfig.getInitParameter("excludedPages");
		String[] excludedPageArray = excludedPage.split(";");
		excludedPages.clear();
		for(int i = 0 ; i < excludedPageArray.length ; i++){
			excludedPages.add(excludedPageArray[i].trim());
		}
	}

}
