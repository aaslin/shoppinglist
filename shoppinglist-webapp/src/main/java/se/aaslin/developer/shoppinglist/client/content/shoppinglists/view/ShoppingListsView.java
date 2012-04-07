package se.aaslin.developer.shoppinglist.client.content.shoppinglists.view;

import se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter.ShoppingListsPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ShoppingListsView extends Composite implements ShoppingListsPresenter.ViewDisplay {
	public interface ShoppingListsViewUIBinder extends UiBinder<HTMLPanel, ShoppingListsView> {
	}
	
	private ShoppingListsViewUIBinder uiBinder = GWT.create(ShoppingListsViewUIBinder.class);
	private ShoppingListGridView shoppingListGridView;
	
	@UiField Panel shoppingListGridPanel;
	
	public ShoppingListsView() {
		initWidget(uiBinder.createAndBindUi(this));
		shoppingListGridView = new ShoppingListGridView();
		shoppingListGridPanel.add(shoppingListGridView.getViewAsWidget());
	}

	@Override
	public Widget getViewAsWidget() {
		return this;
	}

	@Override
	public ShoppingListGridView getShoppingListGridView() {
		return shoppingListGridView;
	}
}
