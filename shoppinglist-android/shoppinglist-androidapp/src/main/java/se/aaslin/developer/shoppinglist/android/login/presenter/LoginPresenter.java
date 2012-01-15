package se.aaslin.developer.shoppinglist.android.login.presenter;

import com.google.inject.Inject;

import se.aaslin.developer.robomvp.annotation.InjectActivity;
import se.aaslin.developer.robomvp.annotation.InjectDisplay;
import se.aaslin.developer.robomvp.presenter.RoboPresenter;
import se.aaslin.developer.shoppinglist.android.login.service.LoginService;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPresenter extends RoboPresenter{
	public interface ViewDisplay {

		EditText getUsername();

		EditText getPassword();

		Button getLoginButton();

		TextView getInfo();
	}

	@InjectDisplay ViewDisplay display;
	@InjectActivity Activity activity;
	@Inject LoginService srv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		_bind();
	}

	private void _bind(){
		display.getLoginButton().setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String uname = display.getUsername().getEditableText().toString();
				String password = display.getPassword().getEditableText().toString();
				if(srv.login(uname, password)){
					display.getInfo().setText("");
					Toast.makeText(activity, "Login succeded", Toast.LENGTH_LONG).show();
				}else{
					TextView info = display.getInfo();
					info.setText("Wrong username or password");
					info.setTextColor(Color.RED);
				}
			}
		});
	}
}
