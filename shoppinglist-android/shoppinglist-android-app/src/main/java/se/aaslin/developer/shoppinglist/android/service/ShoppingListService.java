package se.aaslin.developer.shoppinglist.android.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.android.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.exception.HttpException;

public interface ShoppingListService {
	
	List<ShoppingListDTO> getAll() throws HttpException;
}
