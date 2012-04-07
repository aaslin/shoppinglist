package se.aaslin.developer.shoppinglist.client.content.shoppinglists.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("shoppinglists")
public interface ShoppingListGridService extends RemoteService{
	
	List<ShoppingListDTO> getShoppingLists();
	
	List<ShoppingItemDTO> getShoppingItems(int shoppingListId);
}
