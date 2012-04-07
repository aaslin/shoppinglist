package se.aaslin.developer.shoppinglist.client.header;


import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class HeaderActivityMapper implements ActivityMapper {

	@Override
	public Activity getActivity(Place place) {
		return new HeaderActivity(place);
	}
}
