package se.aaslin.developer.shoppinglist.android.ui.dashboard;

import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Place;

public class DashboardPlace extends Place{

	private static final long serialVersionUID = 1440013952974141838L;

	@Override
	protected Class<? extends ActivityPlace<? extends Place>> getActivityClass() {
		return DashboardActivity.class;
	}

}
