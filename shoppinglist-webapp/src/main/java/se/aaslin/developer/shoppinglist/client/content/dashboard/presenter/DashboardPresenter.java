package se.aaslin.developer.shoppinglist.client.content.dashboard.presenter;

import java.util.ArrayList;
import java.util.List;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.content.dashboard.service.DashboardServiceAsync;
import se.aaslin.developer.shoppinglist.client.content.dashboard.view.DashboardListPortletView;
import se.aaslin.developer.shoppinglist.shared.dto.DashboardItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.DashboardListPortletDTO;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DashboardPresenter {
	public interface View extends Display {
		
		void addPortlet(DashboardListPortletView view);
	}
	
	public static class DashboardListPortletModel implements DashboardListPortletPresenter.Model {
		
		private String name;
		private String owner;
		private int shoppingListId;
		private List<DashboardItemDTO> items = new ArrayList<DashboardItemDTO>();
		
		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getOwner() {
			return owner;
		}

		@Override
		public int getShoppingListId() {
			return shoppingListId;
		}

		@Override
		public List<DashboardItemDTO> getItems() {
			return items;
		}
	}
	
	View display;
	DashboardServiceAsync srv;

	public DashboardPresenter(View display, DashboardServiceAsync srv) {
		this.display = display;
		this.srv = srv;
		fetchPortlets();
	}

	private void fetchPortlets() {
		srv.getAllListPortlets(new AsyncCallback<List<DashboardListPortletDTO>>() {
			
			@Override
			public void onSuccess(List<DashboardListPortletDTO> result) {
				for (DashboardListPortletDTO dto : result) {
					DashboardListPortletView view = new DashboardListPortletView();
					DashboardListPortletModel model = new DashboardListPortletModel();
					model.name = dto.getName();
					model.owner = dto.getOwner();
					model.shoppingListId = dto.getShoppingListId();
					new DashboardListPortletPresenter(view, model, srv);
					
					display.addPortlet(view);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}
}
