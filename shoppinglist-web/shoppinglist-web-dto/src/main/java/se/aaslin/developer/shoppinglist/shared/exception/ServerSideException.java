package se.aaslin.developer.shoppinglist.shared.exception;

import java.io.Serializable;

public abstract class ServerSideException extends Exception implements Serializable {
	private static final long serialVersionUID = 1185483843867138655L;

	public ServerSideException() {
		super();
	}

	public ServerSideException(String message) {
		super(message);
	}

	public ServerSideException(Throwable cause) {
		super(cause);
	}
}
