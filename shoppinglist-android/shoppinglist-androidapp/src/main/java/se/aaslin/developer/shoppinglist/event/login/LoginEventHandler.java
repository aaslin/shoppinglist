package se.aaslin.developer.shoppinglist.event.login;

import se.aaslin.developer.roboeventbus.event.EventHandler;

public interface LoginEventHandler extends EventHandler{

	void postInfo(LoginEvent event);
}
