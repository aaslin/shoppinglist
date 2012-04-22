package se.aaslin.developer.shoppinglist.client.content.shoppinglists.view;

import java.util.List;

import se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter.ShoppingListFormPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
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
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

public class ShoppingListFormView extends Composite implements ShoppingListFormPresenter.View {
	public interface ShoppingListFormViewUIBinder extends UiBinder<HTMLPanel, ShoppingListFormView> {
	}
	
	interface Style extends CssResource {
		
		String cell();
		
		String cellHeader();
		
		String nobg();
		
		String cellListBox();
		
		String cellLeft();
		
		String removeBtn();
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
	
	@UiField Style style;
	
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
	public void addMemberNameLabel(int row, String name) {
		grid.setWidget(row, 0, new  Label(name));
		grid.getCellFormatter().addStyleName(row, 0, style.cell());
		grid.getCellFormatter().addStyleName(row, 0, style.cellLeft());
	}

	@Override
	public void addMemberStatus(int row, String status) {
		grid.setWidget(row, 1, new Label(status));
		grid.getCellFormatter().addStyleName(row, 1, style.cell());
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

	@Override
	public ListBox addMembersListBox(List<String> users, int row) {
		ListBox listBox = new ListBox();
		for (String user : users) {
			listBox.addItem(user);
		}
		grid.setWidget(row, 0, listBox);
		grid.getCellFormatter().addStyleName(row, 0, style.cell());
		grid.getCellFormatter().addStyleName(row, 0, style.cellLeft());
		grid.getCellFormatter().addStyleName(row, 0, style.cellListBox());
		
		return listBox;
	}
	
	@Override
	public void addHeaderToMembersGrid() {
		grid.setWidget(0, 0, new Label("Member"));
		grid.getCellFormatter().addStyleName(0, 0, style.cellHeader());
		grid.getCellFormatter().addStyleName(0, 0, style.nobg());
		grid.setWidget(0, 1, new Label("Status"));
		grid.getCellFormatter().addStyleName(0, 1, style.cellHeader());
	}

	@Override
	public void clearMembersGrid() {
		grid.removeAllRows();	
	}
	
	@Override
	public HasValue<Boolean> addRemoveButton(int row) {
		ToggleButton removeBtn = new ToggleButton(new Image(GWT.getModuleBaseURL() + "../images/delete.png"), new Image(GWT.getModuleBaseURL() + "../images/animations/loader_small.gif"));
		removeBtn.setTitle("ta bort");
		grid.setWidget(row, 3, removeBtn);
		grid.getCellFormatter().setStyleName(row, 3, style.removeBtn());	
		return removeBtn;
	}

	@Override
	public void removeMemberRow(int row) {
		grid.removeRow(row);
	}
}
