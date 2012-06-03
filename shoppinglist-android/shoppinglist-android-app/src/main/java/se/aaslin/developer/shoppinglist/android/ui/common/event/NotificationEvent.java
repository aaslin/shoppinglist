package se.aaslin.developer.shoppinglist.android.ui.common.event;

import se.aaslin.developer.roboeventbus.event.RoboEvent;

public class NotificationEvent extends RoboEvent<NotificationEventHandler>{

	public static final Type<NotificationEventHandler> TYPE = new Type<NotificationEventHandler>();
	

	@Override
	public void dispatch(NotificationEventHandler handler) {
		handler.onNotificationReceived();
	}

	@Override
	public se.aaslin.developer.roboeventbus.event.RoboEvent.Type<NotificationEventHandler> getType() {
		return TYPE;
	}
}
