package se.aaslin.developer.shoppinglist.client.content.profile.view;

import se.aaslin.developer.shoppinglist.client.content.profile.presenter.ProfileUserAdminPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProfileUserAdminView extends Composite implements ProfileUserAdminPresenter.View {
	public interface ProfileUserAdminViewUIBinder extends UiBinder<HTMLPanel, ProfileUserAdminView> {
	}
	
	ProfileUserAdminViewUIBinder uiBinder = GWT.create(ProfileUserAdminViewUIBinder.class);

	@UiField Button auth;
	@UiField Button session;
	
	public ProfileUserAdminView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget getViewAsWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getAuthButton() {
		return auth;
	}

	@Override
	public HasClickHandlers getSesionButon() {
		return session;
	}
}
