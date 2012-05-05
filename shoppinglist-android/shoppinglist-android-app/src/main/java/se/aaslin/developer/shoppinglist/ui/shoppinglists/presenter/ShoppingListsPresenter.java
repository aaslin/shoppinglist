package se.aaslin.developer.shoppinglist.ui.shoppinglists.presenter;

import se.aaslin.developer.shoppinglist.app.mvp.Display;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListsServiceAsync;

public class ShoppingListsPresenter {
	public interface View extends Display {
		
	}
	
	public interface Model {
		
	}
	
	View display;
	Model model;
	ShoppingListsServiceAsync srv;
	
	
}
