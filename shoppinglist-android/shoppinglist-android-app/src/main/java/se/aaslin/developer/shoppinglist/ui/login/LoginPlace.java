package se.aaslin.developer.shoppinglist.ui.login;

import se.aaslin.developer.shoppinglist.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.app.mvp.Place;

public class LoginPlace extends Place {
	
	private static final long serialVersionUID = 8471323571285300358L;

	@Override
	protected Class<? extends ActivityPlace<? extends Place>> getActivityClass() {
		return LoginActivity.class;
	}
}
