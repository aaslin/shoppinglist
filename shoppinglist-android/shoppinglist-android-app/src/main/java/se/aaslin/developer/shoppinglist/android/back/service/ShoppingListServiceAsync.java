package se.aaslin.developer.shoppinglist.android.back.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;

public interface ShoppingListServiceAsync {
	
	void getShoppingLists(AsyncCallback<List<ShoppingListDTO>> callback);

	void getShoppingItems(int shoppingListId, AsyncCallback<List<ShoppingItemDTO>> callback);
}
