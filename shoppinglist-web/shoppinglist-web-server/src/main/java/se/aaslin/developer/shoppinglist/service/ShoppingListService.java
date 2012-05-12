package se.aaslin.developer.shoppinglist.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.shared.exception.NotAuthorizedException;


public interface ShoppingListService {
	
	void create(ShoppingList list, List<String> members, String username) throws NotAuthorizedException;
	
	void update(ShoppingList list, List<String> members, String username) throws NotAuthorizedException;
	
	List<ShoppingList> getAllShoppingListsForUser(String username);
	
	ShoppingList findShoppingListById(int shoppingListId, String username) throws NotAuthorizedException;

	void remove(ShoppingList list, String currentSessionsUsername) throws NotAuthorizedException;
}
