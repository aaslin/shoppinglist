package se.aaslin.developer.shoppinglist.client.login.service;


public interface LoginService {
	
	boolean login(String uname, String pass);

	boolean validateUserSession(String uname);
	
}
