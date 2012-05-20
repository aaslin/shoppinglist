package se.aaslin.developer.shoppinglist.android.ui.shoppinglists;

import java.util.ArrayList;

import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.app.util.RPCUtils;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.back.service.ShoppingListServiceAsync;
import se.aaslin.developer.shoppinglist.android.ui.dashboard.DashboardPlace;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.presenter.ShoppingListsPresenter;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.view.ShoppingListsView;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ShoppingListsActivity extends ActivityPlace<ShoppingListsPlace> {

	Presenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setupActionbar();
		
		ShoppingListsPresenter.View view = new ShoppingListsView();
		view.initView(this);
		setContentView(view.getView());
		
		ShoppingListsPresenter.Model model = getPlace().getModel();

		ShoppingListServiceAsync srv = RPCUtils.create(ShoppingListServiceAsync.class, this);

		presenter = new ShoppingListsPresenter(view, srv, model, this);
		InjectionUtils.injectMembers(presenter, this);

		presenter.create();
		presenter.bind();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.shoppinglists, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			new DashboardPlace().moveTo(this, Intent.FLAG_ACTIVITY_CLEAR_TOP);
			return true;
		case R.id.menu_add:
			ShoppingListDTO list = initEmptyShoppingList();
			new EditShoppingListPlace(list).moveTo(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setupActionbar() {
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setTitle(getResources().getString(R.string.myLists));
	}

	private ShoppingListDTO initEmptyShoppingList() {
		ShoppingListDTO list = new ShoppingListDTO();
		list.setName("");
		list.setFromDB(false);
		list.setChanged(true);
		list.setMembers(new ArrayList<String>());
		
		return list;
	}

}
