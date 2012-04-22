package se.aaslin.developer.shoppinglist.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.exception.NotAuthorizedException;


public interface ShoppingListService {
	
	List<ShoppingList> getAllShoppingListsForUser(String username);
	
	ShoppingList findShoppingListById(int shoppingListId, String username) throws NotAuthorizedException;
}
