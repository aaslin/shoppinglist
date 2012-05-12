package se.aaslin.developer.shoppinglist.client.content.shoppinglists.view;

import se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter.ShoppingListBoxPresenter.View;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter.ShoppingListBoxPresenter;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter.ShoppingListFormPresenter;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter.ShoppingListGridPresenter;
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
		
	@UiField Panel formPanel;
	@UiField Panel gridPanel;
	@UiField Panel boxPanel;
	
	private ShoppingListFormPresenter.View formDisplay;
	private ShoppingListGridPresenter.View gridDisplay;
	private ShoppingListBoxPresenter.View boxDisplay;
	
	public ShoppingListsView() {
		initWidget(uiBinder.createAndBindUi(this));
		formDisplay = new ShoppingListFormView();
		formPanel.add(formDisplay.getViewAsWidget());
		gridDisplay = new ShoppingListGridView();
		gridPanel.add(gridDisplay.getViewAsWidget());
		boxDisplay = new ShoppingListBoxView();
		boxPanel.add(boxDisplay.getViewAsWidget());
	}

	@Override
	public Widget getViewAsWidget() {
		return this;
	}

	@Override
	public ShoppingListFormPresenter.View getShoppingListFormView() {
		return formDisplay;
	}

	@Override
	public ShoppingListGridPresenter.View getShoppingListGridView() {
		return gridDisplay;
	}

	@Override
	public View getShoppingListBoxView() {
		return boxDisplay;
	}
}
