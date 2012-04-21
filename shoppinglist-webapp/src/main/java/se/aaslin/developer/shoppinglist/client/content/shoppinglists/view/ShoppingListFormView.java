package se.aaslin.developer.shoppinglist.client.content.shoppinglists.view;

import se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter.ShoppingListFormPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ShoppingListFormView extends Composite implements ShoppingListFormPresenter.View {
	public interface ShoppingListFormViewUIBinder extends UiBinder<HTMLPanel, ShoppingListFormView> {
	}
	
	private ShoppingListFormViewUIBinder uiBinder = GWT.create(ShoppingListFormViewUIBinder.class);
	
	@UiField Button saveList;
	@UiField Button resetList;
	@UiField FlexTable grid;
	@UiField Button addMember;
	@UiField TextBox listTextBox;
	@UiField Image loadingImage;
	@UiField Panel gridPanel;
	@UiField Panel emptyPanel;
	
	public ShoppingListFormView() {
		initWidget(uiBinder.createAndBindUi(this));
		loadingImage.setUrl(GWT.getModuleBaseURL() + "../images/animations/rotating.gif");
	}

	@Override
	public Widget getViewAsWidget() {
		return this;
	}

	@Override
	public Button getSaveListButton() {
		return saveList;
	}

	@Override
	public Button getResetListButton() {
		return resetList;
	}

	@Override
	public Button getAddMemberButton() {
		return addMember;
	}

	@Override
	public ListBox addMemberNameListBox(int row) {
		ListBox listBox = new ListBox();
		grid.setWidget(row, 0, listBox);
		return listBox;
	}

	@Override
	public void addMemberNameLabel(int row, String name) {
		grid.setWidget(row, 0, new  Label(name));
	}

	@Override
	public void addMemberStatus(int row, String status) {
		grid.setWidget(row, 1, new Label(status));
	}

	@Override
	public TextBox getListNameTextBox() {
		return listTextBox;
	}

	@Override
	public void toggleLoading(boolean loading) {
		loadingImage.setVisible(loading);
		gridPanel.setVisible(!loading);
	}

	@Override
	public void setEmpty(boolean empty) {
		if (empty) {
			loadingImage.setVisible(!empty);
		}
		gridPanel.setVisible(!empty);
		emptyPanel.setVisible(empty);		
	}
}
