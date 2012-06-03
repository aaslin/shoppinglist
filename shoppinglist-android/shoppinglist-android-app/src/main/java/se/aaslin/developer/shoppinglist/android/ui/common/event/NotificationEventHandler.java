package se.aaslin.developer.shoppinglist.android.ui.common.event;

import se.aaslin.developer.roboeventbus.event.RoboEventHandler;

public interface NotificationEventHandler extends RoboEventHandler {
	
	void onNotificationReceived();
}
