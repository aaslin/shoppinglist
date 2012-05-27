package se.aaslin.developer.shoppinglist.service.shoppinglist;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.aaslin.developer.shoppinglist.dao.ShoppingItemDAO;
import se.aaslin.developer.shoppinglist.dao.ShoppingListDAO;
import se.aaslin.developer.shoppinglist.entity.Notification.Type;
import se.aaslin.developer.shoppinglist.entity.ShoppingItem;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.entity.TimeStamp;
import se.aaslin.developer.shoppinglist.entity.User;
import se.aaslin.developer.shoppinglist.service.NotificationService;
import se.aaslin.developer.shoppinglist.service.ShoppingItemService;
import se.aaslin.developer.shoppinglist.shared.exception.NotAuthorizedException;
import se.aaslin.developer.shoppinglist.shared.exception.NotFoundException;

@Service
public class ShoppingItemServiceImpl implements ShoppingItemService{

	@Autowired ShoppingListDAO shoppingListDAO;
	@Autowired ShoppingItemDAO shoppingItemDAO;
	@Autowired NotificationService notificationService;

	@Override
	public void remove(ShoppingItem item, String username) throws NotAuthorizedException {
		ShoppingItem managedItem = validateAccess(shoppingItemDAO.findById(item.getId()), username);
		
		managedItem.getShoppingList().getItems().remove(managedItem);
		shoppingItemDAO.delete(managedItem);
		
		notificationService.addNotification(Type.REMOVED, managedItem, username);
	}
	
	@Override
	public void update(ShoppingItem item, String username) throws NotAuthorizedException, NotFoundException {
		ShoppingItem managedItem = shoppingItemDAO.findById(item.getId());
		if (managedItem == null) {
			throw new NotFoundException();
		}
		managedItem = validateAccess(managedItem, username);
		
		managedItem.setName(item.getName());
		managedItem.setAmount(item.getAmount());
		managedItem.setComment(item.getComment());

		Date date = Calendar.getInstance().getTime();
		TimeStamp timeStamp = managedItem.getTimeStamp();
		timeStamp.setModified(date);
		
		notificationService.addNotification(Type.UPDATED, managedItem, username);
	}

	@Override
	public void create(int shoppingListId, ShoppingItem item, String username) throws NotAuthorizedException {
		ShoppingList list = shoppingListDAO.findById(shoppingListId);
		if (list == null) {
			return;
		}
		item.setShoppingList(list);
		list.getItems().add(item);
		item = validateAccess(item, username);
		
		Date date = Calendar.getInstance().getTime();
		TimeStamp timeStamp = new TimeStamp();
		timeStamp.setCreated(date);
		timeStamp.setModified(date);
		item.setTimeStamp(timeStamp);

		shoppingItemDAO.create(item);
		
		notificationService.addNotification(Type.ADDED, item, username);
	}
	
	private ShoppingItem validateAccess(ShoppingItem item, String username) throws NotAuthorizedException {
		if (item != null) {
			if (username.equals(item.getShoppingList().getOwner().getUsername())){
				return item;
			}
			for (User user : item.getShoppingList().getMembers()) {
				if (username.equals(user.getUsername())) {
					return item;
				}
			}
		}
		
		throw new NotAuthorizedException(username);
	}
}
