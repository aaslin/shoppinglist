package se.aaslin.developer.shoppinglist.server.dashboard;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import se.aaslin.developer.shoppinglist.client.content.dashboard.service.DashboardService;
import se.aaslin.developer.shoppinglist.security.CookieUtils;
import se.aaslin.developer.shoppinglist.security.ShoppingListSessionManager;
import se.aaslin.developer.shoppinglist.server.SpringRemoteServiceServlet;
import se.aaslin.developer.shoppinglist.service.DashboardViewService;
import se.aaslin.developer.shoppinglist.shared.dto.DashboardItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.DashboardListPortletDTO;

public class DashboardServiceImpl extends SpringRemoteServiceServlet implements DashboardService {
	
	private static final long serialVersionUID = 2120461756049156718L;
	
	@Autowired DashboardViewService dashboardViewService;
	@Autowired ShoppingListSessionManager sessionManager;
	
	@Override
	public List<DashboardListPortletDTO> getAllListPortlets() {
		return dashboardViewService.getAllListPortlets(getCurrentUsername());
	}

	@Override
	public List<DashboardItemDTO> getShoppingItems(int shoppingListId) {
		return dashboardViewService.getAllShoppingListItems(shoppingListId, getCurrentUsername());
	}

	@Override
	public List<DashboardItemDTO> saveShoppingItems(int shoppingListId, List<DashboardItemDTO> items) {
		return dashboardViewService.saveShoppingItems(shoppingListId, items, getCurrentUsername());
	}

	@Override
	public List<DashboardItemDTO> removeShoppingItem(DashboardItemDTO item) {
		return dashboardViewService.removeShoppingItem(item, getCurrentUsername());
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
