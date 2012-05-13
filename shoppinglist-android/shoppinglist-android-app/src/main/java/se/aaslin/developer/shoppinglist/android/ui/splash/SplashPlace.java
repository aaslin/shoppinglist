package se.aaslin.developer.shoppinglist.android.ui.splash;

import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Place;

public class SplashPlace extends Place {

	private static final long serialVersionUID = 6598624563330750108L;

	@Override
	protected Class<? extends ActivityPlace<? extends Place>> getActivityClass() {
		return SplashActivity.class;
	}
}
