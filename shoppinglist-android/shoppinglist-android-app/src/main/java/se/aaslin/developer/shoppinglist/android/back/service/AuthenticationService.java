package se.aaslin.developer.shoppinglist.android.back.service;

public interface AuthenticationService {
	
	void storeAuthenticationId(String uuid);
	
	String getAuthenticationId();
	
	void storeUsername(String username);

	String getUsername();
	
	void storePassword(String password);
	
	String getPassword();
}