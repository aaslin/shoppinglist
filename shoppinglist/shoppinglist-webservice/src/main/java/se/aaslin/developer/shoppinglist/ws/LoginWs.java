package se.aaslin.developer.shoppinglist.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import se.aaslin.developer.shoppinglist.shared.dto.LoginDTO;

@Path("/shoppinglist")
public interface LoginWs {
	public static final String APPLICATION_XML = "application/xml";
	public static final String APPLICATION_JSON = "application/json";
	
	@POST
	@Consumes(APPLICATION_XML)
	public Response login(LoginDTO loginDTO);
}
