package se.aaslin.developer.shoppinglist.ws.impl;

import java.util.Map;
import java.util.UUID;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import se.aaslin.developer.shoppinglist.security.ShoppingListSessionManager;
import se.aaslin.developer.shoppinglist.shared.dto.LoginDTO;

import com.sun.jersey.api.core.InjectParam;

@Component
@Scope("request")
public class LoginWsImpl {
	
	@InjectParam ShoppingListSessionManager shoppingListSessionManager;
	
	@Context HttpHeaders headers;
	
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
