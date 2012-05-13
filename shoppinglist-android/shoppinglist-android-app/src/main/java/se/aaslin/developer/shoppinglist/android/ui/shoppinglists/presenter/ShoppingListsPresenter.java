package se.aaslin.developer.shoppinglist.android.ui.shoppinglists.presenter;

import java.util.List;

import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.android.app.mvp.Display;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.service.ShoppingListServiceAsync;
import android.content.Context;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;

public class ShoppingListsPresenter extends Presenter {
	public interface View extends Display {
		
		void addLists(List<ShoppingListDTO> listDTOs);
		
		ListView getListView();
		
		void showSpinner(boolean show);
	}
	
	public interface Model {
		
		List<ShoppingListDTO> getShoppingLists();
	}
	
	@Inject Context context;
	
	View display;
	ShoppingListServiceAsync srv;
	Model model;
	
	public ShoppingListsPresenter(View display, ShoppingListServiceAsync srv, Model model) {
		this.display = display;
		this.srv = srv;
		this.model = model;
	}

	@Override
	protected void onBind() {
		display.getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adpaterView, android.view.View view, int pos, long id) {
				ShoppingListDTO shoppingList = model.getShoppingLists().get(pos);
				Toast.makeText(context, shoppingList.getName(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	protected void onCreate() {
		fetchShoppingLists();
	}

	private void fetchShoppingLists() {
		display.showSpinner(true);
		srv.getAll(new AsyncCallback<List<ShoppingListDTO>>() {
			
			@Override
			public void onSuccess(List<ShoppingListDTO> result) {
				model.getShoppingLists().clear();
				model.getShoppingLists().addAll(result);
				updateShoppinglists();
				display.showSpinner(false);
			}

			@Override
			public void onFailure(Throwable caught) {
				display.showSpinner(false);
				Log.e(ShoppingListsPresenter.class.getCanonicalName(), caught.getLocalizedMessage());
				Toast.makeText(context, caught.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void updateShoppinglists() {
		display.addLists(model.getShoppingLists());
	}
}
