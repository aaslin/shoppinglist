package se.aaslin.developer.shoppinglist.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.entity.Notification;
import se.aaslin.developer.shoppinglist.entity.Notification.Type;
import se.aaslin.developer.shoppinglist.entity.ShoppingItem;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;

public interface NotificationService {
	
	List<Notification> getNotifications(String username); 
	
	void removeNotifications(List<Integer> ids);

	void addNotification(Type type, ShoppingItem managedItem, String username);
	
	void addNotification(Type type, ShoppingList shoppingList, String username);
}
