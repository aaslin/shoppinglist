package se.aaslin.developer.shoppinglist.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.entity.User;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

public interface ShoppingListService {
	
	void save(ShoppingListDTO shoppingList);
	
	List<ShoppingListDTO> getAll();
	
	ShoppingListDTO findById(int id);
	
	void remove(int shoppingList);
	
	List<ShoppingListDTO> getAllShoppingListsForUser(User user);
}
