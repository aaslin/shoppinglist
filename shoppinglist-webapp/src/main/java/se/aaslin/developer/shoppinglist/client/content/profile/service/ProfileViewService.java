package se.aaslin.developer.shoppinglist.client.content.profile.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("profile")
public interface ProfileViewService extends RemoteService {
	
	void changePassword(String newPassword);
}