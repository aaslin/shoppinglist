package se.aaslin.developer.shoppinglist.client.content.shoppinglists.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("shoppinglists")
public interface ShoppingListsService extends RemoteService{
	
	/**
	 * Returns a shallow list of the current users shopping lists 
	 * @return
	 */
	List<ShoppingListDTO> getShoppingLists();
	
	List<ShoppingItemDTO> getShoppingItems(int shoppingListId);
	
	/**
	 * Returns a complete shoppingList
	 * @param shoppingListId
	 * @return
	 */
	ShoppingListDTO getShoppingList(int shoppingListId);
	
	List<ShoppingListDTO> saveShoppingList(ShoppingListDTO dto);
	
	/**
	 * Updates an existing shopping list. Returns the updated instance of the list.
	 * @param dto
	 * @return
	 */
	ShoppingListDTO updateShoppingList(ShoppingListDTO dto);
	
	List<ShoppingItemDTO> saveShoppingItems(int shoppingListId, List<ShoppingItemDTO> itemDTOs);
	
	List<ShoppingListDTO> removeShoppingList(ShoppingListDTO dto);

	List<ShoppingItemDTO> removeShoppingItem(ShoppingItemDTO itemDTO);
	
	List<String> getAllUsers();
}
