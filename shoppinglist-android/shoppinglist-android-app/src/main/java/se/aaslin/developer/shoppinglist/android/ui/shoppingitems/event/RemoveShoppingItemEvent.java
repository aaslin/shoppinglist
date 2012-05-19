package se.aaslin.developer.shoppinglist.android.ui.shoppingitems.event;

import se.aaslin.developer.roboeventbus.event.RoboEvent;

public class RemoveShoppingItemEvent extends RoboEvent<RemoveShoppingItemEventHandler>{
	
	public final static Type<RemoveShoppingItemEventHandler> TYPE = new Type<RemoveShoppingItemEventHandler>();
	
	@Override
	public void dispatch(RemoveShoppingItemEventHandler handler) {
		handler.onRemove();
	}

	@Override
	public se.aaslin.developer.roboeventbus.event.RoboEvent.Type<RemoveShoppingItemEventHandler> getType() {
		return TYPE;
	}
}
