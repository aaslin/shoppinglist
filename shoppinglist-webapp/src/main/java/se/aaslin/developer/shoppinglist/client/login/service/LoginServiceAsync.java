package se.aaslin.developer.shoppinglist.client.login.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	void login(String uname, String pass, AsyncCallback<Boolean> callback);
}
