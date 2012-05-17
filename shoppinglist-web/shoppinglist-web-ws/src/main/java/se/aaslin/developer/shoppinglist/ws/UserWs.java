package se.aaslin.developer.shoppinglist.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.aaslin.developer.shoppinglist.security.ShoppingListSessionManager;
import se.aaslin.developer.shoppinglist.security.UserSession;
import se.aaslin.developer.shoppinglist.service.UserService;
import se.aaslin.developer.shoppinglist.shared.dto.UserDTO;
import se.aaslin.developer.shoppinglist.shared.exception.NotAuthorizedException;

import com.sun.jersey.api.core.InjectParam;

@Path("/user")
@Service
@Transactional
public class UserWs extends GenericWs {

	@InjectParam UserService userService;
	@InjectParam ShoppingListSessionManager shoppingListSessionManager;
	@InjectParam UserSession userSession;
	
	@GET
	@Produces({ APPLICATION_XML, APPLICATION_JSON })
	public Response getAllUsers() {
		try {
			String username = userSession.getCurrentSessionsUsername();
			if(username == null) {
				throw new NotAuthorizedException();
			}
			List<String> users = userService.getAllUsers(username);
			List<UserDTO> dtos = createUserDTOs(users);
			
			GenericEntity<List<UserDTO>> entity = new GenericEntity<List<UserDTO>>(dtos) {};
			
			return Response.ok(entity).build();
		} catch (NotAuthorizedException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	private List<UserDTO> createUserDTOs(List<String> users) {
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		for (String user : users) {
			UserDTO dto = new UserDTO();
			dto.setUsername(user);
			dtos.add(dto);
		}
		
		return dtos;
	}
}
