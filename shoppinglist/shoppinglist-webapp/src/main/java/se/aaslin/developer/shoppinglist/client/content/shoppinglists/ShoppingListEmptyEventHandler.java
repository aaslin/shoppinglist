package se.aaslin.developer.shoppinglist.client.content.shoppinglists;

import com.google.gwt.event.shared.EventHandler;

public interface ShoppingListEmptyEventHandler extends EventHandler {
	
	void onEmpty(ShoppingListEmptyEvent event);
}
