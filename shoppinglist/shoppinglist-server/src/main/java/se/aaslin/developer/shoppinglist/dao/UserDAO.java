package se.aaslin.developer.shoppinglist.dao;

import java.util.List;
import java.util.Set;

import se.aaslin.developer.shoppinglist.entity.User;

public interface UserDAO extends GenericDAO<Integer, User>{
	
	User findByUsername(String username);

	Set<User> findUsers(List<String> usernames);
}
