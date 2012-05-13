package se.aaslin.developer.shoppinglist.android.app.exception;

public class HttpException extends Exception { 
	private static final long serialVersionUID = -5794104328427127289L; 
	
	public HttpException(int statusCode) {
		super("HTTP status code: " + statusCode);
	}
}
