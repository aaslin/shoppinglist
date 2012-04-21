package se.aaslin.developer.shoppinglist.security;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ShoppingListSessionManager {
	
	private static final Map<UUID, String> loggedinUsers = new HashMap<UUID, String>();
	private Map<String, String> users;

	public ShoppingListSessionManager() {
		users = new HashMap<String, String>();
		users.put("lars", "123");
		users.put("linda", "123");	
	}

	public boolean validateUser(String username, String pass) {
		if (users.containsKey(username) && users.get(username).equals(pass)) {
			return true;
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
