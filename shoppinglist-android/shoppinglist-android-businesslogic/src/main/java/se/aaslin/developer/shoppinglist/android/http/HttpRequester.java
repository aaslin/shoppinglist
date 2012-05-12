package se.aaslin.developer.shoppinglist.android.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import se.aaslin.developer.shoppinglist.android.conf.Urls;

import android.util.Log;

public abstract class HttpRequester {
	protected static final String BASE_URL = Urls.URL_REST;
	
	protected InputStream makeRequest(String service) {
		return makeRequest(service, new HashMap<String, String>());
	}

	protected InputStream makeRequest(String service, Map<String, String> arguments) {
		String args = extractArguments(arguments);
		try {
			URL url = new URL(new StringBuilder().append(getUri()).append(args).toString());
			URLConnection conn = url.openConnection();
			return conn.getInputStream();
		} catch (MalformedURLException e) {
			Log.e(this.getClass().getName(), e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(this.getClass().getName(), e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	private String extractArguments(Map<String, String> args) {
		StringBuilder sb = new StringBuilder();
		for (String key : args.keySet()) {
			sb.append(key).append("=").append(args.get(key)).append("&");
		}
		sb.deleteCharAt(sb.lastIndexOf("&"));

		return sb.toString();
	}
	
	protected abstract String getUri();
}
