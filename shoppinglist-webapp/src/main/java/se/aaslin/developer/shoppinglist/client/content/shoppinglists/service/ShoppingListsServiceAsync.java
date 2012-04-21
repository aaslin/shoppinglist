package se.aaslin.developer.shoppinglist.client.content.shoppinglists.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ShoppingListsServiceAsync {

	void getShoppingItems(int shoppingListID, AsyncCallback<List<ShoppingItemDTO>> callback);

	void getShoppingLists(AsyncCallback<List<ShoppingListDTO>> callback);

	void saveShoppingList(ShoppingListDTO dto, AsyncCallback<List<ShoppingListDTO>> callback);

	void saveShoppingItems(int shoppingListId, List<ShoppingItemDTO> itemDTOs, AsyncCallback<List<ShoppingItemDTO>> callback);

	void removeShoppingItem(ShoppingItemDTO itemDTO, AsyncCallback<List<ShoppingItemDTO>> callback);

	void removeShoppingList(ShoppingListDTO dto, AsyncCallback<List<ShoppingListDTO>> callback);

	void getAllUsers(AsyncCallback<List<String>> callback);

	void getShoppingList(int shoppingListId, AsyncCallback<ShoppingListDTO> callback);

	void updateShoppingList(ShoppingListDTO dto, AsyncCallback<ShoppingListDTO> callback);
}
