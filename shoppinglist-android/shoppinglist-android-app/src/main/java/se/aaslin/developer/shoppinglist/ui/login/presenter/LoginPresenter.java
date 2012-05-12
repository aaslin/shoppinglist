package se.aaslin.developer.shoppinglist.ui.login.presenter;

import se.aaslin.developer.shoppinglist.android.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.app.mvp.Display;
import se.aaslin.developer.shoppinglist.app.mvp.Place;
import se.aaslin.developer.shoppinglist.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.client.login.service.LoginViewServiceAsync;
import se.aaslin.developer.shoppinglist.ui.login.LoginPlace;
import se.aaslin.developer.shoppinglist.ui.shoppinglists.ShoppingListsPlace;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class LoginPresenter extends Presenter<LoginPlace> {
	public interface ViewDisplay extends Display {

		EditText getUsername();

		EditText getPassword();

		Button getLoginButton();

		TextView getInfo();	
	}
	
	@Inject Context context;
	@Inject AuthenticationService authenticationService;
	
	ViewDisplay display;
	LoginViewServiceAsync srv;
	Activity activity;
	
	public LoginPresenter(ViewDisplay display, LoginViewServiceAsync srv, Activity activity) {
		this.display = display;
		this.srv = srv;
		this.activity = activity;
	}

	@Override
	protected void onBind(){
		display.getLoginButton().setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String uname = display.getUsername().getEditableText().toString();
				String password = display.getPassword().getEditableText().toString();
				srv.login(uname, password, new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String result) {
						if (result != null) {
							authenticationService.storeAuthenticationId(result);
							Place shoppingListsPlace = new ShoppingListsPlace();
							shoppingListsPlace.moveTo(activity);
						} else {
							TextView info = display.getInfo();
							info.setText("Wrong username or password");
							info.setTextColor(Color.RED);
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Toast.makeText(context, caught.getMessage(), Toast.LENGTH_LONG).show();
						Log.getStackTraceString(caught);
					}
				});
			}
		});
	}
}
