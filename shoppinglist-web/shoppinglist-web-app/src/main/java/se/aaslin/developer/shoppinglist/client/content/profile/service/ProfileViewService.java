package se.aaslin.developer.shoppinglist.client.content.profile.service;

import se.aaslin.developer.shoppinglist.shared.exception.SessionExpiredException;
import se.aaslin.developer.shoppinglist.shared.exception.NotAuthorizedException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("profile")
public interface ProfileViewService extends RemoteService {

	void changePassword(String newPassword);

	// Dummy
	void testAuthException() throws NotAuthorizedException;

	void testSessionException() throws SessionExpiredException;
}
