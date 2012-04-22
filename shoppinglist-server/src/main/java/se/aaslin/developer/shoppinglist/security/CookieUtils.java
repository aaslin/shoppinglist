package se.aaslin.developer.shoppinglist.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtils {
	
	public static String getAuthCookie(HttpServletRequest request) {
		return getCookie("auth", request);
	}
	
	public static String getCookie(String key, HttpServletRequest request) {
		for (Cookie cookie : request.getCookies()) {
			if(cookie.getName().equals(key)) {
				return cookie.getValue();
			}
		}
		
		return null;
	}
}
