package se.aaslin.developer.shoppinglist.server.shoppinglist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import se.aaslin.developer.shoppinglist.client.shoppinglist.service.ShoppingListGridService;
import se.aaslin.developer.shoppinglist.server.SpringRemoteServiceServlet;
import se.aaslin.developer.shoppinglist.service.ShoppingItemService;
import se.aaslin.developer.shoppinglist.service.ShoppingListService;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

public class ShoppingListGridServiceImpl extends SpringRemoteServiceServlet implements ShoppingListGridService{

	private static final long serialVersionUID = -2973378272150112283L;
	
	@Autowired ShoppingListService shoppingListService;
	@Autowired ShoppingItemService shoppingItemService;

	@Override
	public List<ShoppingListDTO> getShoppingLists(int userId) {
		return shoppingListService.getAllShoppingListsForUser(userId);
	}

	@Override
	public List<ShoppingItemDTO> getShoppingItems(int shoppingListId) {
		return shoppingItemService.getAllShoppingListItems(shoppingListId);
	}

}
