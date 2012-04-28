package se.aaslin.developer.shoppinglist.client.content.profile.presenter;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.content.profile.place.ProfilePasswordPlace;
import se.aaslin.developer.shoppinglist.client.content.profile.service.ProfileServiceAsync;
import se.aaslin.developer.shoppinglist.client.content.profile.view.ProfilePasswordView;
import se.aaslin.developer.shoppinglist.client.content.profile.view.ProfileSubMenuView;
import se.aaslin.developer.shoppinglist.client.content.profile.view.ProfileUserAdminView;
import se.aaslin.developer.shoppinglist.client.place.ProfilePlace;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;

public class ProfilePresenter {
	public interface View extends Display {
		
		void addSubMenu(Widget submenu);
		
		void addContent(Widget content);
	}
	
	public static class ProfilePasswordModel implements ProfilePasswordPresenter.Model {
		private String newPassword1 = "";
		private String newPassword2 = "";
		
		@Override
		public String getNewPassword1() {
			return newPassword1;
		}

		@Override
		public void setNewPassword1(String password) {
			newPassword1 = password;
		}

		@Override
		public String getNewPassword2() {
			return newPassword2;
		}

		@Override
		public void setNewPassword2(String password) {
			newPassword2 = password;
		}

	}
	
	View display;
	EventBus eventBus;
	ProfileServiceAsync srv;
	ProfilePlace place;
	
	public ProfilePresenter(View display, EventBus eventBus, ProfileServiceAsync srv, ProfilePlace place) {
		this.display = display;
		this.eventBus = eventBus;
		this.srv = srv;
		this.place = place;
		setup();
	}

	private void setup() {
		ProfileSubMenuView view = new ProfileSubMenuView();
		new ProfileSubMenuPresenter(view, place);
		display.addSubMenu(view.getViewAsWidget());
		
		if (place instanceof ProfilePasswordPlace) {
			ProfilePasswordPresenter.View passwordView = new ProfilePasswordView();
			ProfilePasswordPresenter.Model passwrodModel = new ProfilePasswordModel();
			new ProfilePasswordPresenter(passwordView, passwrodModel, srv);
			display.addContent(passwordView.getViewAsWidget());
		} else {
			ProfileUserAdminPresenter.View userAdminView = new ProfileUserAdminView();
			new ProfileUserAdminPresenter(userAdminView, srv);
			display.addContent(userAdminView.getViewAsWidget());
		}
	}
}
