package se.aaslin.developer.shoppinglist.ui.shoppinglists;

import android.os.Bundle;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.app.mvp.ActivityPlace;

public class ShoppingListsActivity extends ActivityPlace<ShoppingListsPlace> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
	}
}
