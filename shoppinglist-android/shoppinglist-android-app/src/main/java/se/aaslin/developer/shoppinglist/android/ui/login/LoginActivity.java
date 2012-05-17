package se.aaslin.developer.shoppinglist.android.ui.login;

import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.app.util.RPCUtils;
import se.aaslin.developer.shoppinglist.android.back.service.LoginServiceAsync;
import se.aaslin.developer.shoppinglist.android.ui.login.presenter.LoginPresenter;
import se.aaslin.developer.shoppinglist.android.ui.login.view.LoginView;
import android.os.Bundle;

public class LoginActivity extends ActivityPlace<LoginPlace> {

	Presenter loginPresenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		LoginPresenter.ViewDisplay display = new LoginView();
		display.initView(getWindow().getDecorView());
		
		LoginServiceAsync srv = RPCUtils.create(LoginServiceAsync.class, this);
		loginPresenter = new LoginPresenter(display, srv, this);
		InjectionUtils.injectMembers(loginPresenter, this);
		loginPresenter.bind();
	}
}
