package se.aaslin.developer.shoppinglist.android.app.exception;

public class AuthenticationFailedException extends HttpException {
	
	private static final long serialVersionUID = 7175837135601427096L;

	public AuthenticationFailedException() {
		super(403);
	}
}
