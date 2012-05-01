package se.aaslin.developer.shoppinglist.client.login.presenter;

import java.util.UUID;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.common.Presenter;
import se.aaslin.developer.shoppinglist.client.login.service.LoginViewServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;

public class LoginPresenter implements Presenter{
	public interface ViewDisplay extends Display {
		
		HasText getUsername();
		
		HasText getPassword();
		
		Button getLoginButton();
		
		Label getInfo();
	}
	
	private LoginViewServiceAsync srv;
	private ViewDisplay display;

	public LoginPresenter(ViewDisplay display, LoginViewServiceAsync srv) {
		this.srv = srv;
		this.display = display;
		bind();
	}

	@Override
	public void initContainer(HasWidgets container) {
		container.add(display.getViewAsWidget());
	}
	
	private void bind(){
		display.getLoginButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String uname = display.getUsername().getText();
				String pass = display.getPassword().getText();
				
				srv.login(uname, pass, new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String result) {
						if(result != null){
							String url = GWT.getHostPageBaseURL() + "index.jsp";
							Window.Location.assign(url);
						}else{
							Label info = display.getInfo();
							info.setText("Wrong username or password");
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});
			}
		});
	}
}
