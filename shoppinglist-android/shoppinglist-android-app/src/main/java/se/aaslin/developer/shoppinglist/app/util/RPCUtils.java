package se.aaslin.developer.shoppinglist.app.util;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URL;

import se.aaslin.developer.roboproxy.RoboProxy;
import se.aaslin.developer.shoppinglist.android.conf.Urls;
import se.aaslin.developer.shoppinglist.android.service.AuthenticationService;
import android.content.Context;

public class RPCUtils {

	@SuppressWarnings("unchecked")
	public static <T> T getRPCService(Class<T> clazz, Context context) {
		AuthenticationService authenticationService = InjectionUtils.getInstance(AuthenticationService.class, context);
		String authId = authenticationService.getAuthenticationId();
		CookieManager cookieManager = createCookieManager(authId);
		
		return (T) RoboProxy.newProxyInstance(clazz, Urls.URL, context, cookieManager);
	}
	
	private static CookieManager createCookieManager(String authId) {
		try {
			CookieManager cookieManager = new CookieManager();
			cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			
			HttpCookie cookie = new HttpCookie("auth", authId);
			cookie.setPath(Urls.PATH);
			cookieManager.getCookieStore().add(new URL(Urls.DOMAIN).toURI(), cookie);
		
			return cookieManager;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
