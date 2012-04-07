package se.aaslin.developer.shoppinglist.client.header.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import se.aaslin.developer.shoppinglist.client.header.presenter.HeaderPresenter;;

public class HeaderView extends Composite implements HeaderPresenter.ViewDisplay {
	public interface HeaderViewUIBinder extends UiBinder<HTMLPanel, HeaderView> {
	}
	
	private HeaderViewUIBinder uiBinder = GWT.create(HeaderViewUIBinder.class);
	
	@UiField Button logoutBtn;
	
	public HeaderView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget getViewAsWidget() {
		return this;
	}

	@Override
	public Button getLogoutButton() {
		return logoutBtn;
	}

}
