package se.aaslin.developer.shoppinglist.client.content.profile.place;

import se.aaslin.developer.shoppinglist.client.place.ProfilePlace;

import com.google.gwt.place.shared.PlaceTokenizer;

public class ProfilePasswordPlace extends ProfilePlace {
	
	public ProfilePasswordPlace(String token) {
		super(token);
	}

	public static class Tokenizer implements PlaceTokenizer<ProfilePasswordPlace> {

		@Override
		public ProfilePasswordPlace getPlace(String token) {
			return new ProfilePasswordPlace(token);
		}

		@Override
		public String getToken(ProfilePasswordPlace place) {
			return place.getToken();
		}
	}
}
