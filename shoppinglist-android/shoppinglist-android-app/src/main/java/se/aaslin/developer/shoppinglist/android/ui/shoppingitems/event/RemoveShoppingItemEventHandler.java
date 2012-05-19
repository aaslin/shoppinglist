package se.aaslin.developer.shoppinglist.android.ui.shoppingitems.event;

import se.aaslin.developer.roboeventbus.event.RoboEventHandler;

public interface RemoveShoppingItemEventHandler extends RoboEventHandler {
	
	 void onRemove();
}
