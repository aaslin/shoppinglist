package se.aaslin.developer.shoppinglist.client.content.profile.place;

import se.aaslin.developer.shoppinglist.client.place.ProfilePlace;

import com.google.gwt.place.shared.PlaceTokenizer;

public class ProfileUserAdminPlace extends ProfilePlace {
	
	public ProfileUserAdminPlace(String token) {
		super(token);
	}

	public static class Tokenizer implements PlaceTokenizer<ProfileUserAdminPlace> {

		@Override
		public ProfileUserAdminPlace getPlace(String token) {
			return new ProfileUserAdminPlace(token);
		}

		@Override
		public String getToken(ProfileUserAdminPlace place) {
			return place.getToken();
		}
	}
}
