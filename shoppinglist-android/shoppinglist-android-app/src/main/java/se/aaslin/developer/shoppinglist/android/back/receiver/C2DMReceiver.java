package se.aaslin.developer.shoppinglist.android.back.receiver;

import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.app.util.RPCUtils;
import se.aaslin.developer.shoppinglist.android.back.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.android.back.service.LoginServiceAsync;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.inject.Inject;

public class C2DMReceiver extends BroadcastReceiver {

	@Inject AuthenticationService authenticationService;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		InjectionUtils.injectMembers(this, context);
		if (intent.getAction().equals("com.google.android.c2dm.intent.REGISTRATION")) {
			handleRegistration(context, intent);
		} else if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
			handleMessage(context, intent);
		}
	}

	private void handleRegistration(Context context, Intent intent) {
		String registration = intent.getStringExtra("registration_id");
		if (intent.getStringExtra("error") != null) {
			// Registration failed, should try again later.
			Log.d(C2DMReceiver.this.getClass().getCanonicalName(), "registration failed");
			String error = intent.getStringExtra("error");
			if (error == "SERVICE_NOT_AVAILABLE") {
				Log.d(C2DMReceiver.this.getClass().getCanonicalName(), "SERVICE_NOT_AVAILABLE");
			} else if (error == "ACCOUNT_MISSING") {
				Log.d(C2DMReceiver.this.getClass().getCanonicalName(), "ACCOUNT_MISSING");
			} else if (error == "AUTHENTICATION_FAILED") {
				Log.d(C2DMReceiver.this.getClass().getCanonicalName(), "AUTHENTICATION_FAILED");
			} else if (error == "TOO_MANY_REGISTRATIONS") {
				Log.d(C2DMReceiver.this.getClass().getCanonicalName(), "TOO_MANY_REGISTRATIONS");
			} else if (error == "INVALID_SENDER") {
				Log.d(C2DMReceiver.this.getClass().getCanonicalName(), "INVALID_SENDER");
			} else if (error == "PHONE_REGISTRATION_ERROR") {
				Log.d(C2DMReceiver.this.getClass().getCanonicalName(), "PHONE_REGISTRATION_ERROR");
			}
		} else if (intent.getStringExtra("unregistered") != null) {
			// unregistration done, new messages from the authorized sender will
			// be rejected
			Log.d("c2dm", "unregistered");

		} else if (registration != null) {
			Log.d(C2DMReceiver.this.getClass().getCanonicalName(), "Registration id received: " + registration);
			authenticationService.storeRegistration(registration);
			
			LoginServiceAsync loginService = RPCUtils.create(LoginServiceAsync.class, context);
			loginService.register(registration, new AsyncCallback<Void>() {
				
				@Override
				public void onSuccess(Void result) {
					Log.d(C2DMReceiver.this.getClass().getCanonicalName(), "Successfully completed registration with backend");
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Log.e(C2DMReceiver.this.getClass().getCanonicalName(), caught.getMessage(), caught);
				}
			});
		}
	}

	private void handleMessage(Context context, Intent intent) {
		// Do whatever you want with the message
	}
}
