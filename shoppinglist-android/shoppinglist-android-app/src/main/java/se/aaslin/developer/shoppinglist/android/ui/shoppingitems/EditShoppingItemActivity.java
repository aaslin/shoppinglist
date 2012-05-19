package se.aaslin.developer.shoppinglist.android.ui.shoppingitems;

import se.aaslin.developer.roboeventbus.RoboEventBus;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.app.util.RPCUtils;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.back.service.ShoppingListServiceAsync;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.event.RemoveShoppingItemEvent;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.event.SaveShoppingItemEvent;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.presenter.EditShoppingItemPresenter;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.view.EditShoppingItemView;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class EditShoppingItemActivity extends ActivityPlace<EditShoppingItemPlace> {
	public class EditShoppingItemModel implements EditShoppingItemPresenter.Model {
		
		private final ShoppingListDTO shoppingListDTO;
		private final ShoppingItemDTO shoppingItemDTO;
		
		public EditShoppingItemModel(ShoppingListDTO shoppingListDTO, ShoppingItemDTO shoppingItemDTO) {
			this.shoppingListDTO = shoppingListDTO;
			this.shoppingItemDTO = shoppingItemDTO;
		}

		@Override
		public ShoppingItemDTO getShoppingItemDTO() {
			return shoppingItemDTO;
		}

		@Override
		public ShoppingListDTO getShoppingListDTO() {
			return shoppingListDTO;
		}		
	}
	
	Presenter presenter;
	RoboEventBus eventBus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
		eventBus = RoboEventBus.getInstance();
		
		final ShoppingListServiceAsync srv = RPCUtils.create(ShoppingListServiceAsync.class, this);
		
		EditShoppingItemPresenter.View view = new EditShoppingItemView();
		view.initView(this);
		setContentView(view.getView());
		
		EditShoppingItemPresenter.Model model = new EditShoppingItemModel(getPlace().getShoppingListDTO(), getPlace().getShoppingItem());
		
		presenter = new EditShoppingItemPresenter(view, model, srv, this);
		InjectionUtils.injectMembers(presenter, this);
		presenter.bind();
		presenter.create();
		
		setupActionbar();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.destroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.edit_shoppingitem, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			new ShoppingItemsPlace(getPlace().getShoppingListDTO()).moveTo(this, Intent.FLAG_ACTIVITY_CLEAR_TOP);
			return true;
		case R.id.menu_save:
			eventBus.fireEvent(new SaveShoppingItemEvent());
			return true;
		case R.id.menu_reset:
			eventBus.fireEvent(new RemoveShoppingItemEvent());
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void setupActionbar() {
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		if (getPlace().getShoppingItem().isFromDB()) {
			actionbar.setTitle(getPlace().getShoppingItem().getName());
		} else {
			actionbar.setTitle(getResources().getString(R.string.newList));
		}
	}
}
