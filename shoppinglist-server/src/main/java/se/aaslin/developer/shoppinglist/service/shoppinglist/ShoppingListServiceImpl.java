package se.aaslin.developer.shoppinglist.service.shoppinglist;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.aaslin.developer.shoppinglist.dao.ShoppingItemDAO;
import se.aaslin.developer.shoppinglist.dao.ShoppingListDAO;
import se.aaslin.developer.shoppinglist.dao.UserDAO;
import se.aaslin.developer.shoppinglist.entity.ShoppingItem;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.entity.TimeStamp;
import se.aaslin.developer.shoppinglist.entity.User;
import se.aaslin.developer.shoppinglist.service.ShoppingListService;
import se.aaslin.developer.shoppinglist.shared.exception.NotAuthorizedException;

@Service
public class ShoppingListServiceImpl implements ShoppingListService {
	
	@Autowired UserDAO userDAO;
	@Autowired ShoppingListDAO shoppingListDAO;
	@Autowired ShoppingItemDAO shoppingItemDAO;
	
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
		return validateAccess(list, username);
	}

	@Override
	public void create(ShoppingList list, List<String> members, String username) throws NotAuthorizedException {
		list.setOwner(userDAO.findByUsername(username));
		list.setMembers(userDAO.findUsers(members));
		
		TimeStamp timeStamp = new TimeStamp();
		Date date = Calendar.getInstance().getTime();
		timeStamp.setCreated(date);
		timeStamp.setModified(date);
		
		shoppingListDAO.create(list);
	}

	@Override
	public void update(ShoppingList list, List<String> members, String username) throws NotAuthorizedException {
		ShoppingList managedList = shoppingListDAO.findById(list.getID());
		managedList = validateAccessOwnerOnly(managedList, username);
		
		managedList.setName(list.getName());
		managedList.setMembers(userDAO.findUsers(members));
		
		Date date = Calendar.getInstance().getTime();
		TimeStamp timeStamp = managedList.getTimeStamp();
		timeStamp.setModified(date);
	}
	
	@Override
	public void remove(ShoppingList list, String currentSessionsUsername) throws NotAuthorizedException {
		list = validateAccessOwnerOnly(list, currentSessionsUsername);
		ShoppingList managedList = shoppingListDAO.findById(list.getID());
		if(managedList != null){
			List<ShoppingItem> items = managedList.getItems();
			for (ShoppingItem item : items) {
				shoppingItemDAO.delete(item);
			}
			shoppingListDAO.delete(managedList);
		}
	}

	private ShoppingList validateAccess(ShoppingList list, String username) throws NotAuthorizedException {
		if (list != null) {
			if (username.equals(list.getOwner().getUsername())){
				return list;
			}
			for (User user : list.getMembers()) {
				if (username.equals(user.getUsername())) {
					return list;
				}
			}
		}
		
		throw new NotAuthorizedException(username);
	}
	
	private ShoppingList validateAccessOwnerOnly(ShoppingList list, String username) throws NotAuthorizedException {
		if (list != null) {
			if (username.equals(list.getOwner().getUsername())){
				return list;
			}
		}
		
		throw new NotAuthorizedException(username);
	}
}
