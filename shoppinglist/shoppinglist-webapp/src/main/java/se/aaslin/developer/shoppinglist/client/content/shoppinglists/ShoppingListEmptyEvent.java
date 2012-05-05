package se.aaslin.developer.shoppinglist.client.content.shoppinglists;

import com.google.gwt.event.shared.GwtEvent;

public class ShoppingListEmptyEvent extends GwtEvent<ShoppingListEmptyEventHandler> {
	
	public static final GwtEvent.Type<ShoppingListEmptyEventHandler> TYPE = new Type<ShoppingListEmptyEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShoppingListEmptyEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShoppingListEmptyEventHandler handler) {
		handler.onEmpty(this);
	}
}
