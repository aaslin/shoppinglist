package se.aaslin.developer.shoppinglist.shared.exception;

public class ServerSideException extends Exception {
	
	private static final long serialVersionUID = -4219727415431749579L;

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
