package se.aaslin.developer.shoppinglist.app.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URL;

import se.aaslin.developer.shoppinglist.android.conf.Urls;
import se.aaslin.developer.shoppinglist.android.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.app.ShoppingListApplication;
import android.content.Context;

public class RPCUtils {
//	public static class RPCProxyHandler implements InvocationHandler {
//		
//		Context context;
//		
//		@Override
//		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//			((ShoppingListApplication) context.getApplicationContext()).getInjector().getInstance(type)
//			return null;
//		}
//	}
	
//	@SuppressWarnings("unchecked")
//	public static <T> T getRPCService(Class<T> clazz, Context context) {
//		AuthenticationService authenticationService = InjectionUtils.getInstance(AuthenticationService.class, context);
//		String authId = authenticationService.getAuthenticationId();
//		CookieManager cookieManager = createCookieManager(authId);
//
//		return (T) Proxy.newProxyInstance(context.getClassLoader(), new Class[] {clazz}, new RPCProxyHandler());
//	}
	
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
