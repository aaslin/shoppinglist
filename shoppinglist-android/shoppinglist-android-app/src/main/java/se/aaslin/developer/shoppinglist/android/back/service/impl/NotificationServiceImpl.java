package se.aaslin.developer.shoppinglist.android.back.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpStatus;

import se.aaslin.developer.shoppinglist.android.app.conf.Urls;
import se.aaslin.developer.shoppinglist.android.app.exception.HttpException;
import se.aaslin.developer.shoppinglist.android.back.dto.NotificationDTO;
import se.aaslin.developer.shoppinglist.android.back.http.HttpRequest;
import se.aaslin.developer.shoppinglist.android.back.http.HttpResponse;
import se.aaslin.developer.shoppinglist.android.back.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.android.back.service.NotificationService;

import com.google.inject.Inject;

public class NotificationServiceImpl implements NotificationService {

	@Inject	AuthenticationService authenticationService;
	
	@Override
	public List<NotificationDTO> getNotifications() throws HttpException {
		try {
			String authId = authenticationService.getAuthenticationId();

			HttpRequest<NotificationDTO> request = new HttpRequest<NotificationDTO>(NotificationDTO.class, List.class);
			HttpResponse<NotificationDTO> response = request.setCookie("auth", authId).doGet(new URI(Urls.URL_REST_NOTIFICATION));

			if (response.getStatusCode() == HttpStatus.SC_OK) {
				return response.getEntities();
			}

			throw new HttpException(response.getStatusCode());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
