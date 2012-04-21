package se.aaslin.developer.shoppinglist.client.content.shoppinglists.view;

import se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter.ShoppingListBoxPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ShoppingListBoxView extends Composite implements ShoppingListBoxPresenter.View {
	public interface ShoppingListBoxViewUIBinder extends UiBinder<HTMLPanel, ShoppingListBoxView > {
	}
	
	private ShoppingListBoxViewUIBinder uiBinder = GWT.create(ShoppingListBoxViewUIBinder.class);
	
	@UiField ListBox listBox;
	@UiField Button newList;
	@UiField Button removeList;
	
	public ShoppingListBoxView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget getViewAsWidget() {
		return this;
	}

	@Override
	public HasChangeHandlers getShoppingListBox() {
		return listBox;
	}

	@Override
	public int getSelectedIndex() {
		return listBox.getSelectedIndex();
	}

	@Override
	public void clearShoppingListBox() {
		listBox.clear();
	}

	@Override
	public void setSelectedIndex(int index) {
		listBox.setSelectedIndex(index);
	}

	@Override
	public Button getNewListButton() {
		return newList;
	}

	@Override
	public void addShoppingListToBox(BoxElement element) {
		listBox.addItem(element.getName());
	}

	@Override
	public Button getRemoveButton() {
		return removeList;
	}
}
