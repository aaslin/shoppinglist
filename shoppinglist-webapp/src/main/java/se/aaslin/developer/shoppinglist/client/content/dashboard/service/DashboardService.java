package se.aaslin.developer.shoppinglist.client.content.dashboard.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.shared.dto.DashboardItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.DashboardListPortletDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("dashboard")
public interface DashboardService extends RemoteService {
	
	List<DashboardListPortletDTO> getAllListPortlets();
	
	List<DashboardItemDTO> getShoppingItems(int shoppingListId);
	
	List<DashboardItemDTO> saveShoppingItems(int shoppingListId, List<DashboardItemDTO> items);
	
	List<DashboardItemDTO> removeShoppingItem(DashboardItemDTO item);
}
