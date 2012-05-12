package se.aaslin.developer.shoppinglist.client.entrypoint.service;

import se.aaslin.developer.shoppinglist.shared.dto.AuthenticationDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AuthenticationServiceAsync {

	void getCurrentAuthentication(AsyncCallback<AuthenticationDTO> callback);
}
