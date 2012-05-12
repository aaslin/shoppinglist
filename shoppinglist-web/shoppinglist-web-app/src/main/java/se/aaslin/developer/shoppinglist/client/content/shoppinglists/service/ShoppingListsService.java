package se.aaslin.developer.shoppinglist.client.content.shoppinglists.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.shared.exception.NotAuthorizedException;
import se.aaslin.developer.shoppinglist.shared.exception.ServerSideException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("shoppinglists")
public interface ShoppingListsService extends RemoteService {
	
	/**
	 * Returns a shallow list of the current users shopping lists 
	 * @return
	 */
	List<ShoppingListDTO> getShoppingLists() throws ServerSideException;
	
	List<ShoppingItemDTO> getShoppingItems(int shoppingListId) throws ServerSideException;
	
	/**
	 * Returns a complete shoppingList
	 * @param shoppingListId
	 * @return
	 * @throws NotAuthorizedException 
	 */
	ShoppingListDTO getShoppingList(int shoppingListId) throws ServerSideException;
	
	List<ShoppingListDTO> saveShoppingList(ShoppingListDTO dto) throws ServerSideException;
	
	/**
	 * Updates an existing shopping list. Returns the updated instance of the list.
	 * @param dto
	 * @return
	 * @throws NotAuthorizedException 
	 */
	ShoppingListDTO updateShoppingList(ShoppingListDTO dto) throws ServerSideException;
	
	List<ShoppingItemDTO> saveShoppingItems(int shoppingListId, List<ShoppingItemDTO> itemDTOs) throws ServerSideException;
	
	List<ShoppingListDTO> removeShoppingList(ShoppingListDTO dto) throws ServerSideException;

	List<ShoppingItemDTO> removeShoppingItem(ShoppingItemDTO itemDTO) throws ServerSideException;
	
	List<String> getAllUsers() throws ServerSideException;
}
