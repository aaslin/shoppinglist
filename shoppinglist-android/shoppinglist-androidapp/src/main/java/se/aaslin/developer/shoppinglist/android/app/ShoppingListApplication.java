package se.aaslin.developer.shoppinglist.android.app;

import java.util.List;

import roboguice.config.AbstractAndroidModule;
import se.aaslin.developer.robomvp.app.RoboMVPApplication;
import se.aaslin.developer.shoppinglist.android.login.service.LoginService;
import se.aaslin.developer.shoppinglist.android.login.service.LoginServiceImpl;

import com.google.inject.Module;

public class ShoppingListApplication extends RoboMVPApplication {
		@Override
		protected void addApplicationModules(List<Module> modules) {

			modules.add(new AbstractAndroidModule() {
				@Override
				protected void configure() {
					bind(LoginService.class).to(LoginServiceImpl.class);
				}
			});
		}
	
}
