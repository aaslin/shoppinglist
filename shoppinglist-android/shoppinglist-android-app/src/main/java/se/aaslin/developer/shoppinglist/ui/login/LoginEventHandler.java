package se.aaslin.developer.shoppinglist.ui.login;

import se.aaslin.developer.roboeventbus.event.RoboEventHandler;

public interface LoginEventHandler extends RoboEventHandler{

	void postInfo(LoginEvent event);
}
