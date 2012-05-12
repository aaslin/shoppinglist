package se.aaslin.developer.shoppinglist.client.content.shoppinglists;

import se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter.ShoppingListsPresenter;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListsService;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListsServiceAsync;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.view.ShoppingListsView;
import se.aaslin.developer.shoppinglist.client.place.ShoppingListsPlace;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ShoppingListsActivity extends AbstractActivity {

	public ShoppingListsActivity(ShoppingListsPlace place) {
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ShoppingListsPresenter.ViewDisplay display = new ShoppingListsView();
		ShoppingListsServiceAsync srv = GWT.create(ShoppingListsService.class);
		new ShoppingListsPresenter(display, srv, eventBus);
		
		panel.setWidget(display.getViewAsWidget());
	}
}
