package se.aaslin.developer.shoppinglist.android.ui.splash;

import android.os.Bundle;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.ui.splash.presenter.SplashPresenter;
import se.aaslin.developer.shoppinglist.android.ui.splash.view.SplashView;


public class SplashActivity extends ActivityPlace<SplashPlace>{

	Presenter splashPresenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		SplashPresenter.ViewDisplay display = new SplashView();
		display.initView(getWindow().getDecorView());
		
		splashPresenter = new SplashPresenter(display, this);
		InjectionUtils.injectMembers(splashPresenter, this);
		splashPresenter.create();
	}
}
