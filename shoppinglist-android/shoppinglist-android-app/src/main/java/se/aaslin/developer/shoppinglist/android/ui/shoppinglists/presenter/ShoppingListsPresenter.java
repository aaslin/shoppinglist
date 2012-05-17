package se.aaslin.developer.shoppinglist.android.ui.shoppinglists.presenter;

import java.util.List;

import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.android.app.mvp.Display;
import se.aaslin.developer.shoppinglist.android.app.mvp.IsView;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.back.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.android.back.service.ShoppingListServiceAsync;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.ShoppingItemsPlace;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.NewShoppingListPlace;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.view.ShoppingListsListElementView;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.inject.Inject;

public class ShoppingListsPresenter extends Presenter {
	public interface View extends IsView {

		void bindListAdapter(ArrayAdapter<ShoppingListDTO> adapter);

		void addList(ShoppingListDTO list);

		void removeList(ShoppingListDTO list);

		void showLoadingSpinner();

		void disableLoadingSpinner();
	}

	public interface ListElement extends Display {

		void setName(String text);

		void setInfo(String text);

		ImageButton getEditButton();

		ImageButton getMoreButton();
	}

	public interface Model {

		List<ShoppingListDTO> getShoppingLists();
	}

	@Inject
	AuthenticationService authenticationService;

	View view;
	ShoppingListServiceAsync srv;
	Model model;
	Activity activity;
	LayoutInflater inflater;

	public ShoppingListsPresenter(View display, ShoppingListServiceAsync srv, Model model, Activity activity) {
		this.view = display;
		this.srv = srv;
		this.model = model;
		this.activity = activity;
		inflater = LayoutInflater.from(activity);
	}

	@Override
	protected void onCreate() {
		fetchShoppingLists();
	}

	@Override
	protected void onBind() {
		view.bindListAdapter(new ArrayAdapter<ShoppingListDTO>(activity, -1) {

			@Override
			public android.view.View getView(int position, android.view.View convertView, ViewGroup parent) {
				return createShoppingListElement(position, convertView, getItem(position));
			}
		});
	}

	private void fetchShoppingLists() {
		view.showLoadingSpinner();
		srv.getShoppingLists(new AsyncCallback<List<ShoppingListDTO>>() {

			@Override
			public void onSuccess(List<ShoppingListDTO> result) {
				model.getShoppingLists().clear();
				model.getShoppingLists().addAll(result);
				updateShoppinglists();
				view.disableLoadingSpinner();
			}

			@Override
			public void onFailure(Throwable caught) {
				view.disableLoadingSpinner();
				Log.e(ShoppingListsPresenter.class.getCanonicalName(), caught.getLocalizedMessage());
				Toast.makeText(activity, caught.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void updateShoppinglists() {
		for (ShoppingListDTO list : model.getShoppingLists()) {
			view.addList(list);
		}
	}

	private android.view.View createShoppingListElement(int position, android.view.View convertView, final ShoppingListDTO list) {
		ListElement element = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.shoppinglists_listelement, null);
			element = new ShoppingListsListElementView();
			element.initView(convertView);
			convertView.setTag(element);
		} else {
			element = (ListElement) convertView.getTag();
		}

		element.setName(list.getName());
		element.setInfo(list.getOwner());
		element.getMoreButton().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(android.view.View v) {
				new ShoppingItemsPlace(list).moveTo(activity);
			}
		});
		if (list.getOwner().equals(getCurrentUsername())) {
			element.getEditButton().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(android.view.View v) {
					new NewShoppingListPlace(list).moveTo(activity);
				}
			});
		}

		return convertView;
	}

	private Object getCurrentUsername() {
		return authenticationService.getUsername();
	}
}
