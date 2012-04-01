package se.aaslin.developer.shoppinglist.server.login;

import java.util.UUID;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;

import se.aaslin.developer.shoppinglist.client.login.service.LoginService;
import se.aaslin.developer.shoppinglist.security.ShoppingListSessionManager;
import se.aaslin.developer.shoppinglist.server.SpringRemoteServiceServlet;

public class LoginServiceImpl extends SpringRemoteServiceServlet implements LoginService{

	private static final long serialVersionUID = -7220706604802765894L;
	
	@Autowired ShoppingListSessionManager shoppingListSessionManager;
	
	@Override
	public Boolean login(String username, String password) {
		if (shoppingListSessionManager.validateUser(username, password)) {
			for (Cookie cookie : getThreadLocalRequest().getCookies()) {
				if(cookie.getName().equals("auth")) {
					shoppingListSessionManager.invalidateSession(cookie.getValue());
				}
			}
			UUID uuid = shoppingListSessionManager.newSession(username);
			getThreadLocalResponse().addCookie(new Cookie("auth", uuid.toString()));
			
			return true;
		}
		
		return false;
	}
}
