package se.aaslin.developer.shoppinglist.android.ui.shoppingitems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import se.aaslin.developer.roboeventbus.RoboEventBus;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.app.util.RPCUtils;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.back.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.android.back.service.LoginServiceAsync;
import se.aaslin.developer.shoppinglist.android.back.service.ShoppingListServiceAsync;
import se.aaslin.developer.shoppinglist.android.ui.common.Notification;
import se.aaslin.developer.shoppinglist.android.ui.login.LoginPlace;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.presenter.ShoppingItemsPresenter;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.view.ShoppingItemsView;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.ShoppingListsPlace;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.inject.Inject;

public class ShoppingItemsActivity extends ActivityPlace<ShoppingItemsPlace> {
	public class ShoppingItemsModel implements ShoppingItemsPresenter.Model, Serializable {
		
		private static final long serialVersionUID = -5456092349901774940L;
		
		private final ShoppingListDTO shoppingListDTO;
		private final List<ShoppingItemDTO> shoppingItemDTOs = new ArrayList<ShoppingItemDTO>();
		private final Notification notification;

		public ShoppingItemsModel(ShoppingListDTO shoppingListDTO, Notification notification) {
			this.shoppingListDTO = shoppingListDTO;
			this.notification = notification;
		}

		@Override
		public ShoppingListDTO getShoppingList() {
			return shoppingListDTO;
		}

		@Override
		public List<ShoppingItemDTO> getShoppingItems() {
			return shoppingItemDTOs;
		}

		@Override
		public Notification getNotification() {
			return notification;
		}
		
	}

	Presenter presenter;
	RoboEventBus eventBus;
	
	@Inject AuthenticationService authenticationService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		InjectionUtils.injectMembers(this, this);
		eventBus = RoboEventBus.getInstance();
		setupActionbar();
		
		ShoppingItemsPresenter.View view = new ShoppingItemsView();
		view.initView(this);
		setContentView(view.getView());
		
		ShoppingItemsPresenter.Model model = new ShoppingItemsModel(getPlace().getShoppingListDTO(), getPlace().getNotification());
		ShoppingListServiceAsync srv = RPCUtils.create(ShoppingListServiceAsync.class, this);
		
		presenter = new ShoppingItemsPresenter(view, model, srv, this);
		InjectionUtils.injectMembers(presenter, this);
			
		presenter.bind();
		presenter.create();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
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
			new EditShoppingItemPlace(getPlace().getShoppingListDTO(), initEmptyShoppingItem()).moveTo(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private ShoppingItemDTO initEmptyShoppingItem() {
		ShoppingItemDTO item = new ShoppingItemDTO();
		item.setFromDB(false);
		return item;
	}

	private void setupActionbar() {
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setTitle(getPlace().getShoppingListDTO().getName());
	}
	
	private void logout() {
		authenticationService.removeAuthenticationId();
		authenticationService.storePassword("");
		authenticationService.storeUsername("");
		LoginServiceAsync loginService = RPCUtils.create(LoginServiceAsync.class, this);
		loginService.logout(new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				new LoginPlace().moveTo(ShoppingItemsActivity.this, Intent.FLAG_ACTIVITY_CLEAR_TOP);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Log.e(ShoppingItemsActivity.this.getClass().getCanonicalName(), caught.getMessage(), caught);
				Toast.makeText(ShoppingItemsActivity.this, caught.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
	}
}
