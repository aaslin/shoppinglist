package se.aaslin.developer.shoppinglist.security;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class UserSessionUtils {
	
	public static String getAuthCookie(HttpServletRequest request) {
		return getCookie("auth", request);
	}
	
	public static String getCookie(String key, HttpServletRequest request) {
		if (request.getCookies() != null) { 
			for (Cookie cookie : request.getCookies()) {
				if(cookie.getName().equals(key)) {
					return cookie.getValue();
				}
			}
		}
		
		return null;
	}
	
	public static String getCurrentUsername(HttpServletRequest request, ShoppingListSessionManager sessionManager) {
		String cookie = UserSessionUtils.getAuthCookie(request);
		if (cookie == null) {
			return null;
		}
		String username = sessionManager.getSessionUser(UUID.fromString(cookie));
		
		return username;
	}
}
