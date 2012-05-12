package se.aaslin.developer.shoppinglist.android.service.async.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import se.aaslin.developer.shoppinglist.android.conf.Urls;
import se.aaslin.developer.shoppinglist.android.dto.LoginDTO;
import se.aaslin.developer.shoppinglist.android.exception.AuthenticationFailedException;
import se.aaslin.developer.shoppinglist.android.exception.HttpException;
import se.aaslin.developer.shoppinglist.android.service.async.LoginAsyncService;
import se.aaslin.developer.shoppinglist.app.mvp.AsyncCallback;
import android.os.AsyncTask;

import com.thoughtworks.xstream.XStream;

public class LoginAsyncServiceImpl implements LoginAsyncService {

	@Override
	public void login(final String username, final String password, final AsyncCallback<String> callback) {
		AsyncTask<Void, Void, Object> asyncTask = new AsyncTask<Void, Void, Object>() {

			@Override
			protected Object doInBackground(Void... arg0) {
				try {
					return login(username, password);
				} catch (Throwable caugth) {
					return caugth;
				}
			}
			
			@Override
			protected void onPostExecute(Object result) {
				if (result instanceof Throwable) {
					Throwable caught = (Throwable) result;
					if (callback != null) {
						callback.onFailure(caught);
					}
				} else {
					if (callback != null) {
						callback.onSuccess((String) result);
					}
				}
			}
		};
	
		asyncTask.execute();
	}
	
	private String login(String username, String password) throws AuthenticationFailedException, HttpException {
		try { 
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setUsername(username);
			loginDTO.setPassword(password);
			
			XStream stream = new XStream();
			stream.alias("loginDTO", LoginDTO.class);
			String body = stream.toXML(loginDTO);
			
			HttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost();
			request.setURI(new URI(Urls.URL_REST_LOGIN));
			request.setHeader("Content-Type", "application/xml");
			request.setHeader("Accept", "application/xml");
			
			HttpEntity entity = new StringEntity(body);
			request.setEntity(entity);
			
			HttpResponse response = client.execute(request);
		
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return new BufferedReader(new InputStreamReader(response.getEntity().getContent())).readLine();
			} else if(response.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
				throw new AuthenticationFailedException();		
			}
			
			throw new HttpException(response.getStatusLine().getStatusCode());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
