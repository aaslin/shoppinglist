package se.aaslin.developer.shoppinglist.android.back.service.impl;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpStatus;

import se.aaslin.developer.shoppinglist.android.app.conf.Urls;
import se.aaslin.developer.shoppinglist.android.app.exception.AuthenticationFailedException;
import se.aaslin.developer.shoppinglist.android.app.exception.HttpException;
import se.aaslin.developer.shoppinglist.android.back.dto.LoginDTO;
import se.aaslin.developer.shoppinglist.android.back.http.HttpRequest;
import se.aaslin.developer.shoppinglist.android.back.http.HttpResponse;
import se.aaslin.developer.shoppinglist.android.back.service.LoginService;

public class LoginServiceImpl implements LoginService {

	@Override
	public String login(String username, String password) throws AuthenticationFailedException, HttpException {
		try { 
			HttpRequest<String> request = new HttpRequest<String>(String.class);
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setUsername(username);
			loginDTO.setPassword(password);
			HttpResponse<String> response = request.doPost(new URI(Urls.URL_REST_LOGIN), loginDTO);
			
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				return response.getSingleEntity();
			} else if(response.getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
				throw new AuthenticationFailedException();	
			}
			
			throw new HttpException(response.getStatusCode());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
