package se.aaslin.developer.shoppinglist.android.ui.shoppinglists.event;

import se.aaslin.developer.roboeventbus.event.RoboEventHandler;

public interface RemoveShoppingListEventHandler extends RoboEventHandler {
	
	 void onRemove();
}
