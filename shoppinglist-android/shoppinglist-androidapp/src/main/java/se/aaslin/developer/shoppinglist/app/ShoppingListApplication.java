package se.aaslin.developer.shoppinglist.app;

import java.util.List;

import roboguice.config.AbstractAndroidModule;
import se.aaslin.developer.robomvp.app.RoboMVPApplication;

import com.google.inject.Module;

public class ShoppingListApplication extends RoboMVPApplication {

	public static String URL = "http://192.168.0.12:8080/shoppinglist/gwt.shoppinglist/";

	@Override
	protected void addApplicationModules(List<Module> modules) {

		// for(Module module : mockModules){
		// modules.add(module);
		// }
		//
		modules.add(new AbstractAndroidModule() {
			@Override
			protected void configure() {
//				bind(LoginServiceAsync.class).toInstance(
//						(LoginServiceAsync) RoboProxy.newProxyInstance(LoginServiceAsync.class, url, getApplicationContext()));
			}
		});

	}
}
