package org.aaslin.developer.webtest.service;

import java.util.List;

import org.aaslin.developer.webtest.dao.ShoppingListDao;
import org.aaslin.developer.webtest.entity.ShoppingList;
import org.aaslin.developer.webtest.gwt.client.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service("shoppingList")
public class ShoppingListServiceImpl extends RemoteServiceServlet implements ShoppingListService{

	private static final long serialVersionUID = -4526714440078975823L;

	@Autowired
	private ShoppingListDao shoppingListDao;
	
	@Override
	public List<ShoppingList> getAll() {
		return shoppingListDao.getAll();
	}

	@Override
	public ShoppingList findById(long id) {
		return shoppingListDao.findById(id);
	}

	@Override
	public void update(ShoppingList shoppingList) {
		shoppingListDao.update(shoppingList);
	}

	@Override
	public void remove(long id) {
		shoppingListDao.remove(id);
	}

}
