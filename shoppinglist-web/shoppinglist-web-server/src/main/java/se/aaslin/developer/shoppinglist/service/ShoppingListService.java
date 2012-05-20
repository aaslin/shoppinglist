package se.aaslin.developer.shoppinglist.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.shared.exception.NotAuthorizedException;
import se.aaslin.developer.shoppinglist.shared.exception.NotFoundException;


public interface ShoppingListService {
	
	void create(ShoppingList list, List<String> members, String username) throws NotAuthorizedException;
	
	void update(ShoppingList list, List<String> members, String username) throws NotAuthorizedException, NotFoundException;
	
	List<ShoppingList> getAllShoppingListsForUser(String username);
	
	ShoppingList findShoppingListById(int shoppingListId, String username) throws NotAuthorizedException, NotFoundException;

	void remove(ShoppingList list, String currentSessionsUsername) throws NotAuthorizedException;
}
