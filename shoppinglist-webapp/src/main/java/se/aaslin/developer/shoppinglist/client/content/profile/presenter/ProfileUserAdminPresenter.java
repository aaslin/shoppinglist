package se.aaslin.developer.shoppinglist.client.content.profile.presenter;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.content.profile.service.ProfileServiceAsync;

public class ProfileUserAdminPresenter {
	public interface View extends Display {
		
	}
	
	View display;
	ProfileServiceAsync srv;
	
	public ProfileUserAdminPresenter(View display, ProfileServiceAsync srv) {
		this.display = display;
		this.srv = srv;
	}
}
