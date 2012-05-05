package se.aaslin.developer.shoppinglist.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DashboardPlace extends Place {
	
	private String token;

	public DashboardPlace(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<DashboardPlace> {

		@Override
		public DashboardPlace getPlace(String token) {
			return new DashboardPlace(token);
		}

		@Override
		public String getToken(DashboardPlace place) {
			return place.getToken();
		}
	}
}