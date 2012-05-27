package se.aaslin.developer.shoppinglist.android.ui.common.event;

import se.aaslin.developer.roboeventbus.event.RoboEventHandler;
import se.aaslin.developer.shoppinglist.android.ui.common.Notification;

public interface NotificationEventHandler extends RoboEventHandler {
	
	void onNotificationReceived(Notification notification);
}
