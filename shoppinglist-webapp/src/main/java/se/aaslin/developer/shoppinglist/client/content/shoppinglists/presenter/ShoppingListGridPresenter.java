package se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter;

import java.util.ArrayList;
import java.util.List;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListGridServiceAsync;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class ShoppingListGridPresenter {
	public interface ViewDisplay extends Display {
		
		ListBox getShoppingListsBox();
		
		void addHeader(String shoppingListName, String owner);
		
		TextBox addNameTextBox(int row, String name);
		
		TextBox addAmountTextBox(int row, String amount);
		
		TextBox addCommentTextBox(int row, String comment);
		
		HasValue<Boolean> addRemoveButton(int row);
		
		Button getNewItemButton();
		
		Button getSaveButton();
		
		Button getResetButton();

		void removeRow(int i);
	}

	ShoppingListGridServiceAsync srv;
	ViewDisplay display;
	List<ShoppingListDTO> shoppingListDTOs;
	List<ShoppingItemDTO> shoppingItemDTOs; 

	public ShoppingListGridPresenter(ShoppingListGridServiceAsync srv, ViewDisplay display) {
		this.srv = srv;
		this.display = display;
		fetchShoppingLists();
		bind();
	}

	private void fetchShoppingLists() {
		srv.getShoppingLists(new AsyncCallback<List<ShoppingListDTO>>() {
			
			@Override
			public void onSuccess(List<ShoppingListDTO> result) {
				updateShoppingLists(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}
	
	private void updateShoppingLists(List<ShoppingListDTO> result) {
		shoppingListDTOs = result;
		for (ShoppingListDTO dto : result) {
			display.getShoppingListsBox().addItem(dto.getName(), Integer.toString(dto.getID()));
		}
		
		display.getShoppingListsBox().addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				fetchShoppingItems();
			}
		});
		display.getShoppingListsBox().setItemSelected(0, true);
		
		fetchShoppingItems();
	}
	
	private void fetchShoppingItems() {
		ShoppingListDTO shoppingList = getCurrentShoppingList();
		srv.getShoppingItems(shoppingList.getID(), new AsyncCallback<List<ShoppingItemDTO>>() {
			
			@Override
			public void onSuccess(List<ShoppingItemDTO> result) {
				updateShoppingItems(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	private void updateShoppingItems(List<ShoppingItemDTO> result) {
		shoppingItemDTOs = result;
		ShoppingListDTO shoppingList = getCurrentShoppingList();
		display.addHeader(shoppingList.getName(), shoppingList.getOwnerUserName());
		
		int row = 1;
		for (final ShoppingItemDTO dto : result) {
			addAndBindRow(row, dto);
			row++;
		}
	}

	private void bind() {
		display.getNewItemButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ShoppingItemDTO dto = new ShoppingItemDTO();
				shoppingItemDTOs.add(dto);
				addAndBindRow(shoppingItemDTOs.size(), dto);
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

	private void addAndBindRow(int row, final ShoppingItemDTO dto) {
		display.addNameTextBox(row, dto.getName()).addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				dto.setChanged(true);
				dto.setName(event.getValue());
				display.getSaveButton().setEnabled(true);
			}
		});
		
		display.addAmountTextBox(row, dto.getAmount()).addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				dto.setChanged(true);
				dto.setAmount(event.getValue());
				display.getSaveButton().setEnabled(true);
			}
		});
		
		display.addCommentTextBox(row, dto.getComment()).addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				dto.setChanged(true);
				dto.setComment(event.getValue());
				display.getSaveButton().setEnabled(true);
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
		for (ShoppingItemDTO dto : shoppingItemDTOs) {
			if (dto.isChanged()) {
				toSave.add(dto);
			}
		}
		
		if(toSave.size() > 0) {
			srv.saveShoppingItems(getCurrentShoppingList().getID(), toSave, new AsyncCallback<List<ShoppingItemDTO>>() {
				
				@Override
				public void onSuccess(List<ShoppingItemDTO> result) {
					updateShoppingItems(result);
				}

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}
			});
		}
	}

	private ShoppingListDTO getCurrentShoppingList() {
		int index = display.getShoppingListsBox().getSelectedIndex();
		if (index == -1) {
			return null;
		}

		return shoppingListDTOs.get(index);
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
		int index = shoppingItemDTOs.indexOf(dto);
		if (index != -1) {
			shoppingItemDTOs.remove(index);
			display.removeRow(index + 1);
		}
		
		boolean unsavedChanges = false;
		for (ShoppingItemDTO shoppingItemDTO : shoppingItemDTOs) {
			if (shoppingItemDTO.isChanged()) {
				unsavedChanges = true;
				break;
			}
		}
		
		display.getSaveButton().setEnabled(unsavedChanges);
	}
}
