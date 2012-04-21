package se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter;

import java.util.List;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListChangeEvent;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListEmptyEvent;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListUpdateEvent;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListUpdateEventHandler;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListsServiceAsync;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

public class ShoppingListBoxPresenter {
	public interface View extends Display {
		public static class BoxElement {
			
			private String name;

			public BoxElement(String name) {
				this.name = name;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
		}
		
		HasChangeHandlers getShoppingListBox();
		
		int getSelectedIndex();
		
		void clearShoppingListBox();
		
		void addShoppingListToBox(BoxElement element);
		
		void setSelectedIndex(int index);

		Button getNewListButton();
		
		Button getRemoveButton();
	}
	
	public interface Model {

		List<ShoppingListDTO> getShoppingListDTOs();
		
		String getCurrentUser();
	}
	
	View display;
	Model model;
	EventBus eventBus;
	ShoppingListsServiceAsync srv;

	public ShoppingListBoxPresenter(View display, Model model, EventBus eventBus, ShoppingListsServiceAsync srv) {
		this.display = display;
		this.model = model;
		this.eventBus = eventBus;
		this.srv = srv;
		
		fetchShoppingLists();
		bind();
	}

	private void fetchShoppingLists() {
		srv.getShoppingLists(new AsyncCallback<List<ShoppingListDTO>>() {
			
			@Override
			public void onSuccess(List<ShoppingListDTO> result) {
				model.getShoppingListDTOs().clear();
				model.getShoppingListDTOs().addAll(result);
				updateListBox(0);
				if (model.getShoppingListDTOs().size() > 0) {
					display.getRemoveButton().setEnabled(true);
					eventBus.fireEvent(new ShoppingListChangeEvent(model.getShoppingListDTOs().get(0)));
				} else {
					display.getRemoveButton().setEnabled(false);
					eventBus.fireEvent(new ShoppingListEmptyEvent());
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	private void updateListBox(int index) {
		display.clearShoppingListBox();
		for (ShoppingListDTO dto : model.getShoppingListDTOs()) {
			display.addShoppingListToBox(new View.BoxElement(dto.getName()));
		}

		display.setSelectedIndex(index);
	}

	
	private void bind() {
		eventBus.addHandler(ShoppingListUpdateEvent.TYPE, new ShoppingListUpdateEventHandler() {
			
			@Override
			public void onUpdate(ShoppingListUpdateEvent event) {
				updateList(event);
			}
		});
		
		display.getShoppingListBox().addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				eventBus.fireEvent(new ShoppingListChangeEvent(getSelectedShoppingList()));		
			}
		});
		
		display.getNewListButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				newList();
			}
		});
		
		display.getRemoveButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				removeList();
			}
		});
	}
	
	private void updateList(ShoppingListUpdateEvent event) {
		int index = model.getShoppingListDTOs().indexOf(event.getShoppingListDTO());
		ShoppingListDTO shoppingListDTO = model.getShoppingListDTOs().get(index);
		shoppingListDTO.setName(event.getShoppingListDTO().getName());
		updateListBox(display.getSelectedIndex());
	}
	
	private void newList() {
		ShoppingListDTO shoppingListDTO = new ShoppingListDTO();
		shoppingListDTO.setOwnerUsername(model.getCurrentUser());
		shoppingListDTO.setName("\"New Shopping List\"");
		
		saveShoppingList(shoppingListDTO);
	}
	
	private void saveShoppingList(ShoppingListDTO shoppingListDTO) {
		srv.saveShoppingList(shoppingListDTO, new AsyncCallback<List<ShoppingListDTO>>() {
			
			@Override
			public void onSuccess(List<ShoppingListDTO> result) {
				model.getShoppingListDTOs().clear();
				model.getShoppingListDTOs().addAll(result);
				int index = model.getShoppingListDTOs().size() - 1;
				updateListBox(index);
				display.getRemoveButton().setEnabled(true);
				eventBus.fireEvent(new ShoppingListChangeEvent(model.getShoppingListDTOs().get(index)));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());	
			}
		});
	}
	
	private void removeList() {
		boolean confirmed = Window.confirm("Are you sure you want to delete the shopping list: " + getSelectedShoppingList().getName());
		if(confirmed) {
			removeShoppingList(getSelectedShoppingList());
		}
	}
	
	private void removeShoppingList(ShoppingListDTO shoppingListDTO) {
		srv.removeShoppingList(shoppingListDTO, new AsyncCallback<List<ShoppingListDTO>>() {
			
			@Override
			public void onSuccess(List<ShoppingListDTO> result) {
				model.getShoppingListDTOs().clear();
				model.getShoppingListDTOs().addAll(result);
				int index = model.getShoppingListDTOs().size() - 1;
				if (index >= 0) {
					updateListBox(index);
					eventBus.fireEvent(new ShoppingListChangeEvent(model.getShoppingListDTOs().get(index)));
				} else {
					display.getRemoveButton().setEnabled(false);
					updateListBox(0);
					eventBus.fireEvent(new ShoppingListEmptyEvent());
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}
	
	private ShoppingListDTO getSelectedShoppingList() {
		int index = display.getSelectedIndex();
		ShoppingListDTO shoppingList = model.getShoppingListDTOs().get(index);
		
		return shoppingList;
	}
}
