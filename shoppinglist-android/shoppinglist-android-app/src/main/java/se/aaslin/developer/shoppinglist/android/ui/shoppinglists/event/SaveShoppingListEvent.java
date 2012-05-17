package se.aaslin.developer.shoppinglist.android.ui.shoppinglists.event;

import se.aaslin.developer.roboeventbus.event.RoboEvent;

public class SaveShoppingListEvent extends RoboEvent<SaveShoppingListEventHandler>{
	
	public final static Type<SaveShoppingListEventHandler> TYPE = new Type<SaveShoppingListEventHandler>();
	
	@Override
	public void dispatch(SaveShoppingListEventHandler handler) {
		handler.onSave();
	}

	@Override
	public se.aaslin.developer.roboeventbus.event.RoboEvent.Type<SaveShoppingListEventHandler> getType() {
		return TYPE;
	}
}
