package se.aaslin.developer.shoppinglist.security;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ShoppingListSecurityFilter implements Filter{

	@Autowired ShoppingListSessionManager shoppingListSessionManager;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String baseURL = httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getRequestURI(), httpServletRequest.getContextPath());
		String loginURL = baseURL + "/login.jsp";
		String gwtLogin = baseURL + "/gwt.login";
		String checkLogin = baseURL + "/security_check";
		
		String thisURL = httpServletRequest.getRequestURL().toString();
		
		if (thisURL.equals(loginURL) || thisURL.startsWith(gwtLogin) || thisURL.startsWith(checkLogin) || isSessionValid(httpServletRequest)) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
			httpServletResponse.sendRedirect(loginURL);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ServletContext servletContext = filterConfig.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
		autowireCapableBeanFactory.autowireBean(this);
	} 

	private boolean isSessionValid(HttpServletRequest httpServletRequest) {
		if (httpServletRequest.getCookies() == null) {
			return false;
		}
		for (Cookie cookie : httpServletRequest.getCookies()) {
			if (cookie.getName() != null && cookie.getName().equals("auth")) {
				UUID sessionId = UUID.fromString(cookie.getValue());
				if (shoppingListSessionManager.isSessionValid(sessionId)) {
					return true;
				}
			}
		}
		
		return false;
	}
}
