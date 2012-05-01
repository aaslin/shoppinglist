package se.aaslin.developer.shoppinglist.app;

import java.util.List;

import roboguice.application.RoboApplication;
import se.aaslin.developer.shoppinglist.app.mvp.ActivityProvider;
import android.app.Activity;

import com.google.inject.Binder;
import com.google.inject.Module;

public class ShoppingListApplication extends RoboApplication {

	public static String URL = "http://192.168.0.12:8080/shoppinglist/gwt.shoppinglist/";
	public static String LOGIN_URL = "http://192.168.0.12:8080/shoppinglist/gwt.login/";
	
	@Override
	protected void addApplicationModules(List<Module> modules) {

		modules.add(new Module() {
			@Override
			public void configure(Binder binder) {
			}
		});
	}
}
