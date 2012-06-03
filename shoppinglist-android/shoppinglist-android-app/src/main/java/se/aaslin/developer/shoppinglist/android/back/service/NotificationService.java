package se.aaslin.developer.shoppinglist.android.back.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.android.app.exception.HttpException;
import se.aaslin.developer.shoppinglist.android.back.dto.NotificationDTO;

public interface NotificationService {

	List<NotificationDTO> getNotifications() throws HttpException;
}
