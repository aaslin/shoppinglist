package se.aaslin.developer.shoppinglist.client.shoppinglist;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;

public class ShoppingList implements EntryPoint {
	
	public interface ShoppingListUIBinder extends UiBinder<HTMLPanel, ShoppingList>{
	}
	
	public ShoppingListUIBinder uiBinder = GWT.create(ShoppingListUIBinder.class);
	
	private String DIV_ID = "gwt_shoppingListPlaceHolder";
	
	private HTMLPanel outerPanel;
	
	@Override
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get(DIV_ID);
		
		outerPanel = uiBinder.createAndBindUi(this);
		rootPanel.add(outerPanel);
		
		new ShoppingListController(this).initHistory();
	}

	public HasWidgets getContentPanel() {
		return outerPanel;
	}
}
