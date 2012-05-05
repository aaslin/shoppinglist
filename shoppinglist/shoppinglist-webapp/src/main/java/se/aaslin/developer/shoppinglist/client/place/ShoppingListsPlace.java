package se.aaslin.developer.shoppinglist.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ShoppingListsPlace extends Place {
	
	private String token;

	public ShoppingListsPlace(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
	
	public static class Tokenizer implements PlaceTokenizer<ShoppingListsPlace> {

		@Override
		public ShoppingListsPlace getPlace(String token) {
			return new ShoppingListsPlace(token);
		}

		@Override
		public String getToken(ShoppingListsPlace place) {
			return place.getToken();
		}
	}
}
