package se.aaslin.developer.shoppinglist.client.content.profile.presenter;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.content.profile.service.ProfileViewServiceAsync;

public class ProfileUserAdminPresenter {
	public interface View extends Display {
		
	}
	
	View display;
	ProfileViewServiceAsync srv;
	
	public ProfileUserAdminPresenter(View display, ProfileViewServiceAsync srv) {
		this.display = display;
		this.srv = srv;
	}
}
