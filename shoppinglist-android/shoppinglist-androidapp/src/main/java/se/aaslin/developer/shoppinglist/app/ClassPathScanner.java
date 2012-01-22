package se.aaslin.developer.shoppinglist.app;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import se.aaslin.developer.robomvp.view.RoboDisplay;
import se.aaslin.developer.shoppinglist.app.mock.RoboDisplayMock;
import android.util.Log;

import com.google.inject.AbstractModule;
import com.google.inject.Module;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

public class ClassPathScanner {
	private static final String TAG = ClassPathScanner.class.getSimpleName();

	private static Field dexField;

	public ClassPathScanner() {
		try {
			for(Field field : PathClassLoader.class.getDeclaredFields()){
				Log.i(TAG, field.getName());
			}
			dexField = PathClassLoader.class.getDeclaredField("mDexs");
			
			dexField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			Log.e(TAG, "NoSuchFieldException: Failed to get mDexs field " + e.getLocalizedMessage());
		} catch (SecurityException e) {
			Log.e(TAG, "SecurityExceptio: Failed to get mDexs field " + e.getLocalizedMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<Module> run() {
		List<Module> modules = new ArrayList<Module>();
		try {
			// TODO (2): check here - in theory, the class loader is not
			// required to be a PathClassLoader
			PathClassLoader classLoader = (PathClassLoader) Thread.currentThread().getContextClassLoader();

			DexFile[] dexs = (DexFile[]) dexField.get(classLoader);
			for (DexFile dex : dexs) {
				Enumeration<String> entries = dex.entries();
				while (entries.hasMoreElements()) {
					// (3) Each entry is a class name, like "foo.bar.MyClass"
					String entry = entries.nextElement();
					Log.d(TAG, "Entry: " + entry);

					// (4) Load the class
					Class<?> entryClass = dex.loadClass(entry, classLoader);
					if (entryClass != null) {
						if (RoboDisplay.class.isAssignableFrom(entryClass)) {
							final Class<RoboDisplay> roboDisplayClass = (Class<RoboDisplay>) entryClass;
							modules.add(new AbstractModule() {

								@Override
								protected void configure() {
									bind(roboDisplayClass).to(RoboDisplayMock.class);
								}
							});
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO (5): more precise error handling
			Log.e(TAG, "Error", e);
		}
		return modules;
	}

}