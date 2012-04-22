package se.aaslin.developer.shoppinglist.client.content.profile.presenter;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.content.profile.place.ProfilePasswordPlace;
import se.aaslin.developer.shoppinglist.client.content.profile.place.ProfileUserAdminPlace;
import se.aaslin.developer.shoppinglist.client.content.profile.service.ProfileServiceAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;

public class ProfilePresenter {
	public interface View extends Display {
		
	}
	
	View display;
	EventBus eventBus;
	ProfileServiceAsync srv;
	Place place;
	
	public ProfilePresenter(View display, EventBus eventBus, ProfileServiceAsync srv, Place place) {
		this.display = display;
		this.eventBus = eventBus;
		this.srv = srv;
		this.place = place;
		setup();
	}

	private void setup() {
		if (place instanceof ProfilePasswordPlace) {
			
		} else if (place instanceof ProfileUserAdminPlace) {
			
		}
	}
}
