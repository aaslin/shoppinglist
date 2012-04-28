package se.aaslin.developer.shoppinglist.client.content.profile.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProfileServiceAsync {

	void changePassword(String newPassword, AsyncCallback<Void> callback);
}
