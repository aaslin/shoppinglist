package se.aaslin.developer.shoppinglist.android.ui.shoppingitems;

import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Place;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;

public class EditShoppingItemPlace extends Place{

	private static final long serialVersionUID = 9041790023796399251L;

	private final ShoppingListDTO shoppingListDTO;
	private final ShoppingItemDTO shoppingItem;

	public EditShoppingItemPlace(ShoppingListDTO shoppingListDTO, ShoppingItemDTO shoppingItem) {
		this.shoppingListDTO = shoppingListDTO;
		this.shoppingItem = shoppingItem;
	}

	@Override
	protected Class<? extends ActivityPlace<? extends Place>> getActivityClass() {
		return EditShoppingItemActivity.class;
	}

	public ShoppingItemDTO getShoppingItem() {
		return shoppingItem;
	}

	public ShoppingListDTO getShoppingListDTO() {
		return shoppingListDTO;
	}
}
