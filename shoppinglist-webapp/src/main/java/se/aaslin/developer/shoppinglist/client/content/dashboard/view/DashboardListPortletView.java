package se.aaslin.developer.shoppinglist.client.content.dashboard.view;

import se.aaslin.developer.shoppinglist.client.content.dashboard.presenter.DashboardListPortletPresenter;

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
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

public class DashboardListPortletView extends Composite implements DashboardListPortletPresenter.View {
	public interface DashboardListPortletViewUIBinder extends UiBinder<HTMLPanel, DashboardListPortletView> {
	}
	
	public interface Style extends CssResource {
		
		String cell();
		
		String cellHeader();
		
		String nobg();
		
		String removeBtn();
		
		String cellLeft();
		
		String textBox();
	}

	DashboardListPortletViewUIBinder uiBinder = GWT.create(DashboardListPortletViewUIBinder.class);
	
	@UiField Panel gridPanel;
	@UiField Panel emptyPanel;
	@UiField Image loadingImage;
	@UiField FlexTable grid;
	@UiField Button newItem;
	@UiField Button save;
	@UiField Button reset;
	@UiField Label label;
//	@UiField Label ownerLabel;
 	
	@UiField Style style;
	
	public DashboardListPortletView() {
		initWidget(uiBinder.createAndBindUi(this));
		loadingImage.setUrl(GWT.getModuleBaseURL() + "../images/animations/rotating.gif");
	}

	@Override
	public Widget getViewAsWidget() {
		return this;
	}

	@Override
	public void addHeader() {
		grid.setWidget(0, 0, new Label("Item"));
		grid.getCellFormatter().addStyleName(0, 0, style.cellHeader());
		grid.getCellFormatter().addStyleName(0, 0, style.nobg());
		grid.setWidget(0, 1, new Label("Amount"));
		grid.getCellFormatter().addStyleName(0, 1, style.cellHeader());
		grid.setWidget(0, 2, new Label("Comment"));
		grid.getCellFormatter().addStyleName(0, 2, style.cellHeader());
	}

	@Override
	public TextBox addNameTextBox(int row, String name) {
		TextBox tb = new TextBox();
		tb.setText(name);
		tb.setStyleName(style.textBox());
		grid.setWidget(row, 0, tb);
		grid.getCellFormatter().addStyleName(row, 0, style.cell());
		grid.getCellFormatter().addStyleName(row, 0, style.cellLeft());
		
		return tb;
	}

	@Override
	public TextBox addAmountTextBox(int row, String amount) {
		TextBox tb = new TextBox();
		tb.setText(amount);
		tb.setStyleName(style.textBox());
		grid.setWidget(row, 1, tb);
		grid.getCellFormatter().addStyleName(row, 1, style.cell());
		
		return tb;
	}

	@Override
	public TextBox addCommentTextBox(int row, String comment) {
		TextBox tb = new TextBox();
		tb.setText(comment);
		tb.setStyleName(style.textBox());
		grid.setWidget(row, 2, tb);
		grid.getCellFormatter().addStyleName(row, 2, style.cell());
		
		return tb;
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

	@Override
	public void toggleLoading(boolean loading) {
		loadingImage.setVisible(loading);
		gridPanel.setVisible(!loading);
	}

	@Override
	public void clear() {
		grid.removeAllRows();
		addHeader();
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
	public void setLabel(String text) {
		label.setText(text);
	}

	@Override
	public void setOwner(String username) {
//		label.setText(username);
	}
}
