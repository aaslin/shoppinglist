package se.aaslin.developer.shoppinglist.service.notification;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import se.aaslin.developer.shoppinglist.service.C2DMService;

@Service
public class C2DMServiceImpl implements C2DMService {
	
	private static final Logger log = Logger.getLogger(C2DMServiceImpl.class);
	private static final String PARAM_REGISTRATION_ID = "registration_id";
	private static final String PARAM_COLLAPSE_KEY = "collapse_key";
	private static final String UTF8 = "UTF-8";

	@Override
	public int sendMessage(String registrationId, String message) {
		try {
			StringBuilder postDataBuilder = new StringBuilder();
			postDataBuilder.append(PARAM_REGISTRATION_ID).append("=").append(registrationId);
			postDataBuilder.append("&").append(PARAM_COLLAPSE_KEY).append("=").append("0");
			postDataBuilder.append("&").append("data.payload").append("=").append(URLEncoder.encode(message, UTF8));
	
			byte[] postData = postDataBuilder.toString().getBytes(UTF8);
	
			URL url = new URL("https://android.clients.google.com/c2dm/send");
			HttpsURLConnection.setDefaultHostnameVerifier(new CustomizedHostnameVerifier());
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
			conn.setRequestProperty("Authorization", "GoogleLogin auth=" + AUTH);
	
			OutputStream out = conn.getOutputStream();
			out.write(postData);
			out.close();
	
			int responseCode = conn.getResponseCode();
			return responseCode;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			
			return -1;
		}
	}
	
	private class CustomizedHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
}
