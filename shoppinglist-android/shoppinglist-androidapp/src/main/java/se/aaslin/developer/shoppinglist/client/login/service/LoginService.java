package se.aaslin.developer.shoppinglist.client.login.service;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("login")
public interface LoginService {
	
	boolean login(String uname, String pass);

	boolean validateUserSession(String uname);
	
}
