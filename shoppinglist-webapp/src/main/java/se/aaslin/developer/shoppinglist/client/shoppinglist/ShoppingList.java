package se.aaslin.developer.shoppinglist.client.shoppinglist;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class ShoppingList implements EntryPoint {
	
	public interface ShoppingListUIBinder extends UiBinder<HTMLPanel, ShoppingList>{
	}
	
	public ShoppingListUIBinder uiBinder = GWT.create(ShoppingListUIBinder.class);
	
	private String DIV_ID = "gwt_shoppingListPlaceHolder";
	
	@Override
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get(DIV_ID);
		
		HTMLPanel outerPanel = uiBinder.createAndBindUi(this);
		rootPanel.add(outerPanel);
	}

}
