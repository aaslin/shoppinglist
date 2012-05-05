package se.aaslin.developer.shoppinglist.client.content.shoppinglists;

import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.google.gwt.event.shared.GwtEvent;

public class ShoppingListChangeEvent extends GwtEvent<ShoppingListChangeEventHandler> {
	
	public static final GwtEvent.Type<ShoppingListChangeEventHandler> TYPE = new Type<ShoppingListChangeEventHandler>();
	
	private ShoppingListDTO newShoppingListDTO;
	
	public ShoppingListChangeEvent(ShoppingListDTO newShoppingListDTO) {
		this.newShoppingListDTO = newShoppingListDTO;
	}

	@Override
	public GwtEvent.Type<ShoppingListChangeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShoppingListChangeEventHandler handler) {
		handler.onChange(this);
	}

	public ShoppingListDTO getShoppingListDTO() {
		return newShoppingListDTO;
	}
}
