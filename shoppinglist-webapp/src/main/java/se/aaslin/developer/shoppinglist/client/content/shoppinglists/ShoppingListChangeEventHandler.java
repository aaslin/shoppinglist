package se.aaslin.developer.shoppinglist.client.content.shoppinglists;

import com.google.gwt.event.shared.EventHandler;

public interface ShoppingListChangeEventHandler extends EventHandler {
	
	void onChange(ShoppingListChangeEvent event);
}
