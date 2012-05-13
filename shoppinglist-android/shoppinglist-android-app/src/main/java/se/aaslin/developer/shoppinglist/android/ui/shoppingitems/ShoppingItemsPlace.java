package se.aaslin.developer.shoppinglist.android.ui.shoppingitems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Place;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.presenter.ShoppingItemsPresenter;

public class ShoppingItemsPlace extends Place {
	public class Model implements ShoppingItemsPresenter.Model, Serializable {
		
		private static final long serialVersionUID = -5456092349901774940L;
		
		private final ShoppingListDTO shoppingListDTO;
		private final List<ShoppingItemDTO> shoppingItemDTOs = new ArrayList<ShoppingItemDTO>();
		
		public Model(ShoppingListDTO shoppingListDTO) {
			this.shoppingListDTO = shoppingListDTO;
		}

		@Override
		public ShoppingListDTO getShoppingList() {
			return shoppingListDTO;
		}

		@Override
		public List<ShoppingItemDTO> getShoppingItems() {
			return shoppingItemDTOs;
		}
		
	}
	
	private static final long serialVersionUID = 4670475589616053842L;

	private final Model model;

	public ShoppingItemsPlace(ShoppingListDTO shoppingListDTO) {
		model = new Model(shoppingListDTO);
	}

	@Override
	protected Class<? extends ActivityPlace<? extends Place>> getActivityClass() {
		return ShoppingItemsActivity.class;
	}

	public Model getModel() {
		return model;
	}
}
