package se.aaslin.developer.shoppinglist.client.entrypoint;

import se.aaslin.developer.shoppinglist.client.common.AuthAsyncCallback;
import se.aaslin.developer.shoppinglist.client.content.ContentActivityMapper;
import se.aaslin.developer.shoppinglist.client.content.ShoppingListPlaceHistoryMapper;
import se.aaslin.developer.shoppinglist.client.entrypoint.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.client.entrypoint.service.AuthenticationServiceAsync;
import se.aaslin.developer.shoppinglist.client.header.HeaderActivityMapper;
import se.aaslin.developer.shoppinglist.client.place.DashboardPlace;
import se.aaslin.developer.shoppinglist.shared.dto.AuthenticationDTO;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ShoppingList implements EntryPoint {
	public interface ShoppingListUIBinder extends UiBinder<HTMLPanel, ShoppingList>{
	}
	
	public ShoppingListUIBinder uiBinder = GWT.create(ShoppingListUIBinder.class);
	
	private static final String HEADER_ID = "gwt_topPlaceHolder";
	private static final String CONTENT_ID = "gwt_contentPlaceHolder";
//	private static final String FOOTER_ID = "gwt_footerPlaceHolder";
	
	private HTMLPanel outerPanel;
	
	private SimplePanel headerView = new SimplePanel();
	private SimplePanel contentView = new SimplePanel();
	private Place defaultPlace = new DashboardPlace("");
	
	@Override
	public void onModuleLoad() {
		AuthenticationServiceAsync srv = GWT.create(AuthenticationService.class);
		srv.getCurrentAuthentication(new AuthAsyncCallback<AuthenticationDTO>() {
			
			@Override
			public void onSuccess(AuthenticationDTO result) {
				setupAndStartModule();	
			}
			
			@Override
			public void onError(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}
	
	public HTMLPanel getContentPanel() {
		return outerPanel;
	}

	private void setupAndStartModule() {
		EventBus eventBus = GWT.create(SimpleEventBus.class);
		PlaceController placeController = new PlaceController(eventBus);
		
		ActivityMapper headerActivityMapper = new HeaderActivityMapper();
		ActivityManager headerActivityManager = new ActivityManager(headerActivityMapper, eventBus);
		headerActivityManager.setDisplay(headerView);

		RootPanel headerPanel = RootPanel.get(HEADER_ID);
		headerPanel.add(headerView);
		
		ActivityMapper contentActivityMapper = new ContentActivityMapper();
		ActivityManager contentActivityManager = new ActivityManager(contentActivityMapper, eventBus);
		contentActivityManager.setDisplay(contentView);

		RootPanel contentPanel = RootPanel.get(CONTENT_ID);
		contentPanel.add(contentView);
		
		ShoppingListPlaceHistoryMapper placeHistoryMapper = GWT.create(ShoppingListPlaceHistoryMapper.class);
		PlaceHistoryHandler placeHistoryHandler = new PlaceHistoryHandler(placeHistoryMapper);
		placeHistoryHandler.register(placeController, eventBus, defaultPlace);
		
		placeHistoryHandler.handleCurrentHistory();
	}
}
