package se.aaslin.developer.shoppinglist.client.content.dashboard.presenter;

import se.aaslin.developer.shoppinglist.client.common.Display;

public class DashboardPresenter  {
	public interface ViewDisplay extends Display {
		
	}
	
	private ViewDisplay display;
	
	public DashboardPresenter(ViewDisplay display) {
		this.display = display;
		bind();
	}
	
	private void bind() {
		
	}
}
