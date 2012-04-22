package se.aaslin.developer.shoppinglist.client.content.profile.service;

import se.aaslin.developer.shoppinglist.client.common.Display;

public class ProfilePasswordPresenter {
	public interface View extends Display {
		
	}
	
	public interface Model {

		String getNewPassword1();
		
		void setNewPassword1();
		
		String getNewPassword2();
		
		void setNewPassword2();
	}
	
	View display;
	Model model;
	ProfileServiceAsync srv;
	
	public ProfilePasswordPresenter(View display, Model model, ProfileServiceAsync srv) {
		this.display = display;
		this.model = model;
		this.srv = srv;
		bind();
	}

	private void bind() {
		
	}
}
