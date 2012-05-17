package se.aaslin.developer.shoppinglist.android.back.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.back.dto.UserDTO;

public interface ShoppingListServiceAsync {
	
	void getShoppingLists(AsyncCallback<List<ShoppingListDTO>> callback);

	void getShoppingItems(int shoppingListId, AsyncCallback<List<ShoppingItemDTO>> callback);
	
	void saveShoppingList(ShoppingListDTO dto, AsyncCallback<Void> callback);
	
	void getAllUsers(AsyncCallback<List<UserDTO>> callback);
	
	void removeShoppingList(ShoppingListDTO dto, AsyncCallback<Void> callback);
}
