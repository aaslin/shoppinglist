package se.aaslin.developer.shoppinglist.client.header.view;

import se.aaslin.developer.shoppinglist.client.header.presenter.HeaderPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class HeaderView extends Composite implements HeaderPresenter.ViewDisplay {
	public interface HeaderViewUIBinder extends UiBinder<HTMLPanel, HeaderView> {
	}
	
	private HeaderViewUIBinder uiBinder = GWT.create(HeaderViewUIBinder.class);
	
	@UiField Hyperlink logoutBtn;
	
	public HeaderView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget getViewAsWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getLogout() {
		return logoutBtn;
	}
}
