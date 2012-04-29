package se.aaslin.developer.shoppinglist.client.content.dashboard.presenter;

import java.util.ArrayList;
import java.util.List;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.content.dashboard.service.DashboardViewServiceAsync;
import se.aaslin.developer.shoppinglist.shared.dto.DashboardItemDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;

public class DashboardListPortletPresenter {
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
		
		void setLabel(String label);
		
		void setOwner(String username);
	}
	
	public interface Model {
		
		int getShoppingListId();
		
		List<DashboardItemDTO> getItems();

		String getName();

		String getOwner();
	}

	View display;
	Model model;
	DashboardViewServiceAsync srv;
	
	public DashboardListPortletPresenter(View display, Model model, DashboardViewServiceAsync srv) {
		this.display = display;
		this.model = model;
		this.srv = srv;
		fetchShoppingItems();
		bind();
	}

	private void bind() {		
		display.getNewItemButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				DashboardItemDTO dto = new DashboardItemDTO();
				model.getItems().add(dto);
				addAndBindRow(model.getItems().size(), dto);
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
		srv.getShoppingItems(model.getShoppingListId(), new AsyncCallback<List<DashboardItemDTO>>() {
			
			@Override
			public void onSuccess(List<DashboardItemDTO> result) {
				model.getItems().clear();
				model.getItems().addAll(result);
				display.getSaveButton().setEnabled(false);
				display.getResetButton().setEnabled(false);
				display.toggleLoading(false);
				updateShoppingItems();
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	private void updateShoppingItems() {
		display.clear();
		display.setLabel(model.getName());
		display.addHeader();
		
		int row = 1;
		for (final DashboardItemDTO dto : model.getItems()) {
			addAndBindRow(row, dto);
			row++;
		}
	}

	private void addAndBindRow(int row, final DashboardItemDTO dto) {
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
		List<DashboardItemDTO> toSave = new ArrayList<DashboardItemDTO>();
		for (DashboardItemDTO dto : model.getItems()) {
			if (dto.isChanged()) {
				toSave.add(dto);
			}
		}
		
		if(toSave.size() > 0) {
			srv.saveShoppingItems(model.getShoppingListId(), toSave, new AsyncCallback<List<DashboardItemDTO>>() {
				
				@Override
				public void onSuccess(List<DashboardItemDTO> result) {
					model.getItems().clear();
					model.getItems().addAll(result);
					display.getSaveButton().setEnabled(false);
					display.getResetButton().setEnabled(false);
					updateShoppingItems();
				}

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}
			});
		}
	}
	
	private void remove(final DashboardItemDTO dto, final HasValue<Boolean> removeButton) {
		if (dto.isFromDB()) {
			srv.removeShoppingItem(dto, new AsyncCallback<List<DashboardItemDTO>>() {
				
				@Override
				public void onSuccess(List<DashboardItemDTO> result) {
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
	
	private void doRemove(DashboardItemDTO dto) {
		int index = model.getItems().indexOf(dto);
		if (index != -1) {
			model.getItems().remove(index);
			display.removeRow(index + 1);
		}
		
		boolean unsavedChanges = false;
		for (DashboardItemDTO shoppingItemDTO : model.getItems()) {
			if (shoppingItemDTO.isChanged()) {
				unsavedChanges = true;
				break;
			}
		}
		
		display.getSaveButton().setEnabled(unsavedChanges);
	}
}
