package se.aaslin.developer.shoppinglist.security;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.aaslin.developer.shoppinglist.dao.UserDAO;
import se.aaslin.developer.shoppinglist.entity.User;

@Service
public class ShoppingListSessionManager {
	
	private static final Map<UUID, String> loggedinUsers = new HashMap<UUID, String>();

	@Autowired UserDAO userDAO;
	
	public ShoppingListSessionManager() {
	}

	public boolean validateUser(String username, String pass) {
		User user = userDAO.findByUsername(username);
		if (user != null) {
			return user.getPassword().equals(pass);
		}

		return false;
	}
	
	public void invalidateSession(String uuid) {
		loggedinUsers.remove(UUID.fromString(uuid));
	}
	
	public UUID newSession(String uname) {
		UUID uuid = UUID.randomUUID();
		loggedinUsers.put(uuid, uname);
		
		return uuid;
	}
	
	public void addSession(String uname, String jsessionid) {
		loggedinUsers.put(UUID.fromString(jsessionid), uname);
	}
	
	public boolean isSessionValid(UUID sessionId) {
		return loggedinUsers.containsKey(sessionId);
	}
	
	public String getSessionUser(UUID sessionId) {
		return loggedinUsers.get(sessionId);
	}
}
