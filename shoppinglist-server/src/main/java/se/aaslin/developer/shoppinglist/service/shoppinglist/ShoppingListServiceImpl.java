package se.aaslin.developer.shoppinglist.service.shoppinglist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.aaslin.developer.shoppinglist.dao.ShoppingListDAO;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.service.ShoppingListService;

@Service("shoppingList")
public class ShoppingListServiceImpl implements ShoppingListService{

	@Autowired ShoppingListDAO shoppingListDao;
	
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
