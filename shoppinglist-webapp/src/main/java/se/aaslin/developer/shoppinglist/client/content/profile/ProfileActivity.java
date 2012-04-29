package se.aaslin.developer.shoppinglist.client.content.profile;

import se.aaslin.developer.shoppinglist.client.content.profile.presenter.ProfilePresenter;
import se.aaslin.developer.shoppinglist.client.content.profile.service.ProfileViewService;
import se.aaslin.developer.shoppinglist.client.content.profile.service.ProfileViewServiceAsync;
import se.aaslin.developer.shoppinglist.client.content.profile.view.ProfileView;
import se.aaslin.developer.shoppinglist.client.place.ProfilePlace;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ProfileActivity extends AbstractActivity {
	
	private ProfilePlace place;
	
	public ProfileActivity(ProfilePlace place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ProfileView view = new ProfileView();
		ProfileViewServiceAsync srv = GWT.create(ProfileViewService.class);
		new ProfilePresenter(view, eventBus, srv, place);
		
		panel.setWidget(view.getViewAsWidget());
	}	
}
