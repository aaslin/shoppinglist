package se.aaslin.developer.shoppinglist.app.mvp;

import java.io.Serializable;

import android.content.Context;
import android.content.Intent;


public abstract class Place implements Serializable {

	private static final long serialVersionUID = 1359015581256488668L;

	public void moveTo(Context context) {
		Intent intent = new Intent(context, getActivityClass());
		intent.putExtra(this.getClass().getName(), this);
		context.startActivity(intent);
	}
	
	protected abstract Class<? extends ActivityPlace<? extends Place>> getActivityClass();
}
