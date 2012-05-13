package se.aaslin.developer.shoppinglist.android.ui.login;

import se.aaslin.developer.roboeventbus.event.RoboEventHandler;

public interface LoginEventHandler extends RoboEventHandler{

	void postInfo(LoginEvent event);
}
