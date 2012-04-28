package se.aaslin.developer.shoppinglist.client.content.profile.presenter;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.content.profile.service.ProfileServiceAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ProfilePasswordPresenter {
	public interface View extends Display {

		HasKeyUpHandlers getPasswordTextBox1();
		
		HasKeyUpHandlers getPasswordTextBox2();
		
		String getPassword1();
		
		String getPassword2();
		
		void setPassword1Status(boolean isOk);
		
		void setPassword2Status(boolean isOk);
		
		void setPassword1StatusVisibility(boolean visible);
		
		void setPassword2StatusVisibility(boolean visible);
		
		void enableChangeButton(boolean enable);
		
		HasClickHandlers getChangeButton();
	}
	
	public interface Model {

		String getNewPassword1();
		
		void setNewPassword1(String password);
		
		String getNewPassword2();
		
		void setNewPassword2(String password);
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
		display.getPasswordTextBox1().addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				model.setNewPassword1(display.getPassword1());
				validatePassword();	
			}
		});
		
		display.getPasswordTextBox2().addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				model.setNewPassword2(display.getPassword2());
				validatePassword();	
			}
		});
		
		display.getChangeButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				srv.changePassword(model.getNewPassword1(), new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						display.enableChangeButton(false);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});
			}
		});
	}
	
	private void validatePassword() {
		validatePassword1();
		validatePassword2();
	}
	
	private void validatePassword1() {
		if(model.getNewPassword1().equals("")) {
			display.setPassword1StatusVisibility(false);
		} else if (validatePassword(model.getNewPassword1())) {
			display.setPassword1StatusVisibility(true);
			display.setPassword1Status(true);
		} else {
			display.setPassword1StatusVisibility(true);
			display.setPassword1Status(false);
			display.enableChangeButton(false);
		}
	}
	
	private void validatePassword2() {
		if(model.getNewPassword2().equals("")) {
			display.setPassword2StatusVisibility(false);
		} else if (validatePassword(model.getNewPassword2()) && model.getNewPassword2().equals(model.getNewPassword1())) {
			display.setPassword2StatusVisibility(true);
			display.setPassword2Status(true);
			display.enableChangeButton(true);
		} else {
			display.setPassword2StatusVisibility(true);
			display.setPassword2Status(false);
			display.enableChangeButton(false);
		}
	}
	
	private boolean validatePassword(String password) {
		return password != null && password.length() > 6;
	}
}

