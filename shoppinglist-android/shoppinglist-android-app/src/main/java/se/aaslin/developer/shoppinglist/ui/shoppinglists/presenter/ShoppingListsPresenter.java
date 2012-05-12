package se.aaslin.developer.shoppinglist.ui.shoppinglists.presenter;

import java.util.List;

import se.aaslin.developer.shoppinglist.android.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.app.mvp.Display;
import se.aaslin.developer.shoppinglist.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.ui.shoppinglists.ShoppingListsPlace;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;

public class ShoppingListsPresenter extends Presenter<ShoppingListsPlace> {
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
	Model model;
	
	public ShoppingListsPresenter(View display, Model model) {
		this.display = display;
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
//		srv.getShoppingLists(new AsyncCallback<List<ShoppingListDTO>>() {
//			
//			@Override
//			public void onSuccess(List<ShoppingListDTO> result) {
//				model.getShoppingLists().clear();
//				model.getShoppingLists().addAll(result);
//				updateShoppinglists();
//				display.showSpinner(false);
//			}
//
//			@Override
//			public void onFailure(Throwable caught) {
//				display.showSpinner(false);
//				Toast.makeText(context, caught.getMessage(), Toast.LENGTH_SHORT).show();
//			}
//		});
	}
	
	private void updateShoppinglists() {
		display.addLists(model.getShoppingLists());
	}
}
