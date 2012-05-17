package se.aaslin.developer.shoppinglist.android.ui.shoppinglists.event;

import se.aaslin.developer.roboeventbus.event.RoboEvent;

public class RemoveShoppingListEvent extends RoboEvent<RemoveShoppingListEventHandler>{
	
	public final static Type<RemoveShoppingListEventHandler> TYPE = new Type<RemoveShoppingListEventHandler>();
	
	@Override
	public void dispatch(RemoveShoppingListEventHandler handler) {
		handler.onRemove();
	}

	@Override
	public se.aaslin.developer.roboeventbus.event.RoboEvent.Type<RemoveShoppingListEventHandler> getType() {
		return TYPE;
	}
}
