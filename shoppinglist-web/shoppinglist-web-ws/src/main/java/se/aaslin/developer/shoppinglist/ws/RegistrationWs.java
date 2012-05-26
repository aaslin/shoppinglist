package se.aaslin.developer.shoppinglist.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.jersey.api.core.InjectParam;

import se.aaslin.developer.shoppinglist.security.UserSession;
import se.aaslin.developer.shoppinglist.service.UserService;
import se.aaslin.developer.shoppinglist.shared.dto.RegistrationDTO;
import se.aaslin.developer.shoppinglist.shared.exception.NotAuthorizedException;

@Path("/registration")
@Service
@Scope("request")
@Transactional
public class RegistrationWs extends GenericWs {

	@InjectParam UserService userService;
	@InjectParam UserSession userSession;
	
	@POST
	@Consumes(APPLICATION_XML)
	public Response register(RegistrationDTO regstrationDTO) {
		try {
			String username = userSession.getCurrentSessionsUsername();
			if(username == null) {
				throw new NotAuthorizedException();
			}
			userService.register(username, regstrationDTO.getRegistration());
			
			return Response.ok().build();
		} catch (NotAuthorizedException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
}
