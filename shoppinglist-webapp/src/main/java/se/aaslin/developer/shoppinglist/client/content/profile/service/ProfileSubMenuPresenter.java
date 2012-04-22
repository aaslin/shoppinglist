package se.aaslin.developer.shoppinglist.client.content.profile.service;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.place.ProfilePlace;

public class ProfileSubMenuPresenter {
	public interface View extends Display {
		
		void setUserAdminSelected();
		
		void setPasswordSelected();
	}
	
	View view;
	ProfilePlace place;
	
	public ProfileSubMenuPresenter(View view, ProfilePlace place) {
		this.view = view;
		this.place = place;
	}
}
