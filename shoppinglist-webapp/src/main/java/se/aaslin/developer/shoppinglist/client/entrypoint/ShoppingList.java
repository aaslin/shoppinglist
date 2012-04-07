package se.aaslin.developer.shoppinglist.client.entrypoint;

import se.aaslin.developer.shoppinglist.client.content.ContentActivityMapper;
import se.aaslin.developer.shoppinglist.client.content.ContentView;
import se.aaslin.developer.shoppinglist.client.content.ShoppginListPlaceHistoryMapper;
import se.aaslin.developer.shoppinglist.client.header.HeaderActivityMapper;
import se.aaslin.developer.shoppinglist.client.place.DashboardPlace;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class ShoppingList implements EntryPoint {
	
	public interface ShoppingListUIBinder extends UiBinder<HTMLPanel, ShoppingList>{
	}
	
	public ShoppingListUIBinder uiBinder = GWT.create(ShoppingListUIBinder.class);
	
	private static final String HEADER_ID = "gwt_headerPlaceHolder";
	private static final String DIV_ID = "gwt_shoppingListPlaceHolder";
	
	private HTMLPanel outerPanel;
	
	private SimplePanel headerWidget = new SimplePanel();
	private ContentView contentView = new ContentView();
	private Place defaultPlace = new DashboardPlace("");
	
	@Override
	public void onModuleLoad() {
		EventBus eventBus = new SimpleEventBus();
		PlaceController placeController = new PlaceController(eventBus);
		
		ActivityMapper headerActivityMapper = new HeaderActivityMapper();
		ActivityManager headerActivityManager = new ActivityManager(headerActivityMapper, eventBus);
		headerActivityManager.setDisplay(headerWidget);

		RootPanel headerPanel = RootPanel.get(HEADER_ID);
		headerPanel.add(headerWidget);
		
		ActivityMapper contentActivityMapper = new ContentActivityMapper();
		ActivityManager contentActivityManager = new ActivityManager(contentActivityMapper, eventBus);
		contentActivityManager.setDisplay(contentView);

		RootPanel contentPanel = RootPanel.get(DIV_ID);
		contentPanel.add(contentView);
		
		ShoppginListPlaceHistoryMapper placeHistoryMapper = GWT.create(ShoppginListPlaceHistoryMapper.class);
		PlaceHistoryHandler placeHistoryHandler = new PlaceHistoryHandler(placeHistoryMapper);
		placeHistoryHandler.register(placeController, eventBus, defaultPlace);
		
		placeHistoryHandler.handleCurrentHistory();
	}
	
	public HTMLPanel getContentPanel() {
		return outerPanel;
	}
}
