package se.aaslin.developer.shoppinglist.event.login;

import se.aaslin.developer.roboeventbus.event.RoboEvent;

public class LoginEvent extends RoboEvent<LoginEventHandler>{

	public static final Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();
	
	private boolean succeded;
	
	public LoginEvent(boolean succeded) {
		this.succeded = succeded;
	}
	
	public boolean isSucceded() {
		return succeded;
	}

	@Override
	public void dispatch(LoginEventHandler handler) {
		handler.postInfo(this);
	}

	@Override
	public se.aaslin.developer.roboeventbus.event.RoboEvent.Type<LoginEventHandler> getType() {
		return TYPE;
	}

}
