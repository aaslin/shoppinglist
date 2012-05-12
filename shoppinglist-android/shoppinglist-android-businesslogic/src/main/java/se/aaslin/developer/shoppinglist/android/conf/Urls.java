package se.aaslin.developer.shoppinglist.android.conf;

public class Urls {
	
	public static String DOMAIN = "http://192.168.0.12:8080";
	public static String PATH = "/shoppinglist";
	public static String URL = String.format("%s%s/gwt.shoppinglist/", DOMAIN, PATH);
	public static String LOGIN_URL = String.format("%s%s/gwt.login/", DOMAIN, PATH);
	public static String URL_REST = String.format("%s/rest/", URL);
}
