package se.aaslin.developer.shoppinglist.client.content.profile.presenter;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.content.profile.place.ProfilePasswordPlace;
import se.aaslin.developer.shoppinglist.client.place.ProfilePlace;

public class ProfileSubMenuPresenter {
	public interface View extends Display {
		
		void setUserAdminSelected();
		
		void setPasswordSelected();
	}
	
	View display;
	ProfilePlace place;
	
	public ProfileSubMenuPresenter(View view, ProfilePlace place) {
		this.display = view;
		this.place = place;
		setSelected();
	}

	private void setSelected() {
		if (place instanceof ProfilePasswordPlace) {
			display.setPasswordSelected();
		} else {
			display.setUserAdminSelected();
		}
	}
}
