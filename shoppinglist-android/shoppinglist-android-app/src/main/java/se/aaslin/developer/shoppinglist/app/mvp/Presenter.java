package se.aaslin.developer.shoppinglist.app.mvp;


public abstract class Presenter<T extends Place> {
	
	public final void create() {
		onCreate();
	}
	
	public final void start() {
		onStart();
	}
	
	public final void stop() {
		onStop();
	}
	
	public final void destroy() {
		onDestroy();
	}
	
	public final void bind() {
		onBind();
	}
	
	protected void onBind() {
	}

	protected void onCreate() {
	}
	
	protected void onStart() {
	}
	
	protected void onStop() {
	}
	
	protected void onDestroy() {	
	}
}
