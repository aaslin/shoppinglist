package se.aaslin.developer.shoppinglist.android.back.service;

import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;

public interface LoginServiceAsync {
	
	void login(String username, String password, AsyncCallback<String> callback);
}