package se.aaslin.developer.shoppinglist.client.content;

import se.aaslin.developer.shoppinglist.client.content.profile.place.ProfilePasswordPlace;
import se.aaslin.developer.shoppinglist.client.content.profile.place.ProfileUserAdminPlace;
import se.aaslin.developer.shoppinglist.client.place.DashboardPlace;
import se.aaslin.developer.shoppinglist.client.place.ProfilePlace;
import se.aaslin.developer.shoppinglist.client.place.ShoppingListsPlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ DashboardPlace.Tokenizer.class, ShoppingListsPlace.Tokenizer.class, ProfilePlace.Tokenizer.class, ProfilePasswordPlace.Tokenizer.class,
		ProfileUserAdminPlace.Tokenizer.class })
public interface ShoppingListPlaceHistoryMapper extends PlaceHistoryMapper {
}
