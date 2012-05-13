package se.aaslin.developer.shoppinglist.android.ui.shoppinglists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Place;
import se.aaslin.developer.shoppinglist.android.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.presenter.ShoppingListsPresenter;

public class ShoppingListsPlace extends Place {
	public class Model implements ShoppingListsPresenter.Model, Serializable {

		private static final long serialVersionUID = 2080858464024310549L;
		
		private final List<ShoppingListDTO> shoppingLists = new ArrayList<ShoppingListDTO>();
		
		@Override
		public List<ShoppingListDTO> getShoppingLists() {
			return shoppingLists;
		}
	}
	
	private static final long serialVersionUID = 6084678072636227990L;

	private final Model model;
	
	public ShoppingListsPlace() {
		model = new Model();
	}

	public Model getModel() {
		return model;
	}

	@Override
	protected Class<? extends ActivityPlace<? extends Place>> getActivityClass() {
		return ShoppingListsActivity.class;
	}
}
