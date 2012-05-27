package se.aaslin.developer.shoppinglist.dao;

import java.util.List;

import se.aaslin.developer.shoppinglist.entity.Notification;

public interface NotificationDAO extends GenericDAO<Integer, Notification>{
	
	List<Notification> getNotifications(String username);

	void removeNotifications(List<Integer> ids);
}
