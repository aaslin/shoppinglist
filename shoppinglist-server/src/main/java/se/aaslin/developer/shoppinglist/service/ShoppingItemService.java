package se.aaslin.developer.shoppinglist.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;

public interface ShoppingItemService {
	
	List<ShoppingItemDTO> getAllShoppingListItems(int shoppingListId);

	void saveItemsToShoppingList(Integer shoppingListId, List<ShoppingItemDTO> items);

	void remove(ShoppingItemDTO itemDTO);
}

