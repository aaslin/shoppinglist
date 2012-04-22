package se.aaslin.developer.shoppinglist.client.content.profile.view;

import se.aaslin.developer.shoppinglist.client.content.profile.presenter.ProfilePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;


public class ProfileView extends Composite implements ProfilePresenter.View {
	public interface ProfileViewUIBinder extends UiBinder<HTMLPanel, ProfileView> {
	}
	
	ProfileViewUIBinder uiBinder = GWT.create(ProfileViewUIBinder.class);
		
	public ProfileView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget getViewAsWidget() {
		// TODO Auto-generated method stub
		return null;
	}

}
