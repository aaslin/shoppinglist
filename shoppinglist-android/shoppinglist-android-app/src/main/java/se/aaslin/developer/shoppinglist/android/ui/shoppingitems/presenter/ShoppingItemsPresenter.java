package se.aaslin.developer.shoppinglist.android.ui.shoppingitems.presenter;

import java.util.List;

import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.android.app.mvp.Display;
import se.aaslin.developer.shoppinglist.android.app.mvp.IsView;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.back.service.ShoppingListServiceAsync;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.EditShoppingItemPlace;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.view.ShoppingItemsListElementView;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

public class ShoppingItemsPresenter extends Presenter {
	public interface View extends IsView {
		
		void bindListAdapter(ArrayAdapter<ShoppingItemDTO> adapter);
		
		void addItem(ShoppingItemDTO item);

		void removeList(ShoppingItemDTO item);
		
		void showLoadingSpinner();
		
		void disableLoadingSpinner();
	}
	
	public interface ListElement extends Display {
		
		void setName(String name);
		
		void setAmount(String amount);
		
		ImageButton getEditButton();
		
		ImageButton getInfoButton();
	}
	
	public interface Model {
		
		ShoppingListDTO getShoppingList();
		
		List<ShoppingItemDTO> getShoppingItems();
	}
	
	View view;
	Model model;
	ShoppingListServiceAsync srv;
	Activity activity;
	LayoutInflater inflater;
	
	public ShoppingItemsPresenter(View display, Model model, ShoppingListServiceAsync srv, Activity activity) {
		this.view = display;
		this.model = model;
		this.srv = srv;
		this.activity = activity;
		inflater = LayoutInflater.from(activity);
	}

	@Override
	protected void onCreate() {
		fetchShoppingItems();
	}

	@Override
	protected void onBind() {
		view.bindListAdapter(new ArrayAdapter<ShoppingItemDTO>(activity, -1){

			@Override
			public android.view.View getView(int position, android.view.View convertView, ViewGroup parent) {
				return createShoppingItemElement(position, convertView, getItem(position));
			}
		});
	}

	private void fetchShoppingItems() {
		view.showLoadingSpinner();
		srv.getShoppingItems(model.getShoppingList().getID(), new AsyncCallback<List<ShoppingItemDTO>>() {
			
			@Override
			public void onSuccess(List<ShoppingItemDTO> result) {
				model.getShoppingItems().clear();
				model.getShoppingItems().addAll(result);
				updateItems();
				view.disableLoadingSpinner();
			}

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				view.disableLoadingSpinner();
				Toast.makeText(activity, caught.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
	}

	private void updateItems() {
		for (ShoppingItemDTO item : model.getShoppingItems()) {
			view.addItem(item);
		}
	}
	
	private android.view.View createShoppingItemElement(int position, android.view.View convertView, final ShoppingItemDTO item) {
		ListElement element = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.shoppingitems_listelement, null);
			element = new ShoppingItemsListElementView();
			element.initView(convertView);
			convertView.setTag(element);
		} else {
			element = (ListElement) convertView.getTag();
		}

		element.setName(item.getName());
		element.setAmount(item.getAmount());
		element.getEditButton().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(android.view.View v) {
				new EditShoppingItemPlace(model.getShoppingList(), item).moveTo(activity);
			}
		});
		element.getInfoButton().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(android.view.View v) {
				Toast.makeText(activity, "test", Toast.LENGTH_SHORT).show();
			}
		});
		
		return convertView;
	}
}
