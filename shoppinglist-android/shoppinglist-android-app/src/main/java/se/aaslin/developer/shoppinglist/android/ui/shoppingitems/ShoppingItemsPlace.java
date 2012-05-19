package se.aaslin.developer.shoppinglist.android.ui.shoppingitems;

import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Place;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;

public class ShoppingItemsPlace extends Place {
	
	private static final long serialVersionUID = 4670475589616053842L;

	private final ShoppingListDTO shoppingListDTO;
	
	public ShoppingItemsPlace(ShoppingListDTO shoppingListDTO) {
		this.shoppingListDTO = shoppingListDTO;
	}

	@Override
	protected Class<? extends ActivityPlace<? extends Place>> getActivityClass() {
		return ShoppingItemsActivity.class;
	}

	public ShoppingListDTO getShoppingListDTO() {
		return shoppingListDTO;
	}
}
