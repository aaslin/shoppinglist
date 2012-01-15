package se.aaslin.developer.shoppinglist.client.shoppinglist;

import se.aaslin.developer.shoppinglist.client.common.Presenter;
import se.aaslin.developer.shoppinglist.client.login.presenter.LoginPresenter;
import se.aaslin.developer.shoppinglist.client.login.service.LoginService;
import se.aaslin.developer.shoppinglist.client.login.service.LoginServiceAsync;
import se.aaslin.developer.shoppinglist.client.login.view.LoginView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Label;

public class ShoppingListController implements ValueChangeHandler<String>{

	private ShoppingList entryPoint;
		
	public ShoppingListController(ShoppingList entryPoint) {
		this.entryPoint = entryPoint;
		History.addValueChangeHandler(this);
	}

	/**
	 * Bootstrap the browser history when the current module loads.
	 */
	public void initHistory() {
		if ("".equals(History.getToken())) {
			History.newItem(ShoppingListID.LOGIN.getToken());
		} else {
			History.fireCurrentHistoryState();
		}
	}
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		ShoppingListID token = ShoppingListID.parseString(event.getValue());
		this.entryPoint.getContentPanel().clear();
		
		switch (token) {
		case SHOPPING_LIST:
			entryPoint.getContentPanel().add(new Label("ShoppingList"));
			break;
		case LOGIN:	
		default:
			goToLogin();	
			break;
		}
	}
	
	private void goToLogin(){
		LoginServiceAsync srv = GWT.create(LoginService.class);
		
		Presenter presenter = new LoginPresenter(new LoginView(), srv);
		presenter.initContainer(entryPoint.getContentPanel());
	}

}
