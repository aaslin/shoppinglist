package se.aaslin.developer.shoppinglist.client.content.shoppinglists.view;

import se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter.ShoppingListGridPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

public class ShoppingListGridView extends Composite implements ShoppingListGridPresenter.ViewDisplay{
	public interface ShoppingListGridViewUiBinder extends UiBinder<HTMLPanel, ShoppingListGridView>{
	}
	
	ShoppingListGridViewUiBinder uiBinder = GWT.create(ShoppingListGridViewUiBinder.class);
	
	@UiField ListBox listBox;
	@UiField FlexTable grid;
	@UiField Label shoppingListName;
	@UiField Label shoppingListOwner;
	@UiField Button newItem;
	@UiField Button save;
	@UiField Button reset;
	
	public ShoppingListGridView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget getViewAsWidget() {
		return this;
	}

	@Override
	public ListBox getShoppingListsBox() {
		return listBox;
	}

	@Override
	public void addHeader(String listName, String listOwner) {
		shoppingListName.setText(listName);
		shoppingListOwner.setText(listOwner);
		grid.setWidget(0, 0, new Label("Name"));
		grid.setWidget(0, 0, new Label("Amount"));
		grid.setWidget(0, 0, new Label("Comment"));
	}

	@Override
	public TextBox addNameTextBox(int row, String name) {
		TextBox tb = new TextBox();
		tb.setText(name);
		grid.setWidget(row, 0, tb);
		
		return tb;
	}

	@Override
	public TextBox addAmountTextBox(int row, String amount) {
		TextBox tb = new TextBox();
		tb.setText(amount);
		grid.setWidget(row, 1, tb);
		
		return tb;
	}

	@Override
	public TextBox addCommentTextBox(int row, String comment) {
		TextBox tb = new TextBox();
		tb.setText(comment);
		grid.setWidget(row, 2, tb);
		
		return tb;
	}

	@Override
	public HasValue<Boolean> addRemoveButton(int row) {
		ToggleButton removeBtn = new ToggleButton(new Image(GWT.getModuleBaseURL() + "../images/delete.png"), new Image(GWT.getModuleBaseURL() + "../images/animations/loader_small.gif"));
		removeBtn.setTitle("ta bort");
		removeBtn.setStylePrimaryName("removeBtn");
		grid.setWidget(row, 3, removeBtn);
		return removeBtn;
	}

	@Override
	public Button getNewItemButton() {
		return newItem;
	}

	@Override
	public Button getSaveButton() {
		return save;
	}

	@Override
	public Button getResetButton() {
		return reset;
	}

	@Override
	public void removeRow(int row) {
		grid.removeRow(row);
	}
}