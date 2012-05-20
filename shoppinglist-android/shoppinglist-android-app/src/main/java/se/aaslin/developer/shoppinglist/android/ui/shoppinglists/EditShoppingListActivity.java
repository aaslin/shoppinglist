package se.aaslin.developer.shoppinglist.android.ui.shoppinglists;

import java.util.ArrayList;
import java.util.List;

import se.aaslin.developer.roboeventbus.RoboEventBus;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.app.util.RPCUtils;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.back.dto.UserDTO;
import se.aaslin.developer.shoppinglist.android.back.service.ShoppingListServiceAsync;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.event.RemoveShoppingListEvent;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.event.SaveShoppingListEvent;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.presenter.EditShoppingListPresenter;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.view.EditShoppingListView;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class EditShoppingListActivity extends ActivityPlace<EditShoppingListPlace> {
	public class EditShoppingListModel implements EditShoppingListPresenter.Model {
	
		private final ShoppingListDTO shoppingListDTO;
		private final List<String> allUsers;
		private final List<String> availableUsers = new ArrayList<String>();
				
		public EditShoppingListModel(List<String> users, ShoppingListDTO shoppingListDTO) {
			this.shoppingListDTO = shoppingListDTO;
			this.allUsers = users;
		}

		@Override
		public ShoppingListDTO getShoppingListDTO() {
			return shoppingListDTO;
		}

		@Override
		public List<String> getAllUsers() {
			return allUsers;
		}

		@Override
		public List<String> getAvailableUsers() {
			return availableUsers;
		}
	}
	
	Presenter presenter;
	RoboEventBus eventBus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		eventBus = RoboEventBus.getInstance();
		setupActionbar();
		
		final ShoppingListServiceAsync srv = RPCUtils.create(ShoppingListServiceAsync.class, this);
		srv.getAllUsers(new AsyncCallback<List<UserDTO>>() {
			
			@Override
			public void onSuccess(List<UserDTO> result) {
				EditShoppingListPresenter.View view = new EditShoppingListView(); 
				view.initView(EditShoppingListActivity.this);
				setContentView(view.getView());
				
				List<String> users = new ArrayList<String>();
				for (UserDTO dto : result) {
					users.add(dto.getUsername());
				}
				ShoppingListDTO shoppingList = getPlace().getShoppingList();
				EditShoppingListPresenter.Model model = new EditShoppingListModel(users, shoppingList);
				
				presenter = new EditShoppingListPresenter(view, model, srv, EditShoppingListActivity.this);
				InjectionUtils.injectMembers(presenter, EditShoppingListActivity.this);
				presenter.bind();
				presenter.create();
			}

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				Toast.makeText(EditShoppingListActivity.this, caught.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.destroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.edit_shoppinglist, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			new ShoppingListsPlace().moveTo(this, Intent.FLAG_ACTIVITY_CLEAR_TOP);
			return true;
		case R.id.menu_save:
			eventBus.fireEvent(new SaveShoppingListEvent());
			return true;
		case R.id.menu_reset:
			eventBus.fireEvent(new RemoveShoppingListEvent());
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void setupActionbar() {
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		if (getPlace().getShoppingList().getName().length() == 0) {
			actionbar.setTitle(getResources().getString(R.string.newList));
		} else {
			actionbar.setTitle(getPlace().getShoppingList().getName());
		}
	}
}
