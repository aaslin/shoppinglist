package se.aaslin.developer.shoppinglist.ui.login.view;

import roboguice.inject.InjectView;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.ui.login.presenter.LoginPresenter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginView implements LoginPresenter.ViewDisplay {

	@InjectView(R.id.username) EditText username;
	@InjectView(R.id.password) EditText password;
	@InjectView(R.id.loginButtin) Button loginButton;
	@InjectView(R.id.info) TextView info;

	@Override
	public EditText getUsername() {
		return username;
	}
	
	@Override
	public EditText getPassword() {
		return password;
	}
	
	@Override
	public Button getLoginButton() {
		return loginButton;
	}
	
	@Override
	public TextView getInfo() {
		return info;
	}

	@Override
	public void initView(View view) {
		InjectionUtils.injectViews(this, view);
	}
}
