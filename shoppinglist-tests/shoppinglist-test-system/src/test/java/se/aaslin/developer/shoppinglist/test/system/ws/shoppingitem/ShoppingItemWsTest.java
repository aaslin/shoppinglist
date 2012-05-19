package se.aaslin.developer.shoppinglist.test.system.ws.shoppingitem;

import java.util.List;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import se.aaslin.developer.shoppinglist.shared.dto.LoginDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.test.system.ws.BaseWsTest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class ShoppingItemWsTest extends BaseWsTest {
	
	@Test
	public void testGetShoppingItemsAuthorized() {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername("Lars");
		loginDTO.setPassword("abc123");
		
		ClientResponse response = service.path("rest").path("login").accept(MediaType.APPLICATION_XML).post(ClientResponse.class, loginDTO);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
		String uuid = response.getEntity(String.class);
		
		Cookie cookie = new Cookie("auth", uuid);
		response = service.path("rest").path("shoppingitem").path("/" + 24).cookie(cookie).accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
		
		GenericType<List<ShoppingItemDTO>> type = new GenericType<List<ShoppingItemDTO>>() {};
		List<ShoppingItemDTO> dtos = response.getEntity(type);
		Assert.assertNotNull(dtos);
	}
	
	@Test
	public void testSaveShoppingItemsAuthorized() {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername("Lars");
		loginDTO.setPassword("abc123");
		
		ClientResponse response = service.path("rest").path("login").accept(MediaType.APPLICATION_XML).post(ClientResponse.class, loginDTO);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
		String uuid = response.getEntity(String.class);
		
		ShoppingItemDTO shoppingItemDTO = new ShoppingItemDTO();
		shoppingItemDTO.setName("test");
		shoppingItemDTO.setFromDB(false);
		shoppingItemDTO.setAmount("12 st");
		
		Cookie cookie = new Cookie("auth", uuid);
		response = service.path("rest").path("shoppingitem").path("/" + 24).cookie(cookie).accept(MediaType.APPLICATION_XML).post(ClientResponse.class, shoppingItemDTO);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
	}
	
	@Test
	public void testDeleteShoppingItemsAuthorized() {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername("Lars");
		loginDTO.setPassword("abc123");
		
		ClientResponse response = service.path("rest").path("login").accept(MediaType.APPLICATION_XML).post(ClientResponse.class, loginDTO);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
		String uuid = response.getEntity(String.class);
		
		Cookie cookie = new Cookie("auth", uuid);
		response = service.path("rest").path("shoppingitem").path("/" + 24).cookie(cookie).accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
		
		GenericType<List<ShoppingItemDTO>> type = new GenericType<List<ShoppingItemDTO>>() {};
		List<ShoppingItemDTO> dtos = response.getEntity(type);
		int id = dtos.get(0).getId();
		
		response = service.path("rest").path("shoppingitem").path("/" + id).cookie(cookie).accept(MediaType.APPLICATION_XML).delete(ClientResponse.class);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
	}
}
