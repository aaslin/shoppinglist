package se.aaslin.developer.shoppinglist.client.content.profile.view;

import se.aaslin.developer.shoppinglist.client.content.profile.presenter.ProfilePasswordPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;

public class ProfilePasswordView extends Composite implements ProfilePasswordPresenter.View {
	public interface ProfilePasswordViewUIBinder extends UiBinder<HTMLPanel, ProfilePasswordView> {		
	}
	
	ProfilePasswordViewUIBinder uiBinder = GWT.create(ProfilePasswordViewUIBinder.class);
	
	@UiField PasswordTextBox newPassword1;
	@UiField PasswordTextBox newPassword2;
	@UiField Image okPassword1;
	@UiField Image notOkPassword1;
	@UiField Image okPassword2;
	@UiField Image notOkPassword2;
	@UiField Button changePassword;
	@UiField Panel password1Panel;
	@UiField Panel password2Panel;
	
	public ProfilePasswordView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget getViewAsWidget() {
		return this;
	}

	@Override
	public HasKeyUpHandlers getPasswordTextBox1() {
		return newPassword1;
	}

	@Override
	public HasKeyUpHandlers getPasswordTextBox2() {
		return newPassword2;
	}

	@Override
	public void setPassword1Status(boolean isOk) {
		okPassword1.setVisible(isOk);
		notOkPassword1.setVisible(!isOk);
	}

	@Override
	public void setPassword2Status(boolean isOk) {
		okPassword2.setVisible(isOk);
		notOkPassword2.setVisible(!isOk);
	}

	@Override
	public void enableChangeButton(boolean enabled) {
		changePassword.setEnabled(enabled);
	}

	@Override
	public HasClickHandlers getChangeButton() {
		return changePassword;
	}

	@Override
	public String getPassword1() {
		return newPassword1.getText();
	}

	@Override
	public String getPassword2() {
		return newPassword2.getText();
	}

	@Override
	public void setPassword1StatusVisibility(boolean visible) {
		password1Panel.setVisible(visible);
	}
	
	@Override
	public void setPassword2StatusVisibility(boolean visible) {
		password2Panel.setVisible(visible);
	}
}
