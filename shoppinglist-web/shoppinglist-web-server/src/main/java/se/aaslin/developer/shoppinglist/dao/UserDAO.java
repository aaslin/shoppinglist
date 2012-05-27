package se.aaslin.developer.shoppinglist.dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.entity.User;

public interface UserDAO extends GenericDAO<Integer, User>{
	
	User findByUsername(String username);

	Set<User> findUsers(List<String> usernames);
	
	void register(String username, String registration);

	@Deprecated
	List<User> getAllUsers(ShoppingList list, Collection<String> except);
}
