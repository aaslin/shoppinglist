package se.aaslin.developer.shoppinglist.android.ui.shoppinglists;

import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Place;
import se.aaslin.developer.shoppinglist.android.ui.common.Notification;

public class ShoppingListsPlace extends Place {
	
	private static final long serialVersionUID = 6084678072636227990L;

	private final Notification notification;
	
	public ShoppingListsPlace() {
		this(null);
	}

	public ShoppingListsPlace(Notification notification) {
		this.notification = notification;
	}

	public Notification getNotification() {
		return notification;
	}

	@Override
	protected Class<? extends ActivityPlace<? extends Place>> getActivityClass() {
		return ShoppingListsActivity.class;
	}
}
