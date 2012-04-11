package se.aaslin.developer.shoppinglist.client.content;

import se.aaslin.developer.shoppinglist.client.place.DashboardPlace;
import se.aaslin.developer.shoppinglist.client.place.ShoppingListsPlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({DashboardPlace.Tokenizer.class, ShoppingListsPlace.Tokenizer.class})
public interface ShoppingListPlaceHistoryMapper extends PlaceHistoryMapper {
}
