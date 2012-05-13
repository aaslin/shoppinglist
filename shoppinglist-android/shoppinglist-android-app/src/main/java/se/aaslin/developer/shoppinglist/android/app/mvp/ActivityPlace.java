package se.aaslin.developer.shoppinglist.android.app.mvp;

import java.lang.reflect.ParameterizedType;

import android.app.Activity;
import android.os.Bundle;

public abstract class ActivityPlace<T extends Place> extends Activity {
	
	protected T place;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		Class<T> clazz = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			place = (T) getIntent().getExtras().get(clazz.getName());
		} else {
			try {
				place = clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
