package se.aaslin.developer.shoppinglist.client.content;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;

public class ContentView extends Composite implements AcceptsOneWidget{
	public interface ContentViewUIBinder extends UiBinder<HTMLPanel, ContentView> {
	}
	
	private ContentViewUIBinder uiBinder = GWT.create(ContentViewUIBinder.class);
	
	@UiField HTMLPanel contentPanel;
	
	public ContentView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setWidget(IsWidget widget) {
		contentPanel.add(widget);
	}	
}
