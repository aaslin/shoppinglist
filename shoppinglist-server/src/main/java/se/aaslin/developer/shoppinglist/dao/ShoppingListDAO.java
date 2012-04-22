package se.aaslin.developer.shoppinglist.dao;

import java.util.List;

import se.aaslin.developer.shoppinglist.entity.ShoppingList;

public interface ShoppingListDAO extends GenericDAO<Integer, ShoppingList>{
	
	/**
	 * Return all shopping lists owned by the provided user
	 * @param userId
	 * @return
	 */
	List<ShoppingList> getOwnedShoppingListsForUser(int userId);
	
	/**
	 * Return all shopping lists which the provided user is a member of
	 * @param userId
	 * @return
	 */
	List<ShoppingList> getShoppingListsForUser(int userId);
}
