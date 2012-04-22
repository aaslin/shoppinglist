package se.aaslin.developer.shoppinglist.client.content.dashboard.service;

import java.util.List;

import se.aaslin.developer.shoppinglist.shared.dto.DashboardItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.DashboardListPortletDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DashboardServiceAsync {

	void getAllListPortlets(AsyncCallback<List<DashboardListPortletDTO>> callback);

	void getShoppingItems(int shoppingListId, AsyncCallback<List<DashboardItemDTO>> callback);

	void saveShoppingItems(int shoppingListId, List<DashboardItemDTO> items, AsyncCallback<List<DashboardItemDTO>> callback);

	void removeShoppingItem(DashboardItemDTO item, AsyncCallback<List<DashboardItemDTO>> callback);
}
