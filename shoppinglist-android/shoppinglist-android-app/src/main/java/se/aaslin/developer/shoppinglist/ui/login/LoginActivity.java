package se.aaslin.developer.shoppinglist.ui.login;

import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.service.async.LoginAsyncService;
import se.aaslin.developer.shoppinglist.android.service.async.impl.LoginAsyncServiceImpl;
import se.aaslin.developer.shoppinglist.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.ui.login.presenter.LoginPresenter;
import se.aaslin.developer.shoppinglist.ui.login.view.LoginView;
import android.os.Bundle;

public class LoginActivity extends ActivityPlace<LoginPlace> {

	Presenter<LoginPlace> loginPresenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		LoginPresenter.ViewDisplay display = new LoginView();
		display.initView(getWindow().getDecorView());
		
		LoginAsyncService srv = new LoginAsyncServiceImpl();
		loginPresenter = new LoginPresenter(display, srv, this);
		InjectionUtils.injectMembers(loginPresenter, this);
		loginPresenter.bind();
	}
}
