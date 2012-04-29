package se.aaslin.developer.shoppinglist.service;

import java.util.List;


public interface UserService {
	
	void changePassword(String username, String newPassword);
	
	List<String> getAllUsers(String currentUsername);
}
