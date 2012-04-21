package se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter;

import java.util.List;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListChangeEvent;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListChangeEventHandler;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListEmptyEvent;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListEmptyEventHandler;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListNewEvent;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListNewEventHandler;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.ShoppingListUpdateEvent;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListsServiceAsync;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class ShoppingListFormPresenter {
	public interface View extends Display {
		
		Button getSaveListButton();
		
		Button getResetListButton();
		
		TextBox getListNameTextBox();
		
		Button getAddMemberButton();

		ListBox addMemberNameListBox(int row);

		void addMemberNameLabel(int row, String username);

		void addMemberStatus(int row, String status);
		
		void toggleLoading(boolean loading);
		
		void setEmpty(boolean empty);
	}
	
	public interface Model {
		public static class Member {
			public enum Type {
				OWNER("owner"), MEBMER("member");

				private String label;

				private Type(String label) {
					this.label = label;
				}

				public String getLabel() {
					return label;
				}
			}

			private String userName;
			private Type type = Type.MEBMER;
		}

		ShoppingListDTO getShoppingListDTO();
		
		void setShoppingListDTO(ShoppingListDTO shoppingListDTO);
		
		List<String> getAllUsers();
		
		List<Member> getAllMembers();
	}
	
	View display;
	Model model;
	EventBus eventBus;
	ShoppingListsServiceAsync srv;
	
	public ShoppingListFormPresenter(View display, Model model, EventBus eventBus, ShoppingListsServiceAsync srv) {
		this.display = display;
		this.model = model;
		this.eventBus = eventBus;
		this.srv = srv;
		fecthAllUsers();
		bind();
	}
	
	private void fecthAllUsers() {
		srv.getAllUsers(new AsyncCallback<List<String>>() {
			
			@Override
			public void onSuccess(List<String> result) {
				model.getAllUsers().clear();
				model.getAllUsers().addAll(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}
	
	private void bind() {
		eventBus.addHandler(ShoppingListChangeEvent.TYPE, new ShoppingListChangeEventHandler() {
			
			@Override
			public void onChange(ShoppingListChangeEvent event) {
				fetchShoppingList(event.getShoppingListDTO().getID());
			}
		});
		
		eventBus.addHandler(ShoppingListNewEvent.TYPE, new ShoppingListNewEventHandler() {
			
			@Override
			public void onNew(ShoppingListNewEvent event) {
				display.setEmpty(false);
				model.setShoppingListDTO(event.getShoppingListDTO());
				updateForm();
				display.getListNameTextBox().setSelectionRange(0, model.getShoppingListDTO().getName().length());
			}
		});
		
		eventBus.addHandler(ShoppingListEmptyEvent.TYPE, new ShoppingListEmptyEventHandler() {
			
			@Override
			public void onEmpty(ShoppingListEmptyEvent event) {
				display.setEmpty(true);
			}
		});
		
		display.getAddMemberButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				addMember();
			}

		});
		
		display.getSaveListButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				updateList();
			}
		});
		
		display.getResetListButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				resetList();
			}
		});
	}
	

	private void fetchShoppingList(int shoppingListId) {
		display.toggleLoading(true);
		srv.getShoppingList(shoppingListId, new AsyncCallback<ShoppingListDTO>() {
			
			@Override
			public void onSuccess(ShoppingListDTO result) {
				model.setShoppingListDTO(result);
				updateForm();
				display.toggleLoading(false);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}
	
	private void resetList() {
		fetchShoppingList(model.getShoppingListDTO().getID());
	}

	private void updateList() {
		if (model.getShoppingListDTO().isChanged()) {
			display.getSaveListButton().setEnabled(false);
			display.getResetListButton().setEnabled(false);
			eventBus.fireEvent(new ShoppingListUpdateEvent(model.getShoppingListDTO()));
			srv.updateShoppingList(model.getShoppingListDTO(), new AsyncCallback<ShoppingListDTO>() {
				
				@Override
				public void onSuccess(ShoppingListDTO result) {
					model.setShoppingListDTO(result);
					updateForm();
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}
			});
		}
	}
	
	private void updateForm() {
		TextBox name = display.getListNameTextBox();
		name.setText(model.getShoppingListDTO().getName());
		name.addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				display.getSaveListButton().setEnabled(true);
				model.getShoppingListDTO().setName(event.getValue());
				model.getShoppingListDTO().setChanged(true);
			}
		});
		
		updateMembers();	
	}
	
	private void updateMembers() {
		model.getAllMembers().clear();

		Model.Member member = new Model.Member();
		member.userName = model.getShoppingListDTO().getOwnerUserName();
		member.type = Model.Member.Type.OWNER;
		model.getAllMembers().add(member);

		for (String username : model.getShoppingListDTO().getMembers()) {
			member = new Model.Member();
			member.userName = username;
			member.type = Model.Member.Type.MEBMER;
			model.getAllMembers().add(member);
		}
		
		updateMembersGrid();
	}

	private void updateMembersGrid() {
		int row = 1;
		for (Model.Member member : model.getAllMembers()) {
			addAndBindMemberRow(row++, member, false);
		}
	}
	
	private void addAndBindMemberRow(int row, Model.Member member, boolean isNewRow) {
		if (isNewRow) {
			ListBox listBox = display.addMemberNameListBox(row);
			for (String username : model.getAllUsers()) {
				listBox.addItem(username);
			}
			listBox.setSelectedIndex(0);
			
		} else {
			display.addMemberNameLabel(row, member.userName);
		}
		
		display.addMemberStatus(row, member.type.label);
	}
	
	private void addMember() {
		model.getShoppingListDTO().setChanged(true);
		display.getSaveListButton().setEnabled(true);
		
		Model.Member member = new Model.Member();
		member.userName = null;
		member.type = Model.Member.Type.MEBMER;
		addAndBindMemberRow(model.getAllMembers().size() + 1, member, true);
	}
}
