package se.aaslin.developer.shoppinglist.android.ui.dashboard;

import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.ui.dashboard.presenter.DashboardPresenter;
import se.aaslin.developer.shoppinglist.android.ui.dashboard.view.DashboardView;
import android.os.Bundle;

public class DashboardActivity extends ActivityPlace<DashboardPlace>{

	Presenter dashboardPresenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		
		DashboardPresenter.ViewDisplay display = new DashboardView();
		display.initView(getWindow().getDecorView());
		
		dashboardPresenter = new DashboardPresenter(display, this);
		InjectionUtils.injectMembers(dashboardPresenter, this);
		dashboardPresenter.create();
		dashboardPresenter.bind();
	}

}
