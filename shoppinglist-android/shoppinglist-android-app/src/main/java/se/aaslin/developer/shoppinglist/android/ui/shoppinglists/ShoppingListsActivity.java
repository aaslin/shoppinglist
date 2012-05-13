package se.aaslin.developer.shoppinglist.android.ui.shoppinglists;

import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.ActivityPlace;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.app.util.RPCUtils;
import se.aaslin.developer.shoppinglist.android.service.ShoppingListServiceAsync;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.presenter.ShoppingListsPresenter;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.view.ShoppingListsView;
import android.os.Bundle;
	
public class ShoppingListsActivity extends ActivityPlace<ShoppingListsPlace> {
	
	Presenter presenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.shoppinglists);
		
		ShoppingListsPresenter.View view = new ShoppingListsView(this);
		view.initView(getWindow().getDecorView());
		ShoppingListsPresenter.Model model = place.getModel();
		ShoppingListServiceAsync srv = RPCUtils.getRPCService(ShoppingListServiceAsync.class, this);
		
		presenter = new ShoppingListsPresenter(view, srv, model);
		InjectionUtils.injectMembers(presenter, this);
		presenter.bind();
		presenter.create();
	}
}
