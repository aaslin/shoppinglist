package se.aaslin.developer.shoppinglist.android.back.service;


public interface AuthenticationService {
	
	void storeAuthenticationId(String uuid);
	
	void removeAuthenticationId();
	
	String getAuthenticationId();
	
	void storeUsername(String username);

	String getUsername();
	
	void storePassword(String password);
	
	String getPassword();
	
	void storeRegistration(String registration);
	
	String getRegistration();
}
