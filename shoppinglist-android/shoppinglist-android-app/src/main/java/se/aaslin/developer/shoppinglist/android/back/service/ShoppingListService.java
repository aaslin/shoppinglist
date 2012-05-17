package se.aaslin.developer.shoppinglist.android.back.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.android.app.exception.HttpException;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.back.dto.UserDTO;

public interface ShoppingListService {
	
	List<ShoppingListDTO> getShoppingLists() throws HttpException;
	
	List<ShoppingItemDTO> getShoppingItems(int shoppingListId) throws HttpException;
	
	void saveShoppingList(ShoppingListDTO dto) throws HttpException;
	
	List<UserDTO> getAllUsers() throws HttpException;
	
	void removeShoppingList(ShoppingListDTO dto) throws HttpException;
}
