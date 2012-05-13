package se.aaslin.developer.shoppinglist.android.ui.dashboard.presenter;

import se.aaslin.developer.shoppinglist.android.app.mvp.Display;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.ShoppingListsPlace;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class DashboardPresenter extends Presenter {
	public interface ViewDisplay extends Display {
	
		Button getMyLisyBtn();
		
		Button getSettingsBtn();
	}
	
	ViewDisplay display;
	Activity activity;
	
	public DashboardPresenter(ViewDisplay display, Activity activity) {
		this.display = display;
		this.activity = activity;
	}

	@Override
	protected void onBind() {
		display.getMyLisyBtn().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new ShoppingListsPlace().moveTo(activity);
			}
		});
		display.getSettingsBtn().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "Not yet implemented", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
