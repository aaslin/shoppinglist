package se.aaslin.developer.shoppinglist.dao;

import se.aaslin.developer.shoppinglist.entity.User;

public interface UserDAO extends GenericDAO<Integer, User>{
	
	User findByUsername(String username);
}
