package se.aaslin.developer.shoppinglist.test.system.ws.shoppinglist;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import se.aaslin.developer.shoppinglist.shared.dto.LoginDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.test.system.ws.BaseWsTest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class ShoppingListWsTest extends BaseWsTest {

	@Test
	public void testGetShoppingListUnathorized() {
		ClientConfig config = new DefaultClientConfig();	
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		
		ClientResponse response = service.path("rest").path("shoppinglist").accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.UNAUTHORIZED);
	}
	
	@Test
	public void testGetShoppingListsAuthorized() {
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
		response = service.path("rest").path("shoppinglist").cookie(cookie).accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
		
		GenericType<List<ShoppingListDTO>> type = new GenericType<List<ShoppingListDTO>>() {};
		List<ShoppingListDTO> dtos = response.getEntity(type);
		Assert.assertNotNull(dtos);
	}
	
	@Test
	public void testSaveShoppingList() {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername("Lars");
		loginDTO.setPassword("abc123");
		
		ClientResponse response = service.path("rest").path("login").accept(MediaType.APPLICATION_XML).post(ClientResponse.class, loginDTO);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
		String uuid = response.getEntity(String.class);
		
		ShoppingListDTO shoppingListDTO = new ShoppingListDTO();
		shoppingListDTO.setFromDB(false);
		shoppingListDTO.setName("test");
		shoppingListDTO.setMembers(Arrays.asList(new String[] { "Linda" }));
		
		Cookie cookie = new Cookie("auth", uuid);
		response = service.path("rest").path("shoppinglist").cookie(cookie).accept(MediaType.APPLICATION_XML).post(ClientResponse.class, shoppingListDTO);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
		
		response = service.path("rest").path("shoppinglist").cookie(cookie).accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
		
		GenericType<List<ShoppingListDTO>> type = new GenericType<List<ShoppingListDTO>>() {};
		List<ShoppingListDTO> dtos = response.getEntity(type);
		Assert.assertNotNull(dtos);
		
		for (ShoppingListDTO dto : dtos) {
			if (dto.getName().equals("test")) {
				Assert.assertNotNull(dto.getMembers());
				Assert.assertEquals(1, dto.getMembers().size());
				Assert.assertEquals("Linda", dto.getMembers().get(0));
				return;
			}
		}
		
		Assert.fail();
	}
	
	@Test
	public void testRemoveShoppingListUnauthorized() {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername("Linda");
		loginDTO.setPassword("abc123");
		
		ClientResponse response = service.path("rest").path("login").accept(MediaType.APPLICATION_XML).post(ClientResponse.class, loginDTO);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
		String uuid = response.getEntity(String.class);
		
		Cookie cookie = new Cookie("auth", uuid);
		response = service.path("rest").path("shoppinglist").cookie(cookie).accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
		
		GenericType<List<ShoppingListDTO>> type = new GenericType<List<ShoppingListDTO>>() {};
		List<ShoppingListDTO> dtos = response.getEntity(type);
		Assert.assertNotNull(dtos);
		
		ShoppingListDTO remove = null;
		for (ShoppingListDTO dto : dtos) {
			if (dto.getName().equals("test")) {
				remove = dto;
				break;
			}
		}
		
		response = service.path("rest").path("shoppinglist").path(String.format("/%s", remove.getID())).cookie(cookie).delete(ClientResponse.class);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.UNAUTHORIZED);
	}
	
	@Test
	public void testRemoveShoppingList() {
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
		response = service.path("rest").path("shoppinglist").cookie(cookie).accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
		
		GenericType<List<ShoppingListDTO>> type = new GenericType<List<ShoppingListDTO>>() {};
		List<ShoppingListDTO> dtos = response.getEntity(type);
		Assert.assertNotNull(dtos);
		
		ShoppingListDTO remove = null;
		for (ShoppingListDTO dto : dtos) {
			if (dto.getName().equals("test")) {
				remove = dto;
				break;
			}
		}
		
		response = service.path("rest").path("shoppinglist").path(String.format("/%s", remove.getID())).cookie(cookie).delete(ClientResponse.class);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
	}
	
	@Test
	public void testGetShoppingListAuthorized() {
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
		response = service.path("rest").path("shoppinglist").path(String.format("/%s", 24)).cookie(cookie).accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		Assert.assertTrue(ClientResponse.Status.fromStatusCode(response.getStatus()) == ClientResponse.Status.OK);
		
		GenericType<ShoppingListDTO> type = new GenericType<ShoppingListDTO>() {};
		ShoppingListDTO dto = response.getEntity(type);
		Assert.assertNotNull(dto);
	}
}
