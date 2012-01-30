package se.aaslin.developer.shoppinglist.dao;

import java.util.List;

import se.aaslin.developer.shoppinglist.entity.ShoppingList;

public interface ShoppingListDAO extends GenericDAO<Integer, ShoppingList>{
	
	List<ShoppingList> getShoppingListsForUser(int userId);
}
