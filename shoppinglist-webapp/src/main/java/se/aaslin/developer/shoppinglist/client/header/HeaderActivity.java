package se.aaslin.developer.shoppinglist.client.header;

import se.aaslin.developer.shoppinglist.client.header.presenter.HeaderPresenter;
import se.aaslin.developer.shoppinglist.client.header.view.HeaderView;
import se.aaslin.developer.shoppinglist.client.login.service.LoginViewService;
import se.aaslin.developer.shoppinglist.client.login.service.LoginViewServiceAsync;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class HeaderActivity extends AbstractActivity {

	private Place place;
	
	public HeaderActivity(Place place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		HeaderView view = new HeaderView();
		LoginViewServiceAsync loginService = GWT.create(LoginViewService.class);
		new HeaderPresenter(view, loginService, place);
		
		panel.setWidget(view.getViewAsWidget());
	}
}
