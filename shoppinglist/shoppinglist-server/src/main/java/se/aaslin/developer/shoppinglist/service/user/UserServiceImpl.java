package se.aaslin.developer.shoppinglist.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.aaslin.developer.shoppinglist.dao.UserDAO;
import se.aaslin.developer.shoppinglist.entity.User;
import se.aaslin.developer.shoppinglist.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired UserDAO userDAO;
	
	@Override
	public void changePassword(String username, String newPassword) {
		userDAO.findByUsername(username).setPassword(newPassword);
	}

	@Override
	public List<String> getAllUsers(String currentUsername) {
		List<String> result = new ArrayList<String>();
		for (User user : userDAO.list()) {
			result.add(user.getUsername());
		}
		
		return result;
	}
}
