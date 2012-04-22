package se.aaslin.developer.shoppinglist.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

public interface ShoppingListsViewService {
	
	void save(ShoppingListDTO shoppingList, String username);

	List<ShoppingListDTO> getAll();
	
	ShoppingListDTO findById(int id);
	
	void remove(int shoppingList);
	
	List<ShoppingListDTO> getOwnedShoppingListsForUser(String username);
	
	List<ShoppingListDTO> getAllShoppingListsForUser(String username);
	
	List<ShoppingItemDTO> getAllShoppingListItems(int shoppingListId);

	void saveItemsToShoppingList(Integer shoppingListId, List<ShoppingItemDTO> items);

	void remove(ShoppingItemDTO itemDTO);

	List<String> getAllUsers();
}
