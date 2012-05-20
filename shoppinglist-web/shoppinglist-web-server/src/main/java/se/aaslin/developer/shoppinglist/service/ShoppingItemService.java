package se.aaslin.developer.shoppinglist.service;

import se.aaslin.developer.shoppinglist.entity.ShoppingItem;
import se.aaslin.developer.shoppinglist.shared.exception.NotAuthorizedException;
import se.aaslin.developer.shoppinglist.shared.exception.NotFoundException;

public interface ShoppingItemService {
	
	void update(ShoppingItem item, String username) throws NotAuthorizedException, NotFoundException;
	
	void remove(ShoppingItem item, String username) throws NotAuthorizedException;

	void create(int shoppingListId, ShoppingItem item, String username) throws NotAuthorizedException;	
}

