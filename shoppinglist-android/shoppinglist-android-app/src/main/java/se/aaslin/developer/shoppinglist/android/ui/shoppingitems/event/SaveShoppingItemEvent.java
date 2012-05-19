package se.aaslin.developer.shoppinglist.android.ui.shoppingitems.event;

import se.aaslin.developer.roboeventbus.event.RoboEvent;

public class SaveShoppingItemEvent extends RoboEvent<SaveShoppingItemEventHandler>{
	
	public final static Type<SaveShoppingItemEventHandler> TYPE = new Type<SaveShoppingItemEventHandler>();
	
	@Override
	public void dispatch(SaveShoppingItemEventHandler handler) {
		handler.onSave();
	}

	@Override
	public se.aaslin.developer.roboeventbus.event.RoboEvent.Type<SaveShoppingItemEventHandler> getType() {
		return TYPE;
	}
}
