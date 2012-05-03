package se.aaslin.developer.shoppinglist.app.mvp;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import roboguice.application.RoboApplication;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class InjectionUtils {
	public static void injectViews(Object object, View view) {
		try {
			for (Field field : object.getClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(InjectView.class)) {
					if (Modifier.isStatic(field.getModifiers())) {
						throw new UnsupportedOperationException("Views may not be statically injected");
					} else if (!View.class.isAssignableFrom(field.getType())) {
						throw new UnsupportedOperationException("You may only use @InjectView on fields descended from type View");
					} else if (Context.class.isAssignableFrom(field.getDeclaringClass()) && !Activity.class.isAssignableFrom(field.getDeclaringClass())) {
						throw new UnsupportedOperationException("You may only use @InjectView in Activity contexts");
					} else {
						int viewId = field.getAnnotation(InjectView.class).value();
						field.setAccessible(true);
						field.set(object, view.findViewById(viewId));
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void injectMembers(Object object, Context context) {
		Injector injector = ((RoboApplication) context.getApplicationContext()).getInjector();
		injector.injectMembers(object);
	}

	public static void injectActivity(Object object, Activity activity) {
		try {
			for (Field field : object.getClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(Inject.class) && Activity.class.isAssignableFrom(field.getType())) {
					field.setAccessible(true);
					field.set(object, activity);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}
