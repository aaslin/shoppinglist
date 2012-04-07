package se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListGridService;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListGridServiceAsync;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.view.ShoppingListGridView;

import com.google.gwt.core.client.GWT;

public class ShoppingListsPresenter {
	public interface ViewDisplay extends Display {

		ShoppingListGridView getShoppingListGridView();
	}
	
	private ViewDisplay display;

	public ShoppingListsPresenter(ViewDisplay display) {
		this.display = display;
		ShoppingListGridServiceAsync srv = GWT.create(ShoppingListGridService.class);
		new ShoppingListGridPresenter(srv, display.getShoppingListGridView());
	}
}
