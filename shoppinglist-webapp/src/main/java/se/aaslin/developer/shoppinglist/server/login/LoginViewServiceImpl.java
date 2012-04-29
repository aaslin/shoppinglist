package se.aaslin.developer.shoppinglist.server.login;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import se.aaslin.developer.shoppinglist.client.login.service.LoginViewService;
import se.aaslin.developer.shoppinglist.security.ShoppingListSessionManager;

@Service
@Scope("request")
public class LoginViewServiceImpl implements LoginViewService{

	@Autowired(required = true) HttpServletRequest request;
	@Autowired(required = true) HttpServletResponse response;
	@Autowired ShoppingListSessionManager shoppingListSessionManager;
	
	@Override
	public Boolean login(String username, String password) {
		if (shoppingListSessionManager.validateUser(username, password)) {
			invalidateCurrentSession();
			UUID uuid = shoppingListSessionManager.newSession(username);
			response.addCookie(new Cookie("auth", uuid.toString()));
			
			return true;
		}
		
		return false;
	}

	@Override
	public void logout() {
		invalidateCurrentSession();
	}

	private void invalidateCurrentSession() {
		for (Cookie cookie : request.getCookies()) {
			if(cookie.getName().equals("auth")) {
				shoppingListSessionManager.invalidateSession(cookie.getValue());
			}
		}
	}
}
