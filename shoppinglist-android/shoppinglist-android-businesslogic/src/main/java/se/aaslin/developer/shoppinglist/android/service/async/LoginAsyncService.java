package se.aaslin.developer.shoppinglist.android.service.async;

import se.aaslin.developer.shoppinglist.app.mvp.AsyncCallback;

public interface LoginAsyncService {
	
	void login(final String username, String password, AsyncCallback<String> callback);
}
