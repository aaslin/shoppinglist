package se.aaslin.developer.shoppinglist.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ProfilePlace extends Place {
	
	protected String token;

	public ProfilePlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}
	
	public static class Tokenizer implements PlaceTokenizer<ProfilePlace> {

		@Override
		public ProfilePlace getPlace(String token) {
			return new ProfilePlace(token);
		}

		@Override
		public String getToken(ProfilePlace place) {
			return place.getToken();
		}
	}
}
