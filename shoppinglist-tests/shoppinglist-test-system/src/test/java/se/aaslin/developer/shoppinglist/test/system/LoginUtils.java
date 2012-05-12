package se.aaslin.developer.shoppinglist.test.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

public class LoginUtils {

	private static final String URL_LOGIN = String.format("%s/security_check", Constants.URL);

	/**
	 * @param args
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static CookieManager login() throws IOException, URISyntaxException {
		CookieManager cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(cookieManager);

		String data = new StringBuilder().append(URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode("Lars", "UTF-8"))
				.append("&")
				.append(URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode("abc123", "UTF-8"))
				.toString();
		URL url = new URL(URL_LOGIN);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);

		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		writer.write(data);
		writer.flush();

		flushInputStream(connection);
		
		return cookieManager;

//		ReportE13ServiceAsync srv = (ReportE13ServiceAsync) SyncProxy.newProxyInstance(ReportE13ServiceAsync.class, URL_REPORT_ROOM, cookieManager);
//
//		srv.createReport("2012", "Januari", false, new AsyncCallback<Void>() {
//
//			@Override
//			public void onSuccess(Void result) {
//				System.out.println("Request successfully sent");
//			}
//
//			@Override
//			public void onFailure(Throwable caught) {
//				System.out.println("Request failed");
//			}
//		});

	}

	private static void flushInputStream(HttpURLConnection connection) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		while ((reader.readLine()) != null) {}
	}
}

