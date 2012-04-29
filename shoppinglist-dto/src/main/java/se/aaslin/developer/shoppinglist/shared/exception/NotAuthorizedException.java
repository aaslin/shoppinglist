package se.aaslin.developer.shoppinglist.shared.exception;

public class NotAuthorizedException extends Exception {

	private static final long serialVersionUID = -6820540836951786210L;
	
	public NotAuthorizedException() {
	}

	public NotAuthorizedException(String user) {
		super("An operation was attempted by a user, " + user + ", that is not authorized to perform that operation");
	}
}
