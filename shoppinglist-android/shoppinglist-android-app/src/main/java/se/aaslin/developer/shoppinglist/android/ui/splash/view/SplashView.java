package se.aaslin.developer.shoppinglist.android.ui.splash.view;

import roboguice.inject.InjectView;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import android.view.View;
import android.widget.ProgressBar;
import se.aaslin.developer.shoppinglist.android.ui.splash.presenter.SplashPresenter;


public class SplashView implements SplashPresenter.ViewDisplay {
	
	@InjectView(R.id.progressBar) ProgressBar progressBar;
	
	@Override
	public void initView(View view) {
		InjectionUtils.injectViews(this, view);
	}

	@Override
	public void startLoading() {
		progressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void stopLoading() {
		progressBar.setVisibility(View.INVISIBLE);
	}
}
