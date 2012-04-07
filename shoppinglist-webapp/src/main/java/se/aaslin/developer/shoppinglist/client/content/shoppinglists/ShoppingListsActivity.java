package se.aaslin.developer.shoppinglist.client.content.shoppinglists;

import se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter.ShoppingListsPresenter;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.view.ShoppingListsView;
import se.aaslin.developer.shoppinglist.client.place.ShoppingListsPlace;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ShoppingListsActivity extends AbstractActivity {
	
	private ShoppingListsPlace place;

	public ShoppingListsActivity(ShoppingListsPlace place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ShoppingListsView view = new ShoppingListsView();
		new ShoppingListsPresenter(view);
		
		panel.setWidget(view.asWidget());
	}
}
