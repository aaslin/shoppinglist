package se.aaslin.developer.shoppinglist.android.ui.splash;

import android.os.Bundle;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;


public class SplashActivity extends ActivityPlace<SplashPlace>{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		
	}

}
