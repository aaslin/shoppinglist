package se.aaslin.developer.shoppinglist.android.ui.dashboard;

import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.app.util.RPCUtils;
import se.aaslin.developer.shoppinglist.android.back.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.android.back.service.LoginServiceAsync;
import se.aaslin.developer.shoppinglist.android.ui.dashboard.presenter.DashboardPresenter;
import se.aaslin.developer.shoppinglist.android.ui.dashboard.view.DashboardView;
import se.aaslin.developer.shoppinglist.android.ui.login.LoginPlace;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.inject.Inject;

public class DashboardActivity extends ActivityPlace<DashboardPlace>{

	Presenter dashboardPresenter;
	
	@Inject AuthenticationService authenticationService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		
		InjectionUtils.injectMembers(this, this);
		
		DashboardPresenter.ViewDisplay display = new DashboardView();
		display.initView(getWindow().getDecorView());
		
		dashboardPresenter = new DashboardPresenter(display, this);
		InjectionUtils.injectMembers(dashboardPresenter, this);
		dashboardPresenter.create();
		dashboardPresenter.bind();
	}
	
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.dashboard, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_logout:
			logout();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void logout() {
		authenticationService.removeAuthenticationId();
		authenticationService.storePassword("");
		authenticationService.storeUsername("");
		LoginServiceAsync loginService = RPCUtils.create(LoginServiceAsync.class, this);
		loginService.logout(new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				new LoginPlace().moveTo(DashboardActivity.this, Intent.FLAG_ACTIVITY_CLEAR_TOP);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Log.e(DashboardActivity.this.getClass().getCanonicalName(), caught.getMessage(), caught);
				Toast.makeText(DashboardActivity.this, caught.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
	}
}
