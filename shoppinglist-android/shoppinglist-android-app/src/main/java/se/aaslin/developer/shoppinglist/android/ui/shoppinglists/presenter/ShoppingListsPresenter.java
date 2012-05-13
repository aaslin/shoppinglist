package se.aaslin.developer.shoppinglist.android.ui.shoppinglists.presenter;

import java.util.List;

import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.android.app.mvp.Display;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.back.service.ShoppingListServiceAsync;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.ShoppingItemsPlace;
import android.app.Activity;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ShoppingListsPresenter extends Presenter {
	public interface View extends Display {
		
		void addLists(List<ShoppingListDTO> listDTOs);
		
		ListView getListView();
		
		void showLoadingSpinner();
		
		void disableLoadingSpinner();
	}
	
	public interface Model {
		
		List<ShoppingListDTO> getShoppingLists();
	}
	
	View display;
	ShoppingListServiceAsync srv;
	Model model;
	Activity activity;
	
	public ShoppingListsPresenter(View display, ShoppingListServiceAsync srv, Model model, Activity activity) {
		this.display = display;
		this.srv = srv;
		this.model = model;
		this.activity = activity;
	}
	
	@Override
	protected void onCreate() {
		fetchShoppingLists();
	}

	@Override
	protected void onBind() {
		display.getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adpaterView, android.view.View view, int pos, long id) {
				ShoppingListDTO shoppingList = model.getShoppingLists().get(pos);
				new ShoppingItemsPlace(shoppingList).moveTo(activity);
			}
		});
	}

	private void fetchShoppingLists() {
		display.showLoadingSpinner();
		srv.getShoppingLists(new AsyncCallback<List<ShoppingListDTO>>() {
			
			@Override
			public void onSuccess(List<ShoppingListDTO> result) {
				model.getShoppingLists().clear();
				model.getShoppingLists().addAll(result);
				updateShoppinglists();
				display.disableLoadingSpinner();
			}

			@Override
			public void onFailure(Throwable caught) {
				display.disableLoadingSpinner();
				Log.e(ShoppingListsPresenter.class.getCanonicalName(), caught.getLocalizedMessage());
				Toast.makeText(activity, caught.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void updateShoppinglists() {
		display.addLists(model.getShoppingLists());
	}
}
