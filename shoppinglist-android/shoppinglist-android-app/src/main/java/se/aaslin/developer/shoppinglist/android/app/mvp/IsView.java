package se.aaslin.developer.shoppinglist.android.app.mvp;

import android.content.Context;
import android.view.View;

public interface IsView {

	void initView(Context context);
	
	View getView();
}
