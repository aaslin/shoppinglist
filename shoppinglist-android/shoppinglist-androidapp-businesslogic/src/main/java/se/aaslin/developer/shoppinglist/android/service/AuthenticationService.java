package se.aaslin.developer.shoppinglist.android.service;

public interface AuthenticationService {
	
	void storeAuthenticationId(String uuid);
	
	String getAuthenticationId();
}
