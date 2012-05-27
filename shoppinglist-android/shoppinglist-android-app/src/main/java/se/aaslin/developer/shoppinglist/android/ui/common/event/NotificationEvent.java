package se.aaslin.developer.shoppinglist.android.ui.common.event;

import se.aaslin.developer.roboeventbus.event.RoboEvent;
import se.aaslin.developer.shoppinglist.android.ui.common.Notification;

public class NotificationEvent extends RoboEvent<NotificationEventHandler>{

	public static final Type<NotificationEventHandler> TYPE = new Type<NotificationEventHandler>();
	
	private final Notification notification;
	
	public NotificationEvent(Notification notification) {
		this.notification = notification;
	}

	@Override
	public void dispatch(NotificationEventHandler handler) {
		handler.onNotificationReceived(notification);
	}

	@Override
	public se.aaslin.developer.roboeventbus.event.RoboEvent.Type<NotificationEventHandler> getType() {
		return TYPE;
	}

	public Notification getNotification() {
		return notification;
	}
}
