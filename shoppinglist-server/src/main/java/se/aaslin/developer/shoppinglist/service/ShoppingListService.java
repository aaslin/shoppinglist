package se.aaslin.developer.shoppinglist.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.entity.ShoppingList;

public interface ShoppingListService{
	List<ShoppingList> getAll();
	
	ShoppingList findById(long id);
	
	void update(ShoppingList shoppingList);
	
	void remove(long id);
}
