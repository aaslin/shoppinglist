package se.aaslin.developer.shoppinglist.client.content.profile;

import se.aaslin.developer.shoppinglist.client.content.profile.presenter.ProfilePresenter;
import se.aaslin.developer.shoppinglist.client.content.profile.service.ProfileService;
import se.aaslin.developer.shoppinglist.client.content.profile.service.ProfileServiceAsync;
import se.aaslin.developer.shoppinglist.client.content.profile.view.ProfileView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ProfileActivity extends AbstractActivity {
	
	private Place place;
	
	public ProfileActivity(Place place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ProfileView view = new ProfileView();
		ProfileServiceAsync srv = GWT.create(ProfileService.class);
		new ProfilePresenter(view, eventBus, srv, place);
		
		panel.setWidget(view.getViewAsWidget());
	}	
}
