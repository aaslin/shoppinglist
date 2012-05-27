package se.aaslin.developer.shoppinglist.android.ui.splash.presenter;

import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.conf.Urls;
import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.android.app.mvp.Display;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.back.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.android.back.service.InstallerServiceAsync;
import se.aaslin.developer.shoppinglist.android.back.service.LoginServiceAsync;
import se.aaslin.developer.shoppinglist.android.ui.dashboard.DashboardPlace;
import se.aaslin.developer.shoppinglist.android.ui.login.LoginPlace;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.inject.Inject;

public class SplashPresenter extends Presenter {
	public interface ViewDisplay extends Display {
		void startLoading();

		void stopLoading();
	}	

	@Inject AuthenticationService authenticationService;
	
	ViewDisplay display;
	InstallerServiceAsync installSrv;
	LoginServiceAsync loginSrv;
	Activity activity;

	public SplashPresenter(ViewDisplay display, InstallerServiceAsync installSrv, LoginServiceAsync loginSrv, Activity activity) {
		this.display = display;
		this.installSrv = installSrv;
		this.loginSrv = loginSrv;
		this.activity = activity;
	}

	@Override
	protected void onCreate() {
		setup();
	}

	private void setup() {
		installSrv.runInstallProcedure(new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				authenticate();
				registerForC2DM();
			}

			@Override
			public void onFailure(Throwable caught) {
				display.stopLoading();
				Log.e(SplashPresenter.this.getClass().getCanonicalName(), caught.getLocalizedMessage());
				Toast.makeText(activity, activity.getResources().getString(R.string.installError), Toast.LENGTH_LONG).show();
			}
		});
	}
	
	private void authenticate() {
		String username = authenticationService.getUsername();
		String password = authenticationService.getPassword();
		
		loginSrv.login(username, password, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				display.stopLoading();
				authenticationService.storeAuthenticationId(result);
				new DashboardPlace().moveTo(activity);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				display.stopLoading();			
				new LoginPlace().moveTo(activity);
			}
		});
	}
	
	private void registerForC2DM() {
		Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
		registrationIntent.putExtra("app", PendingIntent.getBroadcast(activity, 0, new Intent(), 0)); // boilerplate
		registrationIntent.putExtra("sender", Urls.C2DM_EMAIL);
		
		Log.d(this.getClass().getCanonicalName(), "Requested registration");
		activity.startService(registrationIntent);
	}
}
