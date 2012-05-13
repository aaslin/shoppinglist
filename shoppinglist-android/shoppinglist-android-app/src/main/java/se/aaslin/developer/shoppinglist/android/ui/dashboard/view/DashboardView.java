package se.aaslin.developer.shoppinglist.android.ui.dashboard.view;

import roboguice.inject.InjectView;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.ui.dashboard.presenter.DashboardPresenter;
import android.view.View;
import android.widget.Button;

public class DashboardView implements DashboardPresenter.ViewDisplay {

	@InjectView(R.id.myLists) Button myLists;
	@InjectView(R.id.settings) Button settings;
	
	@Override
	public void initView(View view) {
		InjectionUtils.injectViews(this, view);
	}

	@Override
	public Button getMyLisyBtn() {
		return myLists;
	}

	@Override
	public Button getSettingsBtn() {
		return settings;
	}
}
