package se.aaslin.developer.shoppinglist.client.header.view;

import se.aaslin.developer.shoppinglist.client.header.presenter.HeaderPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class HeaderView extends Composite implements HeaderPresenter.ViewDisplay {
	public interface HeaderViewUIBinder extends UiBinder<HTMLPanel, HeaderView> {
	}
	
	public interface Style extends CssResource {
		
		String selected();
	}
	
	private HeaderViewUIBinder uiBinder = GWT.create(HeaderViewUIBinder.class);
	
	@UiField Hyperlink logoutBtn;
	@UiField Hyperlink home;
	@UiField Hyperlink shoppinglists;
	@UiField Hyperlink profile;
	
	@UiField Style style;
	
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

	@Override
	public void setHomeSelected() {
		home.setStyleName(style.selected());
	}

	@Override
	public void setShoppingListsSelected() {
		shoppinglists.setStyleName(style.selected());
	}

	@Override
	public void setProfileSelected() {
		profile.setStyleName(style.selected());
	}
}
