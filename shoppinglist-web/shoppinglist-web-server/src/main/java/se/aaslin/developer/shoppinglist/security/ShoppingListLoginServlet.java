package se.aaslin.developer.shoppinglist.security;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ShoppingListLoginServlet extends HttpServlet {
	private static final long serialVersionUID = -288714625274486724L;

	@Autowired ShoppingListSessionManager shoppingListSessionManager;

	@Override
	public void init() throws ServletException {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		context.getAutowireCapableBeanFactory().autowireBean(this);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String baseURL = req.getRequestURL().toString().replace(req.getRequestURI(), req.getContextPath());
		if (shoppingListSessionManager.validateUser(username, password)) {
			if (req.getCookies() != null) {
				for (Cookie cookie : req.getCookies()) {
					if(cookie.getName().equals("auth")) {
						shoppingListSessionManager.invalidateSession(cookie.getValue());
					}
				}
			}
			UUID uuid = shoppingListSessionManager.newSession(username);
			Cookie cookie = new Cookie("auth", uuid.toString());
			cookie.setPath("/shoppinglist");
			resp.addCookie(cookie);	
			
			String indexURL = baseURL + "/index.jsp#shoppinglist";
			resp.sendRedirect(indexURL);
		} else {
			String loginURL = baseURL + "/login.jsp";
			resp.sendRedirect(loginURL);
		}
	}
}
