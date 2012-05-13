package se.aaslin.developer.shoppinglist.android.app.mvp;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;


public abstract class Place implements Serializable {

	private static final long serialVersionUID = 1359015581256488668L;
	
	public void moveTo(Activity activity, int... flags) {
		Intent intent = new Intent(activity, getActivityClass());
		for (int flag : flags) {
			intent.addFlags(flag);
		}
		intent.putExtra(this.getClass().getName(), this);
		activity.startActivity(intent);
	}
	
	protected abstract Class<? extends ActivityPlace<? extends Place>> getActivityClass();
}
