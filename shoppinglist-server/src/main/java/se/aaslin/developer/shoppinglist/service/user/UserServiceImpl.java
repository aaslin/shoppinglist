package se.aaslin.developer.shoppinglist.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.aaslin.developer.shoppinglist.dao.UserDAO;
import se.aaslin.developer.shoppinglist.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired UserDAO userDAO;
	
	@Override
	public void changePassword(String username, String newPassword) {
		userDAO.findByUsername(username).setPassword(newPassword);
	}
}
