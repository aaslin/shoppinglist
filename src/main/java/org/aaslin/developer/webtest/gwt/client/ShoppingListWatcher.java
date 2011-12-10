package org.aaslin.developer.webtest.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class ShoppingListWatcher implements EntryPoint {
	private Button clickMeButton;

	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();

		clickMeButton = new Button();
		rootPanel.add(clickMeButton);
		clickMeButton.setText("Click me!");
		clickMeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Hello, GWT World!");
			}
		});
	}
}
