package se.aaslin.developer.shoppinglist.client.content.shoppinglists;

import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.google.gwt.event.shared.GwtEvent;

public class ShoppingListUpdateEvent extends GwtEvent<ShoppingListUpdateEventHandler> {
	
	public static final GwtEvent.Type<ShoppingListUpdateEventHandler> TYPE = new Type<ShoppingListUpdateEventHandler>();
	
	private ShoppingListDTO shoppingListDTO;
	
	public ShoppingListUpdateEvent(ShoppingListDTO shoppingListDTO) {
		this.shoppingListDTO = shoppingListDTO;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShoppingListUpdateEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShoppingListUpdateEventHandler handler) {
		handler.onUpdate(this);
	}

	public ShoppingListDTO getShoppingListDTO() {
		return shoppingListDTO;
	}
}
