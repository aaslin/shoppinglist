package se.aaslin.developer.shoppinglist.android.ui.login;

import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Place;

public class LoginPlace extends Place {
	
	private static final long serialVersionUID = 8471323571285300358L;

	@Override
	protected Class<? extends ActivityPlace<? extends Place>> getActivityClass() {
		return LoginActivity.class;
	}
}
