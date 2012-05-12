package se.aaslin.developer.shoppinglist.client.content.shoppinglists;

import com.google.gwt.event.shared.EventHandler;


public interface ShoppingListUpdateEventHandler extends EventHandler {
		
	void onUpdate(ShoppingListUpdateEvent event);
}
