package se.aaslin.developer.shoppinglist.android.ui.splash.presenter;

import se.aaslin.developer.shoppinglist.android.app.mvp.Display;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.android.service.InstallerService;
import se.aaslin.developer.shoppinglist.android.ui.login.LoginPlace;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.ShoppingListsPlace;
import android.app.Activity;
import android.os.AsyncTask;

import com.google.inject.Inject;

public class SplashPresenter extends Presenter {
	public interface ViewDisplay extends Display {
		void startLoading();

		void stopLoading();
	}	

	@Inject InstallerService installerService;
	@Inject AuthenticationService authenticationService;
	
	ViewDisplay display;
	Activity activity;

	public SplashPresenter(ViewDisplay display, Activity activity) {
		this.display = display;
		this.activity = activity;
	}

	@Override
	protected void onCreate() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				installerService.runInstallProcedure();
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				String authId = authenticationService.getAuthenticationId();
				if (authId == null) {
					display.stopLoading();			
					new LoginPlace().moveTo(activity);
				} else {
					display.stopLoading();
					new ShoppingListsPlace().moveTo(activity);
				}
			}
		}.execute();
	}
}
