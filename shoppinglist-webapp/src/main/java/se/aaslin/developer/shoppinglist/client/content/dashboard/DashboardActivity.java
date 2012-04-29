package se.aaslin.developer.shoppinglist.client.content.dashboard;

import se.aaslin.developer.shoppinglist.client.content.dashboard.presenter.DashboardPresenter;
import se.aaslin.developer.shoppinglist.client.content.dashboard.service.DashboardViewService;
import se.aaslin.developer.shoppinglist.client.content.dashboard.service.DashboardViewServiceAsync;
import se.aaslin.developer.shoppinglist.client.content.dashboard.view.DashboardView;
import se.aaslin.developer.shoppinglist.client.place.DashboardPlace;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class DashboardActivity extends AbstractActivity {
	
	public DashboardActivity(DashboardPlace place) {
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		DashboardView view = new DashboardView();
		DashboardViewServiceAsync srv = GWT.create(DashboardViewService.class);
		new DashboardPresenter(view, srv);
	
		panel.setWidget(view.getViewAsWidget());
	}
}

