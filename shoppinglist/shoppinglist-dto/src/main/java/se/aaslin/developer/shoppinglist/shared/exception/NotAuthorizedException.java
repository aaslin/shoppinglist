package se.aaslin.developer.shoppinglist.shared.exception;

import java.io.Serializable;

public class NotAuthorizedException extends ServerSideException implements Serializable {

	private static final long serialVersionUID = 3565117014909739393L;

	public NotAuthorizedException() {
	}

	public NotAuthorizedException(String user) {
		super("An operation was attempted by a user, " + user + ", that is not authorized to perform that operation");
	}
}
