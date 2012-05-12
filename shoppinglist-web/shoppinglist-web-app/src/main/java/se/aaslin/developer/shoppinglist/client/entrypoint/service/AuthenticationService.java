package se.aaslin.developer.shoppinglist.client.entrypoint.service;

import se.aaslin.developer.shoppinglist.shared.dto.AuthenticationDTO;
import se.aaslin.developer.shoppinglist.shared.exception.ServerSideException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("authentication")
public interface AuthenticationService extends RemoteService {

	AuthenticationDTO getCurrentAuthentication() throws ServerSideException;
}
