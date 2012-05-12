package se.aaslin.developer.shoppinglist.server.entrypoint;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import se.aaslin.developer.shoppinglist.client.entrypoint.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.security.UserSession;
import se.aaslin.developer.shoppinglist.shared.dto.AuthenticationDTO;

@Service
@Scope("request")
public class AuthenticationSericeImpl implements AuthenticationService {

	@Autowired UserSession userSession;
	
	@PermitAll
	@Override
	public AuthenticationDTO getCurrentAuthentication() {
		AuthenticationDTO dto = new AuthenticationDTO(); 
		String username = userSession.getCurrentSessionsUsername();
		dto.setUsername(username);
		
		return dto;
	}
}
