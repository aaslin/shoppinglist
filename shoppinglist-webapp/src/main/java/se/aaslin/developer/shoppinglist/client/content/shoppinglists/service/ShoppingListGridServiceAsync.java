package se.aaslin.developer.shoppinglist.client.content.shoppinglists.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ShoppingListGridServiceAsync {

	void getShoppingItems(int shoppingListID, AsyncCallback<List<ShoppingItemDTO>> callback);

	void getShoppingLists(AsyncCallback<List<ShoppingListDTO>> callback);

	void addShoppingList(ShoppingListDTO dto, AsyncCallback<List<ShoppingListDTO>> callback);

	void addShoppingItem(ShoppingItemDTO itemDTO, AsyncCallback<List<ShoppingItemDTO>> callback);

	void removeShoppingItem(ShoppingItemDTO itemDTO, AsyncCallback<List<ShoppingItemDTO>> callback);

	void updateShoppingItem(ShoppingItemDTO itemDTO, AsyncCallback<List<ShoppingItemDTO>> callback);

	void removeShoppingList(ShoppingListDTO dto, AsyncCallback<List<ShoppingListDTO>> callback);

	void updateShoppingList(ShoppingListDTO dto, AsyncCallback<List<ShoppingListDTO>> callback);

}
