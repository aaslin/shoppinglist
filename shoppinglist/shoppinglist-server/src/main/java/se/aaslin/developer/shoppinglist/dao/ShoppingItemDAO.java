package se.aaslin.developer.shoppinglist.dao;

import java.util.List;

import se.aaslin.developer.shoppinglist.entity.ShoppingItem;

public interface ShoppingItemDAO extends GenericDAO<Integer, ShoppingItem>{

	List<ShoppingItem> getShoppingListItems(int shoppingListId);
}
