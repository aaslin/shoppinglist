package se.aaslin.developer.shoppinglist.client.login.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService{

	Boolean login(String uname, String pass);
	
	void logout();
}
