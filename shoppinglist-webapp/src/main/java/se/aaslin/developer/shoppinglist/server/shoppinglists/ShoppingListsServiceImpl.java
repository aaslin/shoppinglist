package se.aaslin.developer.shoppinglist.server.shoppinglists;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListsService;
import se.aaslin.developer.shoppinglist.security.CookieUtils;
import se.aaslin.developer.shoppinglist.security.ShoppingListSessionManager;
import se.aaslin.developer.shoppinglist.server.SpringRemoteServiceServlet;
import se.aaslin.developer.shoppinglist.service.ShoppingListsViewService;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

public class ShoppingListsServiceImpl extends SpringRemoteServiceServlet implements ShoppingListsService {

	private static final long serialVersionUID = -2973378272150112283L;
	
	@Autowired ShoppingListsViewService service;
	@Autowired ShoppingListSessionManager sessionManager;
	
	@Override
	public List<ShoppingListDTO> getShoppingLists() {
		return service.getOwnedShoppingListsForUser(getCurrentUsername());
	}

	@Override
	public List<ShoppingItemDTO> getShoppingItems(int shoppingListId) {
		return service.getAllShoppingListItems(shoppingListId);
	}

	@Override
	public List<ShoppingListDTO> saveShoppingList(ShoppingListDTO dto) {
		service.save(dto, getCurrentUsername());
		return getShoppingLists();
	}

	@Override
	public List<ShoppingItemDTO> saveShoppingItems(int shoppingListId, List<ShoppingItemDTO> itemDTOs) {
		service.saveItemsToShoppingList(shoppingListId, itemDTOs);
		return getShoppingItems(shoppingListId);
	}

	@Override
	public List<ShoppingListDTO> removeShoppingList(ShoppingListDTO dto) {
		service.remove(dto.getID());
		return getShoppingLists();
	}

	@Override
	public List<ShoppingItemDTO> removeShoppingItem(ShoppingItemDTO itemDTO) {
		service.remove(itemDTO);
		return getShoppingItems(itemDTO.getShoppingListId());
	}

	@Override
	public List<String> getAllUsers() {
		return service.getAllUsers();
	}

	@Override
	public ShoppingListDTO getShoppingList(int shoppingListId) {
		return service.findById(shoppingListId);
	}

	@Override
	public ShoppingListDTO updateShoppingList(ShoppingListDTO dto) {
		service.save(dto, getCurrentUsername());
		return getShoppingList(dto.getID());
	}
	
	private String getCurrentUsername() {
		String cookie = CookieUtils.getAuthCookie(getThreadLocalRequest());
		if (cookie == null) {
			return null;
		}
		String username = sessionManager.getSessionUser(UUID.fromString(cookie));
		
		return username;
	}
}
