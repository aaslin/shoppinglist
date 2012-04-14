package se.aaslin.developer.shoppinglist.server.shoppinglists;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListGridService;
import se.aaslin.developer.shoppinglist.dao.UserDAO;
import se.aaslin.developer.shoppinglist.entity.User;
import se.aaslin.developer.shoppinglist.security.ShoppingListSessionManager;
import se.aaslin.developer.shoppinglist.server.CookieUtils;
import se.aaslin.developer.shoppinglist.server.SpringRemoteServiceServlet;
import se.aaslin.developer.shoppinglist.service.ShoppingItemService;
import se.aaslin.developer.shoppinglist.service.ShoppingListService;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

public class ShoppingListGridServiceImpl extends SpringRemoteServiceServlet implements ShoppingListGridService{

	private static final long serialVersionUID = -2973378272150112283L;
	
	@Autowired ShoppingListService shoppingListService;
	@Autowired ShoppingItemService shoppingItemService;
	@Autowired ShoppingListSessionManager sessionManager;
	@Autowired UserDAO userDAO;

	@Override
	public List<ShoppingListDTO> getShoppingLists() {
		String cookie = CookieUtils.getAuthCookie(getThreadLocalRequest());
		if (cookie == null) {
			return null;
		}
		String username = sessionManager.getSessionUser(UUID.fromString(cookie));
		User user = userDAO.findByUsername(username);
		
		return shoppingListService.getAllShoppingListsForUser(user);
	}

	@Override
	public List<ShoppingItemDTO> getShoppingItems(int shoppingListId) {
		return shoppingItemService.getAllShoppingListItems(shoppingListId);
	}

	@Override
	public List<ShoppingListDTO> saveShoppingList(ShoppingListDTO dto) {
		shoppingListService.add(dto);
		return getShoppingLists();
	}

	@Override
	public List<ShoppingItemDTO> saveShoppingItems(int shoppingListId, List<ShoppingItemDTO> itemDTOs) {
		shoppingItemService.saveItemsToShoppingList(shoppingListId, itemDTOs);
		return getShoppingItems(shoppingListId);
	}

	@Override
	public List<ShoppingListDTO> removeShoppingList(ShoppingListDTO dto) {
		shoppingListService.remove(dto.getID());
		return getShoppingLists();
	}

	@Override
	public List<ShoppingItemDTO> removeShoppingItem(ShoppingItemDTO itemDTO) {
		shoppingItemService.remove(itemDTO);
		return getShoppingItems(itemDTO.getShoppingListId());
	}

	@Override
	public List<ShoppingListDTO> updateShoppingList(ShoppingListDTO dto) {
		shoppingListService.update(dto);
		return getShoppingLists();
	}

	@Override
	public List<ShoppingItemDTO> updateShoppingItem(ShoppingItemDTO itemDTO) {
		shoppingItemService.update(itemDTO);
		return getShoppingItems(itemDTO.getShoppingListId());
	}
}
