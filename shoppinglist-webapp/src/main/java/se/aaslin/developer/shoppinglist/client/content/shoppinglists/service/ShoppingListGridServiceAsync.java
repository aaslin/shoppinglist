package se.aaslin.developer.shoppinglist.client.content.shoppinglists.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ShoppingListGridServiceAsync {

	void getShoppingItems(int shoppingListID, AsyncCallback<List<ShoppingItemDTO>> callback);

	void getShoppingLists(AsyncCallback<List<ShoppingListDTO>> callback);

}
