package se.aaslin.developer.shoppinglist.android.back.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpStatus;

import se.aaslin.developer.shoppinglist.android.app.conf.Urls;
import se.aaslin.developer.shoppinglist.android.app.exception.HttpException;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.back.dto.UserDTO;
import se.aaslin.developer.shoppinglist.android.back.http.HttpRequest;
import se.aaslin.developer.shoppinglist.android.back.http.HttpRequest.Alias;
import se.aaslin.developer.shoppinglist.android.back.http.HttpResponse;
import se.aaslin.developer.shoppinglist.android.back.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.android.back.service.ShoppingListService;

import com.google.inject.Inject;

public class ShoppingListServiceImpl implements ShoppingListService {

	@Inject	AuthenticationService authenticationService;

	@Override
	public List<ShoppingListDTO> getShoppingLists() throws HttpException {
		try {
			String authId = authenticationService.getAuthenticationId();

			HttpRequest<ShoppingListDTO> request = new HttpRequest<ShoppingListDTO>(ShoppingListDTO.class, List.class);
			HttpResponse<ShoppingListDTO> response = request.setCookie("auth", authId).doGet(new URI(Urls.URL_REST_SHOPPINGLIST),
					new Alias("members", List.class), new Alias("member", String.class));

			if (response.getStatusCode() == HttpStatus.SC_OK) {
				return response.getEntities();
			}

			throw new HttpException(response.getStatusCode());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<ShoppingItemDTO> getShoppingItems(int shoppingListId) throws HttpException {
		try {
			String authId = authenticationService.getAuthenticationId();

			HttpRequest<ShoppingItemDTO> request = new HttpRequest<ShoppingItemDTO>(ShoppingItemDTO.class, List.class);
			HttpResponse<ShoppingItemDTO> response = request.setCookie("auth", authId).doGet(
					new URI(String.format("%s/%s", Urls.URL_REST_SHOPPINGITEM, shoppingListId)));

			if (response.getStatusCode() == HttpStatus.SC_OK) {
				return response.getEntities();
			}

			throw new HttpException(response.getStatusCode());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void saveShoppingList(ShoppingListDTO dto) throws HttpException {
		try {
			String authId = authenticationService.getAuthenticationId();

			HttpRequest<Void> request = new HttpRequest<Void>(Void.class);
			HttpResponse<Void> response = request.setCookie("auth", authId).doPost(
					new URI(Urls.URL_REST_SHOPPINGLIST), dto, new Alias("members", List.class),
					new Alias("member", String.class));

			if (response.getStatusCode() == HttpStatus.SC_OK) {
				return;
			}

			throw new HttpException(response.getStatusCode());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserDTO> getAllUsers() throws HttpException {
		try {
			String authId = authenticationService.getAuthenticationId();

			HttpRequest<UserDTO> request = new HttpRequest<UserDTO>(UserDTO.class, List.class);
			HttpResponse<UserDTO> response = request.setCookie("auth", authId).doGet(new URI(Urls.URL_REST_USER));

			if (response.getStatusCode() == HttpStatus.SC_OK) {
				return response.getEntities();
			}

			throw new HttpException(response.getStatusCode());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void removeShoppingList(ShoppingListDTO dto) throws HttpException {
		try {
			String authId = authenticationService.getAuthenticationId();

			HttpRequest<Void> request = new HttpRequest<Void>(Void.class);
			HttpResponse<Void> response = request.setCookie("auth", authId).doDelete(new URI(String.format("%s/%s", Urls.URL_REST_SHOPPINGLIST, dto.getID())));

			if (response.getStatusCode() == HttpStatus.SC_OK) {
				return;
			}

			throw new HttpException(response.getStatusCode());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void saveShoppingItem(int shoppingListId, ShoppingItemDTO dto) throws HttpException {
		try {
			String authId = authenticationService.getAuthenticationId();

			HttpRequest<Void> request = new HttpRequest<Void>(Void.class);
			HttpResponse<Void> response = request.setCookie("auth", authId).doPost(
					new URI(String.format("%s/%s", Urls.URL_REST_SHOPPINGITEM, shoppingListId)), dto);

			if (response.getStatusCode() == HttpStatus.SC_OK) {
				return;
			}

			throw new HttpException(response.getStatusCode());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void removeShoppingItem(ShoppingItemDTO dto) throws HttpException {
		try {
			String authId = authenticationService.getAuthenticationId();

			HttpRequest<Void> request = new HttpRequest<Void>(Void.class);
			HttpResponse<Void> response = request.setCookie("auth", authId).doDelete(new URI(String.format("%s/%s", Urls.URL_REST_SHOPPINGITEM, dto.getId())));

			if (response.getStatusCode() == HttpStatus.SC_OK) {
				return;
			}

			throw new HttpException(response.getStatusCode());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
