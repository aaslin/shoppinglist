package se.aaslin.developer.shoppinglist.android.ui.shoppingitems.presenter;

import java.util.List;

import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.android.app.mvp.Display;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.back.service.ShoppingListServiceAsync;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ShoppingItemsPresenter extends Presenter {
	public interface ViewDisplay extends Display {
		
		void addItems(List<ShoppingItemDTO> itemDTOs);
		
		ListView getListView();
		
		void showLoadingSpinner();
		
		void disableLoadingSpinner();
	}
	
	public interface Model {
		
		ShoppingListDTO getShoppingList();
		
		List<ShoppingItemDTO> getShoppingItems();
	}
	
	ViewDisplay display;
	Model model;
	ShoppingListServiceAsync srv;
	Activity activity;
	
	public ShoppingItemsPresenter(ViewDisplay display, Model model, ShoppingListServiceAsync srv, Activity activity) {
		this.display = display;
		this.model = model;
		this.srv = srv;
		this.activity = activity;
	}

	@Override
	protected void onCreate() {
		fetchShoppingItems();
	}

	@Override
	protected void onBind() {
		display.getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
				ShoppingItemDTO dto = model.getShoppingItems().get(pos);
				Toast.makeText(activity, dto.getName(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void fetchShoppingItems() {
		display.showLoadingSpinner();
		srv.getShoppingItems(model.getShoppingList().getID(), new AsyncCallback<List<ShoppingItemDTO>>() {
			
			@Override
			public void onSuccess(List<ShoppingItemDTO> result) {
				updateItems(result);
				display.disableLoadingSpinner();
			}

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				display.disableLoadingSpinner();
				Toast.makeText(activity, caught.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
	}

	private void updateItems(List<ShoppingItemDTO> result) {
		model.getShoppingItems().clear();
		model.getShoppingItems().addAll(result);
		display.addItems(model.getShoppingItems());
	}
}
