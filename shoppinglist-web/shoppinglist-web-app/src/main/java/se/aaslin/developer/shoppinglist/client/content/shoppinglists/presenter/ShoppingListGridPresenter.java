package se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter;

import java.util.ArrayList;
import java.util.List;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListChangeEvent;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListChangeEventHandler;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListEmptyEvent;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListEmptyEventHandler;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListNewEvent;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListNewEventHandler;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListsServiceAsync;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;

public class ShoppingListGridPresenter {
	public interface View extends Display {
		
		void addHeader();
		
		TextBox addNameTextBox(int row, String name);
		
		TextBox addAmountTextBox(int row, String amount);
		
		TextBox addCommentTextBox(int row, String comment);
		
		HasValue<Boolean> addRemoveButton(int row);
		
		Button getNewItemButton();
		
		Button getSaveButton();
		
		Button getResetButton();

		void removeRow(int i);
		
		void toggleLoading(boolean loading);
		
		void clear();
		
		void setEmpty(boolean empty);
	}
	
	public interface Model {
		
		void setCurrentShoppingList(ShoppingListDTO shoppingListDTO);
		
		ShoppingListDTO getCurrentShoppingList();
		
		void setShoppingItemDTOs(List<ShoppingItemDTO> shoppingItemDTOs);
		
		List<ShoppingItemDTO> getShoppingItemDTOs();
	}

	View display;
	Model model;
	EventBus eventBus;
	ShoppingListsServiceAsync srv;
	
	public ShoppingListGridPresenter(View display, Model model, EventBus eventBus, ShoppingListsServiceAsync srv) {
		this.display = display;
		this.model = model;
		this.eventBus = eventBus;
		this.srv = srv;
		bind();
	}

	private void bind() {
		eventBus.addHandler(ShoppingListChangeEvent.TYPE, new ShoppingListChangeEventHandler() {
			
			@Override
			public void onChange(ShoppingListChangeEvent event) {
				model.setCurrentShoppingList(event.getShoppingListDTO());
				fetchShoppingItems();
			}
		});
		
		eventBus.addHandler(ShoppingListNewEvent.TYPE, new ShoppingListNewEventHandler() {
			
			@Override
			public void onNew(ShoppingListNewEvent event) {
				display.setEmpty(false);
				model.setCurrentShoppingList(event.getShoppingListDTO());
				updateShoppingItems(new ArrayList<ShoppingItemDTO>());
			}
		});
	
		eventBus.addHandler(ShoppingListEmptyEvent.TYPE, new ShoppingListEmptyEventHandler() {
			
			@Override
			public void onEmpty(ShoppingListEmptyEvent event) {
				display.setEmpty(true);
			}
		});
		
		display.getNewItemButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ShoppingItemDTO dto = new ShoppingItemDTO();
				model.getShoppingItemDTOs().add(dto);
				addAndBindRow(model.getShoppingItemDTOs().size(), dto);
			}
		});
		
		display.getResetButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				fetchShoppingItems();
			}
		});
		
		display.getSaveButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				saveShoppingItems();
			}
		});
	}
	
	private void fetchShoppingItems() {
		display.toggleLoading(true);
		ShoppingListDTO shoppingList = model.getCurrentShoppingList();
		
		srv.getShoppingItems(shoppingList.getID(), new AsyncCallback<List<ShoppingItemDTO>>() {
			
			@Override
			public void onSuccess(List<ShoppingItemDTO> result) {
				updateShoppingItems(result);
				display.getSaveButton().setEnabled(false);
				display.getResetButton().setEnabled(false);
				display.toggleLoading(false);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	private void updateShoppingItems(List<ShoppingItemDTO> result) {
		model.setShoppingItemDTOs(result);

		display.clear();
		display.addHeader();
		
		int row = 1;
		for (final ShoppingItemDTO dto : result) {
			addAndBindRow(row, dto);
			row++;
		}
	}

	private void addAndBindRow(int row, final ShoppingItemDTO dto) {
		display.addNameTextBox(row, dto.getName()).addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				dto.setChanged(true);
				dto.setName(event.getValue());
				display.getSaveButton().setEnabled(true);
				display.getResetButton().setEnabled(true);
			}
		});
		
		display.addAmountTextBox(row, dto.getAmount()).addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				dto.setChanged(true);
				dto.setAmount(event.getValue());
				display.getSaveButton().setEnabled(true);
				display.getResetButton().setEnabled(true);
			}
		});
		
		display.addCommentTextBox(row, dto.getComment()).addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				dto.setChanged(true);
				dto.setComment(event.getValue());
				display.getSaveButton().setEnabled(true);
				display.getResetButton().setEnabled(true);
			}
		});
		
		final HasValue<Boolean> removeButton = display.addRemoveButton(row);
		removeButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if(event.getValue()) {
					remove(dto, removeButton);
				}
			}
		});
	}

	private void saveShoppingItems() {
		List<ShoppingItemDTO> toSave = new ArrayList<ShoppingItemDTO>();
		for (ShoppingItemDTO dto : model.getShoppingItemDTOs()) {
			if (dto.isChanged()) {
				toSave.add(dto);
			}
		}
		
		if(toSave.size() > 0) {
			srv.saveShoppingItems(model.getCurrentShoppingList().getID(), toSave, new AsyncCallback<List<ShoppingItemDTO>>() {
				
				@Override
				public void onSuccess(List<ShoppingItemDTO> result) {
					updateShoppingItems(result);
					display.getSaveButton().setEnabled(false);
					display.getResetButton().setEnabled(false);
				}

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}
			});
		}
	}
	
	private void remove(final ShoppingItemDTO dto, final HasValue<Boolean> removeButton) {
		if (dto.isFromDB()) {
			srv.removeShoppingItem(dto, new AsyncCallback<List<ShoppingItemDTO>>() {
				
				@Override
				public void onSuccess(List<ShoppingItemDTO> result) {
					removeButton.setValue(false);
					doRemove(dto);
				}

				@Override
				public void onFailure(Throwable caught) {
					removeButton.setValue(false);
					Window.alert(caught.getMessage());
				}
			});
		} else {
			doRemove(dto);
		}
	}
	
	private void doRemove(ShoppingItemDTO dto) {
		int index = model.getShoppingItemDTOs().indexOf(dto);
		if (index != -1) {
			model.getShoppingItemDTOs().remove(index);
			display.removeRow(index + 1);
		}
		
		boolean unsavedChanges = false;
		for (ShoppingItemDTO shoppingItemDTO : model.getShoppingItemDTOs()) {
			if (shoppingItemDTO.isChanged()) {
				unsavedChanges = true;
				break;
			}
		}
		
		display.getSaveButton().setEnabled(unsavedChanges);
	}
}
