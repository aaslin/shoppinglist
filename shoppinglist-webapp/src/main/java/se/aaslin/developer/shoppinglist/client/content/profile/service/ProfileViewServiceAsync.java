package se.aaslin.developer.shoppinglist.client.content.profile.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProfileViewServiceAsync {

	void changePassword(String newPassword, AsyncCallback<Void> callback);

	void testSessionException(AsyncCallback<Void> callback);

	void testAuthException(AsyncCallback<Void> callback);
	
}
