package se.aaslin.developer.shoppinglist.client.content.shoppinglists;

import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author lars
 *
 */
public class ShoppingListNewEvent extends GwtEvent<ShoppingListNewEventHandler> {
	
	public static final GwtEvent.Type<ShoppingListNewEventHandler> TYPE = new Type<ShoppingListNewEventHandler>();
	
	private ShoppingListDTO shoppingListDTO;
	
	public ShoppingListNewEvent(ShoppingListDTO shoppingListDTO) {
		this.shoppingListDTO = shoppingListDTO;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShoppingListNewEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShoppingListNewEventHandler handler) {
		handler.onNew(this);
	}

	public ShoppingListDTO getShoppingListDTO() {
		return shoppingListDTO;
	}
}
