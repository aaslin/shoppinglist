package se.aaslin.developer.shoppinglist.android.app.conf;

public class Urls {

	public static final String DOMAIN = "http://192.168.0.15:8080";
	// public static final String DOMAIN = "http://ll.homenet.org:8080";
	public static final String PATH = "/shoppinglist";
	public static final String URL = String.format("%s%s/gwt.shoppinglist/", DOMAIN, PATH);
	public static final String LOGIN_URL = String.format("%s%s/gwt.login/", DOMAIN, PATH);
	public static final String URL_REST = String.format("%s%s/rest", DOMAIN, PATH);
	public static final String URL_REST_LOGIN = String.format("%s/login", URL_REST);
	public static final String URL_REST_LOGOUT = String.format("%s/logout", URL_REST);
	public static final String URL_REST_SHOPPINGLIST = String.format("%s/shoppinglist", URL_REST);
	public static final String URL_REST_SHOPPINGITEM = String.format("%s/shoppingitem", URL_REST);
	public static final String URL_REST_USER = String.format("%s/user", URL_REST);
	public static final String URL_REST_REGISTRATION = String.format("%s/registration", URL_REST);
	public static final String URL_REST_NOTIFICATION = String.format("%s/notification", URL_REST);

	public static String C2DM_EMAIL = "listyourlife@gmail.com";
}