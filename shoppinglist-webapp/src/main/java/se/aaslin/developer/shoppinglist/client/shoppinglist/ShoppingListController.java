package se.aaslin.developer.shoppinglist.client.shoppinglist;

import se.aaslin.developer.shoppinglist.client.common.Presenter;
import se.aaslin.developer.shoppinglist.client.login.presenter.LoginPresenter;
import se.aaslin.developer.shoppinglist.client.login.service.LoginService;
import se.aaslin.developer.shoppinglist.client.login.service.LoginServiceAsync;
import se.aaslin.developer.shoppinglist.client.login.view.LoginView;
import se.aaslin.developer.shoppinglist.client.shoppinglist.presenter.ShoppingListGridPresenter;
import se.aaslin.developer.shoppinglist.client.shoppinglist.service.ShoppingListGridService;
import se.aaslin.developer.shoppinglist.client.shoppinglist.service.ShoppingListGridServiceAsync;
import se.aaslin.developer.shoppinglist.client.shoppinglist.view.ShoppingListGridView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

public class ShoppingListController implements ValueChangeHandler<String> {

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
			History.newItem(ShoppingListID.SHOPPING_LIST.getToken());
		} else {
			History.fireCurrentHistoryState();
		}
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		ShoppingListID token = ShoppingListID.parseString(event.getValue());
		this.entryPoint.getContentPanel().clear();

		switch (token) {
			case LOGIN:
				goToLogin();
				break;
			case SHOPPING_LIST:
			default:
				ShoppingListGridServiceAsync srv = GWT.create(ShoppingListGridService.class);
				Presenter presenter = new ShoppingListGridPresenter(srv, new ShoppingListGridView(), 1);
				presenter.initContainer(entryPoint.getContentPanel());
				break;
		}
	}

	private void goToLogin() {
		LoginServiceAsync srv = GWT.create(LoginService.class);

		Presenter presenter = new LoginPresenter(new LoginView(), srv);
		presenter.initContainer(entryPoint.getContentPanel());
	}

}
