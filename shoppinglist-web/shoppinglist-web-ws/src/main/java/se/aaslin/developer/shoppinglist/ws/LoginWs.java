package se.aaslin.developer.shoppinglist.ws;

import java.util.Map;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import se.aaslin.developer.shoppinglist.security.ShoppingListSessionManager;
import se.aaslin.developer.shoppinglist.shared.dto.LoginDTO;

import com.sun.jersey.api.core.InjectParam;

@Path("/login")
@Service
@Scope("request")
public class LoginWs extends GenericWs{
	
	@InjectParam ShoppingListSessionManager shoppingListSessionManager;
	
	@Context HttpHeaders headers;
	
	@POST
	@Consumes(APPLICATION_XML)
	public Response login(LoginDTO loginDTO) {
		if (shoppingListSessionManager.validateUser(loginDTO.getUsername(), loginDTO.getPassword())) {
			invalidateCurrentSession();
			UUID uuid = shoppingListSessionManager.newSession(loginDTO.getUsername());

			return Response.ok(uuid.toString()).build();
		}

		return Response.status(Response.Status.UNAUTHORIZED).build();	
	}
	
	private void invalidateCurrentSession() {
		Map<String, Cookie> cookies = headers.getCookies();
		
		if(cookies.containsKey("auth")) {
			shoppingListSessionManager.invalidateSession(cookies.get("auth").getValue());
		}
	}
}
