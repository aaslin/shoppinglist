package se.aaslin.developer.shoppinglist.test.system.ws.registration;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import se.aaslin.developer.shoppinglist.shared.dto.LoginDTO;
import se.aaslin.developer.shoppinglist.shared.dto.RegistrationDTO;
import se.aaslin.developer.shoppinglist.test.system.ws.BaseWsTest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RegistrationWsTest extends BaseWsTest {

	@Test
	public void testValidRegistration() {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername("Lars");
		loginDTO.setPassword("abc123");
		
		ClientResponse response = service.path("rest").path("login").accept(MediaType.APPLICATION_XML).post(ClientResponse.class, loginDTO);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
		String uuid = response.getEntity(String.class);
		Assert.assertNotNull(uuid);
		
		Cookie cookie = new Cookie("auth", uuid);
		RegistrationDTO registration = new RegistrationDTO();
		registration.setRegistration("test");
		response = service.path("rest").path("registration").cookie(cookie).accept(MediaType.APPLICATION_XML).post(ClientResponse.class, registration);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
	}
}

