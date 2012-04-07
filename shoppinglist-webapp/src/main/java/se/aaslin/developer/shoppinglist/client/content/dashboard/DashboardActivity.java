package se.aaslin.developer.shoppinglist.client.content.dashboard;

import se.aaslin.developer.shoppinglist.client.content.dashboard.presenter.DashboardPresenter;
import se.aaslin.developer.shoppinglist.client.content.dashboard.view.DashboardView;
import se.aaslin.developer.shoppinglist.client.place.DashboardPlace;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class DashboardActivity extends AbstractActivity {
	
	private String token;
	
	public DashboardActivity(DashboardPlace place) {
		this.token = place.getToken();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		DashboardView view = new DashboardView();
		new DashboardPresenter(view);
	
		panel.setWidget(view.asWidget());
	}
}
