package se.aaslin.developer.shoppinglist.app.mvp;

import android.os.Bundle;

public abstract class Presenter {
	
	public final void create(Bundle savedInstanceState) {
		onCreate(savedInstanceState);
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
	
	protected void onCreate(Bundle savedInstanceState) {
	}
	
	protected void onStart() {
	}
	
	protected void onStop() {
	}
	
	protected void onDestroy() {	
	}
}
