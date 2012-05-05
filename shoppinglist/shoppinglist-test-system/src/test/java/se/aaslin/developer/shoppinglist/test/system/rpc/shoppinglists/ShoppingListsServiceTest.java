package se.aaslin.developer.shoppinglist.test.system.rpc.shoppinglists;

import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URL;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListsService;
import se.aaslin.developer.shoppinglist.client.login.service.LoginViewService;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.shared.exception.NotAuthorizedException;
import se.aaslin.developer.shoppinglist.shared.exception.ServerSideException;
import se.aaslin.developer.shoppinglist.test.system.Constants;

import com.gdevelop.gwt.syncrpc.SyncProxy;

public class ShoppingListsServiceTest {
	
	@Test 
	public void testGetShoppingListsAuthorized() { 
		try {
			LoginViewService loginSrv = (LoginViewService) SyncProxy.newProxyInstance(LoginViewService.class, Constants.URL_LOGIN_MODULE);
			String uuid = loginSrv.login("Lars", "abc123");
			CookieManager cookieManager = new CookieManager();
		
			HttpCookie cookie = new HttpCookie("auth", uuid);
			cookie.setPath("/shoppinglist");
			cookieManager.getCookieStore().add(new URL("http://localhost:8080").toURI(), cookie);
			
			ShoppingListsService shoppingListSrv = (ShoppingListsService) SyncProxy.newProxyInstance(ShoppingListsService.class, Constants.URL_SHOPPING_LIST_MODULE, cookieManager);
			List<ShoppingListDTO> result = shoppingListSrv.getShoppingLists();
			Assert.assertNotNull(result);
			Assert.assertTrue(result.size() > 0);			
			
			ShoppingListDTO list = result.get(0);
			
			ShoppingListDTO list2 = shoppingListSrv.getShoppingList(list.getID());
			Assert.assertTrue(list.getID() == list2.getID());
			
			List<ShoppingItemDTO> items = shoppingListSrv.getShoppingItems(list.getID());
			Assert.assertNotNull(items);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void testGetShoppingListsUnauthorized() throws ServerSideException {
		ShoppingListsService shoppingListSrv = (ShoppingListsService) SyncProxy.newProxyInstance(ShoppingListsService.class, Constants.URL_SHOPPING_LIST_MODULE, new CookieManager());
		shoppingListSrv.getShoppingLists();
	}
	
	@Test 
	public void testGetShoppingListsExpiredAuthentication() { 
		try {
			LoginViewService loginSrv = (LoginViewService) SyncProxy.newProxyInstance(LoginViewService.class, Constants.URL_LOGIN_MODULE);
			
			CookieManager cookieManager = new CookieManager();
			HttpCookie cookie = new HttpCookie("auth", "123");
			cookie.setPath("/shoppinglist");
			cookieManager.getCookieStore().add(new URL("http://localhost:8080").toURI(), cookie);
			
			ShoppingListsService shoppingListSrv = (ShoppingListsService) SyncProxy.newProxyInstance(ShoppingListsService.class, Constants.URL_SHOPPING_LIST_MODULE, cookieManager);
			try {
				shoppingListSrv.getShoppingLists();
			} catch (NotAuthorizedException e) {
				String uuid = loginSrv.login("Lars", "abc123");
				cookie = new HttpCookie("auth", uuid);
				cookie.setPath("/shoppinglist");
				cookieManager.getCookieStore().add(new URL("http://localhost:8080").toURI(), cookie);
				
				List<ShoppingListDTO> result = shoppingListSrv.getShoppingLists();
				Assert.assertNotNull(result);
				Assert.assertTrue(result.size() > 0);	
				return;
			}
			
			Assert.fail();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
