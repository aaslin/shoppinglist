package se.aaslin.developer.shoppinglist.ui.login;

import se.aaslin.developer.roboproxy.RoboProxy;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.conf.Urls;
import se.aaslin.developer.shoppinglist.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.client.login.service.LoginViewServiceAsync;
import se.aaslin.developer.shoppinglist.ui.login.presenter.LoginPresenter;
import se.aaslin.developer.shoppinglist.ui.login.view.LoginView;
import android.os.Bundle;

public class LoginActivity extends ActivityPlace<LoginPlace> {

	Presenter<LoginPlace> loginPresenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		LoginView view = new LoginView();
		view.initView(getWindow().getDecorView());
		LoginViewServiceAsync srv = (LoginViewServiceAsync) RoboProxy.newProxyInstance(LoginViewServiceAsync.class, Urls.LOGIN_URL, this);
		
		loginPresenter = new LoginPresenter(view, srv, this);
		InjectionUtils.injectMembers(loginPresenter, this);
		loginPresenter.bind();
	}
}
