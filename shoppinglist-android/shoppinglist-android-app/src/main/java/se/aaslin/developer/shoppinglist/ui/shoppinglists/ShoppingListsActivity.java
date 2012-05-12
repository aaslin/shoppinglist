package se.aaslin.developer.shoppinglist.ui.shoppinglists;

import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.app.util.RPCUtils;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListsServiceAsync;
import se.aaslin.developer.shoppinglist.ui.shoppinglists.presenter.ShoppingListsPresenter;
import se.aaslin.developer.shoppinglist.ui.shoppinglists.view.ShoppingListsView;
import android.os.Bundle;

public class ShoppingListsActivity extends ActivityPlace<ShoppingListsPlace> {
	
	Presenter<ShoppingListsPlace> presenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.shoppinglists);
		
		ShoppingListsPresenter.View view = new ShoppingListsView(this);
		view.initView(getWindow().getDecorView());
		ShoppingListsPresenter.Model model = place.getModel();
		ShoppingListsServiceAsync srv = RPCUtils.getRPCService(ShoppingListsServiceAsync.class, this);
		
		presenter = new ShoppingListsPresenter(view, model, srv);
		InjectionUtils.injectMembers(presenter, this);
		presenter.bind();
		presenter.create();
	}
}