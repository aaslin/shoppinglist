package se.aaslin.developer.shoppinglist.server;

import javax.servlet.ServletException;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public abstract class SpringRemoteServiceServlet extends RemoteServiceServlet {

	private static final long serialVersionUID = 3030212688199661951L;

	@Override
	public void init() throws ServletException {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		context.getAutowireCapableBeanFactory().autowireBean(this);
		
		super.init();
	}
	
	
}
