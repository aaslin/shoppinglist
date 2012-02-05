package se.aaslin.developer.shoppinglist.client.login;

import se.aaslin.developer.shoppinglist.client.login.presenter.LoginPresenter;
import se.aaslin.developer.shoppinglist.client.login.service.LoginService;
import se.aaslin.developer.shoppinglist.client.login.service.LoginServiceAsync;
import se.aaslin.developer.shoppinglist.client.login.view.LoginView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class Login implements EntryPoint {
	public interface LoginUIBinder extends UiBinder<HTMLPanel, Login>{
	}
	
	public LoginUIBinder uiBinder = GWT.create(LoginUIBinder.class);
	
	private String DIV_ID = "gwt_loginPlaceHolder";
	
	private HTMLPanel outerPanel;
	
	@Override
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get(DIV_ID);
		
		outerPanel = uiBinder.createAndBindUi(this);
		rootPanel.add(outerPanel);
		
		LoginServiceAsync srv = GWT.create(LoginService.class);
		new LoginPresenter(new LoginView(), srv).initContainer(outerPanel);
	}
}
