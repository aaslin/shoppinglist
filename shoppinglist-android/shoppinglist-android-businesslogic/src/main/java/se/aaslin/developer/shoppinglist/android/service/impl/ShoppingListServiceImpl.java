package se.aaslin.developer.shoppinglist.android.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import se.aaslin.developer.shoppinglist.android.conf.Urls;
import se.aaslin.developer.shoppinglist.android.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.exception.HttpException;
import se.aaslin.developer.shoppinglist.android.http.HttpRequest;
import se.aaslin.developer.shoppinglist.android.http.HttpResponse;
import se.aaslin.developer.shoppinglist.android.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.android.service.ShoppingListService;

public class ShoppingListServiceImpl implements ShoppingListService {

	@Inject AuthenticationService authenticationService;
	
	@Override
	public List<ShoppingListDTO> getAll() throws HttpException {
		try { 
			String authId = authenticationService.getAuthenticationId();
			
			HttpRequest<ShoppingListDTO> request = new HttpRequest<ShoppingListDTO>(ShoppingListDTO.class, List.class);
			HttpResponse<ShoppingListDTO> response = request.setCookie("auth", authId).doGet(new URI(Urls.URL_REST_SHOPPINGLIST));
			
			if (response.getStatusCode() == HttpStatus.SC_OK) {
				return response.getEntities();
			}
			
			throw new HttpException(response.getStatusCode());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
