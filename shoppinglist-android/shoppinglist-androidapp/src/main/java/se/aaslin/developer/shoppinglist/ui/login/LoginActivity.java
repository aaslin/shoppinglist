package se.aaslin.developer.shoppinglist.ui.login;

import se.aaslin.developer.roboproxy.RoboProxy;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.app.ShoppingListApplication;
import se.aaslin.developer.shoppinglist.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.client.login.service.LoginViewServiceAsync;
import se.aaslin.developer.shoppinglist.ui.login.presenter.LoginPresenter;
import se.aaslin.developer.shoppinglist.ui.login.view.LoginView;
import android.app.Activity;
import android.os.Bundle;

public class LoginActivity extends Activity {

	Presenter loginPresenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		LoginView view = new LoginView();
		view.initView(this);
		LoginViewServiceAsync srv = (LoginViewServiceAsync) RoboProxy.newProxyInstance(LoginViewServiceAsync.class, ShoppingListApplication.LOGIN_URL, this);
		loginPresenter = new LoginPresenter(view, srv, this);
		loginPresenter.create(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		loginPresenter.start();
	}

	@Override
	protected void onStop() {
		super.onStop();
		loginPresenter.stop();
	}
}