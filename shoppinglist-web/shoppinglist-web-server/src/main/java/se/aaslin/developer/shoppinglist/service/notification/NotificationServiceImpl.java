package se.aaslin.developer.shoppinglist.service.notification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.aaslin.developer.shoppinglist.dao.NotificationDAO;
import se.aaslin.developer.shoppinglist.dao.UserDAO;
import se.aaslin.developer.shoppinglist.entity.Notification;
import se.aaslin.developer.shoppinglist.entity.Notification.Item;
import se.aaslin.developer.shoppinglist.entity.Notification.Type;
import se.aaslin.developer.shoppinglist.entity.ShoppingItem;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.entity.TimeStamp;
import se.aaslin.developer.shoppinglist.entity.User;
import se.aaslin.developer.shoppinglist.service.C2DMService;
import se.aaslin.developer.shoppinglist.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired NotificationDAO notificationDAO;
	@Autowired UserDAO userDAO;
	@Autowired C2DMService c2dmService;
	
	@Override
	public List<Notification> getNotifications(String username) {
		return notificationDAO.getNotifications(username);
	}

	@Override
	public void removeNotifications(List<Integer> ids) {
		notificationDAO.removeNotifications(ids);
	}

	@Override
	public void addNotification(Type type, ShoppingItem managedItem, String username) {
		for (User user : getAllUsers(managedItem.getShoppingList(), username)) {
			Notification notification = new Notification();
			notification.setCreator(userDAO.findByUsername(username));
			notification.setItem(Item.ITEM);
			
			TimeStamp timeStamp = new TimeStamp();
			notification.setTimeStamp(timeStamp);
			Date date = Calendar.getInstance().getTime();
			timeStamp.setCreated(date);
			timeStamp.setModified(date);
			
			notification.setType(type);
			notification.setUser(user);
			notification.setWhat(managedItem.getName());
			
			notificationDAO.create(notification);
			c2dmService.sendMessage(user.getRegistration(), "tickle");
		}
	}

	@Override
	public void addNotification(Type type, ShoppingList shoppingList, String username) {
		for (User user : getAllUsers(shoppingList, username)) {
			Notification notification = new Notification();
			notification.setCreator(userDAO.findByUsername(username));
			notification.setItem(Item.ITEM);
			
			TimeStamp timeStamp = new TimeStamp();
			notification.setTimeStamp(timeStamp);
			Date date = Calendar.getInstance().getTime();
			timeStamp.setCreated(date);
			timeStamp.setModified(date);
			
			notification.setType(type);
			notification.setUser(user);
			notification.setWhat(shoppingList.getName());
			
			notificationDAO.create(notification);
			c2dmService.sendMessage(user.getRegistration(), "tickle");
		}
	}
	
	private List<User> getAllUsers(ShoppingList shoppingList, String username) {
		List<User> users = new ArrayList<User>();
		if (!shoppingList.getOwner().getUsername().equals(username)) {
			users.add(shoppingList.getOwner());
		}
		
		for (User user : shoppingList.getMembers()) {
			if (!user.getUsername().equals(username)) {
				users.add(user);
			}
		}
		
		return users;
	}

}
