package se.aaslin.developer.shoppinglist.android.app.exception;

public class NotFoundException extends HttpException {

	private static final long serialVersionUID = 152434039190770044L;

	public NotFoundException() {
		super(410);
	}
}
