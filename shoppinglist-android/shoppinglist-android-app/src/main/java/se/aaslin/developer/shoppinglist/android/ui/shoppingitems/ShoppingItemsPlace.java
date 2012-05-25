package se.aaslin.developer.shoppinglist.android.ui.shoppingitems;

import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Place;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.ui.common.Notification;

public class ShoppingItemsPlace extends Place {
	
	private static final long serialVersionUID = 4670475589616053842L;

	private final ShoppingListDTO shoppingListDTO;
	private final Notification notification;
	
	public ShoppingItemsPlace(ShoppingListDTO shoppingListDTO) {
		this(shoppingListDTO, null);
	}
	
	public ShoppingItemsPlace(ShoppingListDTO shoppingListDTO, Notification notification) {
		this.shoppingListDTO = shoppingListDTO;
		this.notification = notification;
	}

	public Notification getNotification() {
		return notification;
	}

	@Override
	protected Class<? extends ActivityPlace<? extends Place>> getActivityClass() {
		return ShoppingItemsActivity.class;
	}

	public ShoppingListDTO getShoppingListDTO() {
		return shoppingListDTO;
	}
}
