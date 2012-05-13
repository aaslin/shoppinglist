package se.aaslin.developer.shoppinglist.android.ui.splash;

import android.os.Bundle;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.app.util.RPCUtils;
import se.aaslin.developer.shoppinglist.android.back.service.InstallerServiceAsync;
import se.aaslin.developer.shoppinglist.android.back.service.LoginServiceAsync;
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
		
		LoginServiceAsync loginServiceAsync = RPCUtils.createRPCService(LoginServiceAsync.class, this);
		InstallerServiceAsync installerServiceAsync = RPCUtils.createRPCService(InstallerServiceAsync.class, this);
		
		splashPresenter = new SplashPresenter(display, installerServiceAsync, loginServiceAsync, this);
		InjectionUtils.injectMembers(splashPresenter, this);
		splashPresenter.create();
	}
}
