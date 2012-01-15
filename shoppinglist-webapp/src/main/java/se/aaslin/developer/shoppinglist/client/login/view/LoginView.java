package se.aaslin.developer.shoppinglist.client.login.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import se.aaslin.developer.shoppinglist.client.login.presenter.LoginPresenter;

public class LoginView extends Composite implements LoginPresenter.ViewDisplay{
	
	public interface LoginViewUIBinder extends UiBinder<HTMLPanel, LoginView>{
	}
	
	public LoginViewUIBinder uiBinder = GWT.create(LoginViewUIBinder.class);

	@UiField TextBox username;
	@UiField TextBox password;
	@UiField Label info;
	@UiField Button loginButton;
	
	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget getViewAsWidget() {
		return this;
	}

	@Override
	public HasText getUsername() {
		return username;
	}

	@Override
	public HasText getPassword() {
		return password;
	}

	@Override
	public Button getLoginButton() {
		return loginButton;
	}

	@Override
	public Label getInfo() {
		return info;
	}

}
