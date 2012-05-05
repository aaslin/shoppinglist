package se.aaslin.developer.shoppinglist.client.login.service;

import java.util.UUID;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginViewServiceAsync {

	void login(String uname, String pass, AsyncCallback<String> callback);

	void logout(AsyncCallback<Void> callback);
}
