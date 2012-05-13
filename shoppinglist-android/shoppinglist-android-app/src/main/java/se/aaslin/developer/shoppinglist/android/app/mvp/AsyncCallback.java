package se.aaslin.developer.shoppinglist.android.app.mvp;

public interface AsyncCallback<T> {
	
	public void onSuccess(T result);
	
	public void onFailure(Throwable caught);
}
