package se.aaslin.developer.shoppinglist.ui.login.presenter;

import se.aaslin.developer.shoppinglist.android.exception.AuthenticationFailedException;
import se.aaslin.developer.shoppinglist.android.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.android.service.async.LoginAsyncService;
import se.aaslin.developer.shoppinglist.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.app.mvp.Display;
import se.aaslin.developer.shoppinglist.app.mvp.Place;
import se.aaslin.developer.shoppinglist.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.ui.login.LoginPlace;
import se.aaslin.developer.shoppinglist.ui.shoppinglists.ShoppingListsPlace;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
	LoginAsyncService srv;
	Activity activity;
	
	public LoginPresenter(ViewDisplay display, LoginAsyncService srv, Activity activity) {
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
						authenticationService.storeAuthenticationId(result);
						Place shoppingListsPlace = new ShoppingListsPlace();
						shoppingListsPlace.moveTo(activity);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						if (caught instanceof AuthenticationFailedException) {
							TextView info = display.getInfo();
							info.setText("Wrong username or password");
							info.setTextColor(Color.RED);
						} else {
							Toast.makeText(context, caught.getMessage(), Toast.LENGTH_LONG).show();
							throw new RuntimeException(caught);
						}
					}
				});
			}
		});
	}
}
