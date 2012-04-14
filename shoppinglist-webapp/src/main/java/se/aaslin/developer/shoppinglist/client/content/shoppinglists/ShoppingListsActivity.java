package se.aaslin.developer.shoppinglist.client.content.shoppinglists;

import se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter.ShoppingListGridPresenter;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListGridService;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListGridServiceAsync;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.view.ShoppingListGridView;
import se.aaslin.developer.shoppinglist.client.place.ShoppingListsPlace;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ShoppingListsActivity extends AbstractActivity {
	
	private ShoppingListsPlace place;

	public ShoppingListsActivity(ShoppingListsPlace place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ShoppingListGridPresenter.ViewDisplay display = new ShoppingListGridView();
		ShoppingListGridServiceAsync srv = GWT.create(ShoppingListGridService.class);
		new ShoppingListGridPresenter(srv, display);
		
		panel.setWidget(display.getViewAsWidget());
	}
}
