package se.aaslin.developer.shoppinglist.client.login.presenter;

import se.aaslin.developer.robomvp.annotation.InjectDisplay;
import se.aaslin.developer.robomvp.presenter.RoboPresenter;
import se.aaslin.developer.robomvp.view.RoboDisplay;
import se.aaslin.developer.roboproxy.RoboProxy;
import se.aaslin.developer.shoppinglist.app.ShoppingListApplication;
import se.aaslin.developer.shoppinglist.client.login.service.LoginServiceAsync;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class LoginPresenter extends RoboPresenter{
	public interface ViewDisplay extends RoboDisplay {

		EditText getUsername();

		EditText getPassword();

		Button getLoginButton();

		TextView getInfo();	
	}

	@InjectDisplay ViewDisplay display;
	@Inject Activity activity;
	@Inject Context context;
	
	LoginServiceAsync srv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		srv = (LoginServiceAsync) RoboProxy.newProxyInstance(LoginServiceAsync.class, ShoppingListApplication.URL, context);
	}

	@Override
	protected void onBind(){
		display.getLoginButton().setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String uname = display.getUsername().getEditableText().toString();
				String password = display.getPassword().getEditableText().toString();
				srv.login(uname, password, new AsyncCallback<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
						if(result){
							display.getInfo().setText("");
							Toast.makeText(activity, "Login succeded", Toast.LENGTH_LONG).show();
						}else{
							TextView info = display.getInfo();
							info.setText("Wrong username or password");
							info.setTextColor(Color.RED);
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Toast.makeText(activity, caught.getMessage(), Toast.LENGTH_LONG).show();
					}
				});
			}
		});
	}
}
