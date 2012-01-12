package org.aaslin.developer.webtest.gwt.client.service;

import java.util.List;

import org.aaslin.developer.webtest.entity.ShoppingList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gwt/shoppingList")
public interface ShoppingListService extends RemoteService{
	
	public List<ShoppingList> getAll();
	public ShoppingList findById(long id);
	public void update(ShoppingList shoppingList);
	public void remove(long id);
}
