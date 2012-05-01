package se.aaslin.developer.shoppinglist.client.content.profile.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.content.profile.service.ProfileViewServiceAsync;

public class ProfileUserAdminPresenter {
	public interface View extends Display {
		
		HasClickHandlers getAuthButton();
		
		HasClickHandlers getSesionButon();
	
	}
	
	View display;
	ProfileViewServiceAsync srv;
	
	public ProfileUserAdminPresenter(View display, ProfileViewServiceAsync srv) {
		this.display = display;
		this.srv = srv;
		bind();
	}

	private void bind() {
		display.getAuthButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				srv.testAuthException(new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						Window.alert("success");
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});
			}
		});
		
		display.getSesionButon().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				srv.testSessionException(new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						Window.alert("success");
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});
			}
		});
	}
}
