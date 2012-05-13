package se.aaslin.developer.shoppinglist.android.service;

import se.aaslin.developer.shoppinglist.android.exception.AuthenticationFailedException;
import se.aaslin.developer.shoppinglist.android.exception.HttpException;


public interface LoginService {
	
	String login(String username, String password) throws AuthenticationFailedException, HttpException;
}
