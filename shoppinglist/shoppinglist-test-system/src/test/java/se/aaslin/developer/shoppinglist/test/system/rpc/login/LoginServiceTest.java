package se.aaslin.developer.shoppinglist.test.system.rpc.login;

import java.net.CookieManager;
import java.net.HttpCookie;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import se.aaslin.developer.shoppinglist.client.login.service.LoginViewService;
import se.aaslin.developer.shoppinglist.test.system.Constants;
import se.aaslin.developer.shoppinglist.test.system.LoginUtils;

import com.gdevelop.gwt.syncrpc.SyncProxy;

public class LoginServiceTest {

	@Test 
	public void testLoginServlet() {
		try {
			CookieManager cookieManager = LoginUtils.login();
			List<HttpCookie> cookies = cookieManager.getCookieStore().getCookies();
			
			String auth = null;
			for (HttpCookie cookie : cookies) {
				if (cookie.getName().equals("auth")) {
					auth = cookie.getValue();
				}
			}
			
			Assert.assertNotNull(auth);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testValidLogin() {
		try {
			LoginViewService srv = (LoginViewService) SyncProxy.newProxyInstance(LoginViewService.class, Constants.URL_LOGIN_MODULE);
			String result = srv.login("Lars", "abc123");
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testUnvalidLogin() {
		try {
			LoginViewService srv = (LoginViewService) SyncProxy.newProxyInstance(LoginViewService.class, Constants.URL_LOGIN_MODULE);
			String result = srv.login("Lars", "abc12");
			Assert.assertNull(result);
		} catch (Exception e) {
			Assert.fail();
		}
	}
}
