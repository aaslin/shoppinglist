package se.aaslin.developer.shoppinglist.shared.exception;


public class NotAuthorizedException extends ServerSideException {
	private static final long serialVersionUID = 4873598143318854739L;

	public NotAuthorizedException() {
	}

	public NotAuthorizedException(String user) {
		super("An operation was attempted by a user, " + user + ", that is not authorized to perform that operation");
	}
}
