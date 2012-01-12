package org.aaslin.developer.webtest.gwt.client.service;

import java.util.List;

import org.aaslin.developer.webtest.entity.ShoppingList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ShoppingListServiceAsync {

	public void getAll(AsyncCallback<List<ShoppingList>> callback);
	public void findById(long id, AsyncCallback<ShoppingList> callback);
	public void update(ShoppingList shoppingList, AsyncCallback<Void> callback);
	public void remove(long id, AsyncCallback<Void> callback);
}
