package se.aaslin.developer.shoppinglist.server.login;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import se.aaslin.developer.shoppinglist.client.login.service.LoginService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService{

	private static final long serialVersionUID = -7220706604802765894L;

	private Map<String, String> users;
	private Set<String> loggedinUsers;
	
	public LoginServiceImpl() {
		users = new HashMap<String, String>();
		users.put("lars", "123");
		users.put("linda", "123");
		
		loggedinUsers = new HashSet<String>();
	}

	@Override
	public boolean login(String uname, String pass) {
		if(users.containsKey(uname) && users.get(uname).equals(pass)){
			loggedinUsers.add(uname);
			return true;
		}
		return false;
	}

	@Override
	public boolean validateUserSession(String uname) {
		return loggedinUsers.contains(uname);
	}

	@Override
	protected void checkPermutationStrongName() throws SecurityException {
		return;
	}
	
	
	
}
