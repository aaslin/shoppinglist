package se.aaslin.developer.shoppinglist.client.content.profile.view;

import se.aaslin.developer.shoppinglist.client.content.profile.presenter.ProfileSubMenuPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class ProfileSubMenuView extends Composite implements ProfileSubMenuPresenter.View {
	public interface ProfileSubMenuViewUIBinder extends UiBinder<HTMLPanel, ProfileSubMenuView> {
	}
	
	public interface Style extends CssResource {
		
		String selected();
	}
	
	ProfileSubMenuViewUIBinder uiBinder = GWT.create(ProfileSubMenuViewUIBinder.class);
	
	@UiField Hyperlink password;
	@UiField Hyperlink userAdmin;
	
	@UiField Style style;
	
	public ProfileSubMenuView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget getViewAsWidget() {
		return this;
	}

	@Override
	public void setUserAdminSelected() {
		userAdmin.setStyleName(style.selected());
	}

	@Override
	public void setPasswordSelected() {
		password.setStyleName(style.selected());
	}

}
