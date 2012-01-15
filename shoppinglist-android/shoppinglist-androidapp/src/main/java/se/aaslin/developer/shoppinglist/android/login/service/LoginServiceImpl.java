package se.aaslin.developer.shoppinglist.android.login.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LoginServiceImpl implements LoginService {

	Map<String, String> users;
	Set<String> sessions;
	
	public LoginServiceImpl() {
		users = new HashMap<String, String>();
		users.put("lars", "123");
		users.put("linda", "123");
		
		sessions = new HashSet<String>();
	}

	public boolean login(String uname, String pass) {
		if(users.containsKey(uname) && users.get(uname).equals(pass)){
			sessions.add(uname);
			return true;
		}
		
		return false;
	}

	public boolean validateUserSession(String uname) {
		return sessions.contains(uname);
	}

}
