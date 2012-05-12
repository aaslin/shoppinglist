package se.aaslin.developer.shoppinglist.server.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.aaslin.developer.shoppinglist.client.content.profile.service.ProfileViewService;
import se.aaslin.developer.shoppinglist.security.UserSession;
import se.aaslin.developer.shoppinglist.service.UserService;
import se.aaslin.developer.shoppinglist.shared.exception.SessionExpiredException;
import se.aaslin.developer.shoppinglist.shared.exception.NotAuthorizedException;

@Service
@Scope("request")
@Transactional
public class ProfileViewServiceImpl implements ProfileViewService {

	@Autowired UserService userService;
	@Autowired UserSession userSession;
	
	@Override
	public void changePassword(String newPassword) {
		userService.changePassword(userSession.getCurrentSessionsUsername(), newPassword);
	}

	@Override
	public void testAuthException() throws NotAuthorizedException {
		throw new NotAuthorizedException();
	}

	@Override
	public void testSessionException() throws SessionExpiredException {
		throw new SessionExpiredException();
	}
}
