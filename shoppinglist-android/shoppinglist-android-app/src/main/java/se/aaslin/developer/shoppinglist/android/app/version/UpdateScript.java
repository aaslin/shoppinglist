package se.aaslin.developer.shoppinglist.android.app.version;

import android.content.Context;
import android.util.Log;

public abstract class UpdateScript {
	
	protected Context context;

	protected abstract void onPreUpgrade();

	protected abstract void onUpgrade();

	protected abstract void onPostUpgrade();

	public void preUpgrade() {
		Log.i(this.getClass().getName(), "preUpgrade");
		onPreUpgrade();
	}

	public void upgrade() {
		Log.i(this.getClass().getName(), "upgrade");
		onUpgrade();
	}

	public void postUpgrade() {
		Log.i(this.getClass().getName(), "postUpgrade");
		onPostUpgrade();
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
