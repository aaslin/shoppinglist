package se.aaslin.developer.shoppinglist.client.content;

import se.aaslin.developer.shoppinglist.client.content.dashboard.DashboardActivity;
import se.aaslin.developer.shoppinglist.client.content.profile.ProfileActivity;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListsActivity;
import se.aaslin.developer.shoppinglist.client.place.DashboardPlace;
import se.aaslin.developer.shoppinglist.client.place.ProfilePlace;
import se.aaslin.developer.shoppinglist.client.place.ShoppingListsPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class ContentActivityMapper implements ActivityMapper {

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof DashboardPlace) {
			return new DashboardActivity((DashboardPlace) place);
		} else if (place instanceof ShoppingListsPlace) {
			return new ShoppingListsActivity((ShoppingListsPlace) place);
		} else if (place instanceof ProfilePlace) {
			return new ProfileActivity((ProfilePlace) place);
		}
		
		return null;
	}
}
