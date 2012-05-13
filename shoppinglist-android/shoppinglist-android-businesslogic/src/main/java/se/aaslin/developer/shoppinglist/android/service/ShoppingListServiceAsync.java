package se.aaslin.developer.shoppinglist.android.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.android.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.app.mvp.AsyncCallback;

public interface ShoppingListServiceAsync {
	
	void getAll(AsyncCallback<List<ShoppingListDTO>> callback);
}
