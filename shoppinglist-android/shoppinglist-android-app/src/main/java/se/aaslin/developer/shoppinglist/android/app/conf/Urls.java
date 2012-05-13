package se.aaslin.developer.shoppinglist.android.app.conf;

public class Urls {
	
	public static String DOMAIN = "http://192.168.0.15:8080";
	public static String PATH = "/shoppinglist";
	public static String URL = String.format("%s%s/gwt.shoppinglist/", DOMAIN, PATH);
	public static String LOGIN_URL = String.format("%s%s/gwt.login/", DOMAIN, PATH);
	public static String URL_REST = String.format("%s%s/rest", DOMAIN, PATH);
	public static String URL_REST_LOGIN = String.format("%s/login", URL_REST);
	public static String URL_REST_SHOPPINGLIST = String.format("%s/shoppinglist", URL_REST);
}
