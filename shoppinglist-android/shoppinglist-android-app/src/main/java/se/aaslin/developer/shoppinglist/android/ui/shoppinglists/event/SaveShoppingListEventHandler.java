package se.aaslin.developer.shoppinglist.android.ui.shoppinglists.event;

import se.aaslin.developer.roboeventbus.event.RoboEventHandler;

public interface SaveShoppingListEventHandler extends RoboEventHandler {
	
	 void onSave();
}
