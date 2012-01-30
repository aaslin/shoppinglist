package se.aaslin.developer.shoppinglist.client.shoppinglist.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("shoppinglist")
public interface ShoppingListGridService extends RemoteService{
	
	List<ShoppingListDTO> getShoppingLists(int userId);
	
	List<ShoppingItemDTO> getShoppingItems(int shoppingListId);
}
