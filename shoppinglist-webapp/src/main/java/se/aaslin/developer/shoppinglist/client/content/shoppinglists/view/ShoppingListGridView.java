package se.aaslin.developer.shoppinglist.client.content.shoppinglists.view;

import se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter.ShoppingListGridPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class ShoppingListGridView extends Composite implements ShoppingListGridPresenter.ViewDisplay{
	public interface ShoppingListUiBinder extends UiBinder<HTMLPanel, ShoppingListGridView>{
	}
	
	ShoppingListUiBinder uiBinder = GWT.create(ShoppingListUiBinder.class);
	
	@UiField DecoratedStackPanel shoppingListPanel;
	
	public ShoppingListGridView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget getViewAsWidget() {
		return this;
	}

	
	@Override
	public void clearShoppingList() {
		shoppingListPanel.clear();
	}

	@Override
	public DisclosurePanel addShoppingList(int row, String name) {
		DisclosurePanel panel = new DisclosurePanel(name);
		shoppingListPanel.add(panel);
		return panel;
	}
}