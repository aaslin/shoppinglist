package se.aaslin.developer.shoppinglist.android.back.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.android.back.dto.NotificationDTO;

public interface NotificationServiceAsync {

	void getNotifications(AsyncCallback<List<NotificationDTO>> callback);
}
