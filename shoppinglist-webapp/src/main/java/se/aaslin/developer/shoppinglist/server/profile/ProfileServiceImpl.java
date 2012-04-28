package se.aaslin.developer.shoppinglist.server.profile;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import se.aaslin.developer.shoppinglist.client.content.profile.service.ProfileService;
import se.aaslin.developer.shoppinglist.security.CookieUtils;
import se.aaslin.developer.shoppinglist.security.ShoppingListSessionManager;
import se.aaslin.developer.shoppinglist.server.SpringRemoteServiceServlet;
import se.aaslin.developer.shoppinglist.service.UserService;

public class ProfileServiceImpl extends SpringRemoteServiceServlet implements ProfileService {

	private static final long serialVersionUID = 4167829017313497676L;

	@Autowired UserService userService;
	@Autowired ShoppingListSessionManager sessionManager;
	
	@Override
	public void changePassword(String newPassword) {
		userService.changePassword(getCurrentUsername(), newPassword);
	}
	
	private String getCurrentUsername() {
		String cookie = CookieUtils.getAuthCookie(getThreadLocalRequest());
		if (cookie == null) {
			return null;
		}
		String username = sessionManager.getSessionUser(UUID.fromString(cookie));
		
		return username;
	}
}
