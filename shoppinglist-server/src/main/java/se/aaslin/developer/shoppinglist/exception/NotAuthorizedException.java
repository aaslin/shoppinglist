package se.aaslin.developer.shoppinglist.exception;

public class NotAuthorizedException extends Exception {

	private static final long serialVersionUID = -6820540836951786210L;

	public NotAuthorizedException(String user) {
		super(String.format("An operation was attempted by a user, %s, that is not authorized to perform that operation", user));
	}
}
