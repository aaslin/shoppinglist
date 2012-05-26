package se.aaslin.developer.shoppinglist.android.back.service.impl;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpStatus;

import se.aaslin.developer.shoppinglist.android.app.conf.Urls;
import se.aaslin.developer.shoppinglist.android.app.exception.AuthenticationFailedException;
import se.aaslin.developer.shoppinglist.android.app.exception.HttpException;
import se.aaslin.developer.shoppinglist.android.back.dto.LoginDTO;
import se.aaslin.developer.shoppinglist.android.back.dto.RegistrationDTO;
import se.aaslin.developer.shoppinglist.android.back.http.HttpRequest;
import se.aaslin.developer.shoppinglist.android.back.http.HttpResponse;
import se.aaslin.developer.shoppinglist.android.back.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.android.back.service.LoginService;

import com.google.inject.Inject;

public class LoginServiceImpl implements LoginService {

	@Inject	AuthenticationService authenticationService;

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

	@Override
	public void logout() throws HttpException  {
		try { 
			String authId = authenticationService.getAuthenticationId();
			
			HttpRequest<Void> request = new HttpRequest<Void>(Void.class);
			HttpResponse<Void> response = request.setCookie("auth", authId).doGet(new URI(Urls.URL_REST_LOGOUT));
			
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				return;
			}
			
			throw new HttpException(response.getStatusCode());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void register(String registration) throws HttpException {
		try { 
			String authId = authenticationService.getAuthenticationId();
			RegistrationDTO dto = new RegistrationDTO();
			dto.setRegistration(registration);
			HttpRequest<Void> request = new HttpRequest<Void>(Void.class);
			HttpResponse<Void> response = request.setCookie("auth", authId).doPost(new URI(Urls.URL_REST_REGISTRATION), dto);
			
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				return;
			}
			
			throw new HttpException(response.getStatusCode());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
