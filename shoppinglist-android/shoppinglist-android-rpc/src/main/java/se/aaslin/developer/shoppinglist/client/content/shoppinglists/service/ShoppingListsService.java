package se.aaslin.developer.shoppinglist.client.content.shoppinglists.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.shared.exception.NotAuthorizedException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("shoppinglists")
public interface ShoppingListsService extends RemoteService {
	
	/**
	 * Returns a shallow list of the current users shopping lists 
	 * @return
	 */
	List<ShoppingListDTO> getShoppingLists();
	
	List<ShoppingItemDTO> getShoppingItems(int shoppingListId) throws NotAuthorizedException;
	
	/**
	 * Returns a complete shoppingList
	 * @param shoppingListId
	 * @return
	 * @throws NotAuthorizedException 
	 */
	ShoppingListDTO getShoppingList(int shoppingListId) throws NotAuthorizedException;
	
	List<ShoppingListDTO> saveShoppingList(ShoppingListDTO dto) throws NotAuthorizedException;
	
	/**
	 * Updates an existing shopping list. Returns the updated instance of the list.
	 * @param dto
	 * @return
	 * @throws NotAuthorizedException 
	 */
	ShoppingListDTO updateShoppingList(ShoppingListDTO dto) throws NotAuthorizedException;
	
	List<ShoppingItemDTO> saveShoppingItems(int shoppingListId, List<ShoppingItemDTO> itemDTOs) throws NotAuthorizedException;
	
	List<ShoppingListDTO> removeShoppingList(ShoppingListDTO dto) throws NotAuthorizedException;

	List<ShoppingItemDTO> removeShoppingItem(ShoppingItemDTO itemDTO) throws NotAuthorizedException;
	
	List<String> getAllUsers();
}
