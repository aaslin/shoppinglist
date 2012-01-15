package se.aaslin.developer.shoppinglist.android.login.view;

import roboguice.inject.InjectView;
import se.aaslin.developer.robomvp.annotation.ContentView;
import se.aaslin.developer.robomvp.view.RoboDisplay;
import se.aaslin.developer.shoppinglist.android.R;
import se.aaslin.developer.shoppinglist.android.login.presenter.LoginPresenter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@ContentView(view = R.layout.login)
public class LoginView extends RoboDisplay implements LoginPresenter.ViewDisplay{

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
	
}
