package se.aaslin.developer.shoppinglist.android.ui.shoppinglists;

import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Place;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;

public class NewShoppingListPlace extends Place{

	private static final long serialVersionUID = 8467028510159103467L;

	private final ShoppingListDTO shoppingList;
	
	public NewShoppingListPlace(ShoppingListDTO shoppingList) {
		this.shoppingList = shoppingList;
	}

	@Override
	protected Class<? extends ActivityPlace<? extends Place>> getActivityClass() {
		return NewShoppingListActivity.class;
	}

	public ShoppingListDTO getShoppingList() {
		return shoppingList;
	}
}
