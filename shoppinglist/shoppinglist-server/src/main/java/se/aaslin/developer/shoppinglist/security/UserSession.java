package se.aaslin.developer.shoppinglist.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("request")
public class UserSession {
	
	@Autowired(required = true) HttpServletRequest request;
	@Autowired ShoppingListSessionManager sessionManager;
	
	public String getCurrentSessionsUsername() {
		return UserSessionUtils.getCurrentUsername(request, sessionManager);
	}
}
