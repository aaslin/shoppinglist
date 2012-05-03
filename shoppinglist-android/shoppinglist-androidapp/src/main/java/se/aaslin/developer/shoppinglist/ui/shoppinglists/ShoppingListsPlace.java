package se.aaslin.developer.shoppinglist.ui.shoppinglists;

import se.aaslin.developer.shoppinglist.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.app.mvp.Place;

public class ShoppingListsPlace extends Place {

	private static final long serialVersionUID = 6084678072636227990L;

	@Override
	protected Class<? extends ActivityPlace<? extends Place>> getActivityClass() {
		return ShoppingListsActivity.class;
	}
}
