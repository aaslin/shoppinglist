package se.aaslin.developer.shoppinglist.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.dto.ShoppingListDTO;

public interface ShoppingListService{
	void add(ShoppingListDTO shoppingList);
	
	List<ShoppingListDTO> getAll();
	
	ShoppingListDTO findById(int id);
	
	void update(ShoppingListDTO shoppingList);
	
	void remove(int shoppingList);
}
