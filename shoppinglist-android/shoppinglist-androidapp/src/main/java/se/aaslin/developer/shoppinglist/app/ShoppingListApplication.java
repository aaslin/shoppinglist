package se.aaslin.developer.shoppinglist.app;

import java.util.List;

import roboguice.config.AbstractAndroidModule;
import se.aaslin.developer.robomvp.app.RoboMVPApplication;

import com.google.inject.Module;

public class ShoppingListApplication extends RoboMVPApplication {
	
//	private ClassPathScanner classPathScanner = new ClassPathScanner();

//	private List<Module> mockModules = classPathScanner.run();
	
	@Override
	protected void addApplicationModules(List<Module> modules) {

//		for(Module module : mockModules){
//			modules.add(module);
//		}
//		
		modules.add(new AbstractAndroidModule() {
			@Override
			protected void configure() {
				// bind(LoginServiceAsync.class).to(LoginServiceImpl.class);
			}
		});
		
	}

}
