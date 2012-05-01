package se.aaslin.developer.shoppinglist.shared.exception;

import java.io.Serializable;

public class NotAuthorizedException extends Exception implements Serializable {

	public NotAuthorizedException() {
	}

	public NotAuthorizedException(String user) {
		super("An operation was attempted by a user, " + user + ", that is not authorized to perform that operation");
	}
}
