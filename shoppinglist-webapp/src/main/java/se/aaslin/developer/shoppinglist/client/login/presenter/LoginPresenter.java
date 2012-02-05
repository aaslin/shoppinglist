package se.aaslin.developer.shoppinglist.client.login.presenter;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.common.Presenter;
import se.aaslin.developer.shoppinglist.client.login.service.LoginServiceAsync;
import se.aaslin.developer.shoppinglist.client.shoppinglist.ShoppingListID;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
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
	
	private LoginServiceAsync srv;
	private ViewDisplay display;

	public LoginPresenter(ViewDisplay display, LoginServiceAsync srv) {
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
				
				srv.login(uname, pass, new AsyncCallback<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
						if(result){
							Window.alert("Login Succeeded");
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
