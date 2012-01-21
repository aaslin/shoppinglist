package se.aaslin.developer.shoppinglist.android.login.service;

import se.aaslin.developer.robosync.SyncProxy;


public class LoginServiceImpl implements LoginService{

	LoginService loginService = (LoginService) SyncProxy.newProxyInstance(LoginService.class, "http://192.168.0.12:8080/shoppinglist/gwt.shoppinglist/", "login");

	public boolean login(String uname, String pass) {
		return loginService.login(uname, pass);
	}

	public boolean validateUserSession(String uname) {
		return loginService.validateUserSession(uname);
	}

}
