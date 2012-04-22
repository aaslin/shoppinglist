package se.aaslin.developer.shoppinglist.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.shared.dto.DashboardItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.DashboardListPortletDTO;

public interface DashboardViewService {
	
	List<DashboardListPortletDTO> getAllListPortlets(String username);
	
	List<DashboardItemDTO> getAllShoppingListItems(int shoppingListId, String username);

	List<DashboardItemDTO> saveShoppingItems(int shoppingListId, List<DashboardItemDTO> items, String username);

	List<DashboardItemDTO> removeShoppingItem(DashboardItemDTO item, String username);
}
