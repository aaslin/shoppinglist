package se.aaslin.developer.shoppinglist.android.ui.shoppingitems;

import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.app.util.RPCUtils;
import se.aaslin.developer.shoppinglist.android.back.service.ShoppingListServiceAsync;
import se.aaslin.developer.shoppinglist.android.ui.dashboard.DashboardPlace;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.presenter.ShoppingItemsPresenter;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.view.ShoppingItemsView;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.ShoppingListsPlace;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ShoppingItemsActivity extends ActivityPlace<ShoppingItemsPlace> {

	Presenter shoppingItemsPresenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shoppingitems);
		
		ShoppingItemsPresenter.ViewDisplay display = new ShoppingItemsView(this);
		display.initView(getWindow().getDecorView());
		
		ShoppingItemsPresenter.Model model = getPlace().getModel();
		
		ShoppingListServiceAsync srv = RPCUtils.createRPCService(ShoppingListServiceAsync.class, this);
		
		shoppingItemsPresenter = new ShoppingItemsPresenter(display, model, srv, this);
		shoppingItemsPresenter.create();
		shoppingItemsPresenter.bind();
		
		setupActionbar();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.shoppingitems, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			new ShoppingListsPlace().moveTo(this, Intent.FLAG_ACTIVITY_CLEAR_TOP);
			return true;
		case R.id.menu_add:
			return true;
		case R.id.menu_refresh:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void setupActionbar() {
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
	}
}
