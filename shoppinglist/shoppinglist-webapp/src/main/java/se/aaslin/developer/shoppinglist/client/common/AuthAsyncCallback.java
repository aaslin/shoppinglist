package se.aaslin.developer.shoppinglist.client.common;

import se.aaslin.developer.shoppinglist.shared.exception.SessionExpiredException;

import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AuthAsyncCallback<T> implements AsyncCallback<T> {

	@Override
	public void onFailure(Throwable caught) {
		if (caught instanceof SessionExpiredException) {
			redirectToLogin();
		} else {
			onError(caught);
		}
	}
	
	public abstract void onError(Throwable caught);
	
	private void redirectToLogin() {
		UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
		urlBuilder.setPath("shoppinglist/login.jsp");
		Window.Location.assign(urlBuilder.buildString());
	}
}
