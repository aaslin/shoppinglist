package se.aaslin.developer.shoppinglist.ui.login.presenter;

import se.aaslin.developer.shoppinglist.app.mvp.Display;
import se.aaslin.developer.shoppinglist.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.client.login.service.LoginViewServiceAsync;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginPresenter extends Presenter {
	public interface ViewDisplay extends Display {

		EditText getUsername();

		EditText getPassword();

		Button getLoginButton();

		TextView getInfo();	
	}
	
	ViewDisplay display;
	LoginViewServiceAsync srv;
	Activity activity;
	
	public LoginPresenter(ViewDisplay display, LoginViewServiceAsync srv, Activity activity) {
		this.display = display;
		this.srv = srv;
		this.activity = activity;
		bind();
	}

	protected void bind(){
		display.getLoginButton().setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String uname = display.getUsername().getEditableText().toString();
				String password = display.getPassword().getEditableText().toString();
				srv.login(uname, password, new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String result) {
						if (result != null) {
							display.getInfo().setText("");
							Toast.makeText(activity, "Login succeded\n" + result, Toast.LENGTH_LONG).show();
						} else {
							TextView info = display.getInfo();
							info.setText("Wrong username or password");
							info.setTextColor(Color.RED);
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Toast.makeText(activity, caught.getMessage(), Toast.LENGTH_LONG).show();
						Log.getStackTraceString(caught);
					}
				});
			}
		});
	}
}
