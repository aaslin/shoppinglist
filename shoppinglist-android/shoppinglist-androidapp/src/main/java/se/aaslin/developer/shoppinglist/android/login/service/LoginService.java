package se.aaslin.developer.shoppinglist.android.login.service;

public interface LoginService{

	boolean login(String uname, String pass);

	boolean validateUserSession(String uname);

}
