package se.aaslin.developer.shoppinglist.android.back.service;

import se.aaslin.developer.shoppinglist.android.app.exception.AuthenticationFailedException;
import se.aaslin.developer.shoppinglist.android.app.exception.HttpException;


public interface LoginService {
	
	String login(String username, String password) throws AuthenticationFailedException, HttpException;
}
