package se.aaslin.developer.shoppinglist.ws;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import se.aaslin.developer.shoppinglist.security.ShoppingListSessionManager;

import com.sun.jersey.api.core.InjectParam;

@Path("/logout")
@Service
@Scope("request")
public class LogoutWs extends GenericWs{
	
	@InjectParam ShoppingListSessionManager shoppingListSessionManager;
	
	@Context HttpHeaders headers;
	
	@GET
	public Response logout() {
		try {
			invalidateCurrentSession();
		}catch (IllegalArgumentException e) {
		
		}
		return Response.ok().build();
	}
	
	private void invalidateCurrentSession() {
		Map<String, Cookie> cookies = headers.getCookies();
		
		if(cookies.containsKey("auth")) {
			shoppingListSessionManager.invalidateSession(cookies.get("auth").getValue());
		}
	}
}
