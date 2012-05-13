package se.aaslin.developer.shoppinglist.android.ui.splash.view;

import android.view.View;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.ui.splash.presenter.SplashPresenter;

public class SplashView implements SplashPresenter.ViewDisplay {
	
	@Override
	public void initView(View view) {
		InjectionUtils.injectViews(this, view);
	}

	@Override
	public void startLoading() {
		
	}

	@Override
	public void stopLoading() {
		
	}
}
