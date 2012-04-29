package se.aaslin.developer.shoppinglist.client.header.presenter;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.login.service.LoginViewServiceAsync;
import se.aaslin.developer.shoppinglist.client.place.DashboardPlace;
import se.aaslin.developer.shoppinglist.client.place.ProfilePlace;
import se.aaslin.developer.shoppinglist.client.place.ShoppingListsPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class HeaderPresenter {
	public interface ViewDisplay extends Display {
		
		HasClickHandlers getLogout();
		
		void setHomeSelected();
		
		void setShoppingListsSelected();
		
		void setProfileSelected();
	}

	private ViewDisplay display;
	private LoginViewServiceAsync loginService;
	private Place place;
	
	public HeaderPresenter(ViewDisplay display, LoginViewServiceAsync loginService, Place place) {
		this.display = display;
		this.loginService = loginService;
		this.place = place;
		setSelected();
		bind();
	}

	private void setSelected() {
		if (place instanceof DashboardPlace) {
			display.setHomeSelected();
		} else if (place instanceof ShoppingListsPlace) {
			display.setShoppingListsSelected();
		} else if (place instanceof ProfilePlace) {
			display.setProfileSelected();
		}
	}

	private void bind() {
		display.getLogout().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				loginService.logout(new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						String url = GWT.getHostPageBaseURL() + "login.jsp";
						Window.Location.assign(url);
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
