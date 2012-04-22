package se.aaslin.developer.shoppinglist.service.shoppinglist;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.aaslin.developer.shoppinglist.dao.ShoppingListDAO;
import se.aaslin.developer.shoppinglist.dao.UserDAO;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.entity.User;
import se.aaslin.developer.shoppinglist.exception.NotAuthorizedException;
import se.aaslin.developer.shoppinglist.security.ShoppingListSessionManager;
import se.aaslin.developer.shoppinglist.service.ShoppingListService;

@Service
@Transactional
public class ShoppingListServiceImpl implements ShoppingListService {
	
	@Autowired UserDAO userDAO;
	@Autowired ShoppingListDAO shoppingListDAO;
	@Autowired ShoppingListSessionManager sessionManager;
	@Autowired(required=true) HttpServletRequest request;
	
	@Override
	public List<ShoppingList> getAllShoppingListsForUser(String username) {
		User user = userDAO.findByUsername(username);
		
		List<ShoppingList> ownedLists = shoppingListDAO.getOwnedShoppingListsForUser(user.getID());
		List<ShoppingList> memberLists = shoppingListDAO.getShoppingListsForUser(user.getID());
		
		List<ShoppingList> result = new ArrayList<ShoppingList>();
		result.addAll(ownedLists);
		result.addAll(memberLists);
		
		return result;
	}
	
	@Override
	public ShoppingList findShoppingListById(int shoppingListId, String username) throws NotAuthorizedException {
		ShoppingList list = shoppingListDAO.findById(shoppingListId);
		
		return validateList(list, username);
	}
	
	private ShoppingList validateList(ShoppingList list, String username) throws NotAuthorizedException {
		if (username.equals(list.getOwner().getUsername())){
			return list;
		}
		for (User user : list.getMembers()) {
			if (username.equals(user.getUsername())) {
				return list;
			}
		}
		
		throw new NotAuthorizedException(username);
	}
}
