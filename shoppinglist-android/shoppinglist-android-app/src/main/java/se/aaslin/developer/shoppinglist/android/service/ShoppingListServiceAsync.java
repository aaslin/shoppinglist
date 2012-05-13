package se.aaslin.developer.shoppinglist.android.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.android.dto.ShoppingListDTO;

public interface ShoppingListServiceAsync {
	
	void getAll(AsyncCallback<List<ShoppingListDTO>> callback);
}
