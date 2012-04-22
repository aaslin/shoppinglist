package se.aaslin.developer.shoppinglist.service.shoppinglist;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.aaslin.developer.shoppinglist.dao.ShoppingItemDAO;
import se.aaslin.developer.shoppinglist.dao.ShoppingListDAO;
import se.aaslin.developer.shoppinglist.entity.ShoppingItem;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.entity.TimeStamp;
import se.aaslin.developer.shoppinglist.entity.User;
import se.aaslin.developer.shoppinglist.exception.NotAuthorizedException;
import se.aaslin.developer.shoppinglist.service.ShoppingItemService;

@Service
@Transactional
public class ShoppingItemServiceImpl implements ShoppingItemService{

	@Autowired ShoppingListDAO shoppingListDAO;
	@Autowired ShoppingItemDAO shoppingItemDAO;

	@Override
	public void remove(ShoppingItem item, String username) throws NotAuthorizedException {
		item = validateList(shoppingItemDAO.findById(item.getId()), username);
		
		item.getShoppingList().getItems().remove(item);
		shoppingItemDAO.delete(item);
	}
	
	@Override
	public void update(ShoppingItem item, String username) throws NotAuthorizedException {
		ShoppingItem managedItem = shoppingItemDAO.findById(item.getId());
		managedItem = validateList(managedItem, username);
		
		managedItem.setName(item.getName());
		managedItem.setAmount(item.getAmount());
		managedItem.setComment(item.getComment());

		Date date = Calendar.getInstance().getTime();
		item.getTimeStamp().setModified(date);

		shoppingItemDAO.update(item);
	}

	@Override
	public void create(int shoppingListId, ShoppingItem item, String username) throws NotAuthorizedException {
		ShoppingList list = shoppingListDAO.findById(shoppingListId);
		if (list == null) {
			return;
		}
		item.setShoppingList(list);
		item = validateList(item, username);
		
		Date date = Calendar.getInstance().getTime();
		TimeStamp timeStamp = new TimeStamp();
		timeStamp.setCreated(date);
		timeStamp.setModified(date);
		item.setTimeStamp(timeStamp);

		shoppingItemDAO.create(item);
	}
	
	private ShoppingItem validateList(ShoppingItem item, String username) throws NotAuthorizedException {
		if (username.equals(item.getShoppingList().getOwner().getUsername())){
			return item;
		}
		for (User user : item.getShoppingList().getMembers()) {
			if (username.equals(user.getUsername())) {
				return item;
			}
		}
		
		throw new NotAuthorizedException(username);
	}
}
