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
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter.ShoppingListFormPresenter.Model.Member;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListsServiceAsync;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class ShoppingListFormPresenter {
	public interface View extends Display {
		
		Button getSaveListButton();
		
		Button getResetListButton();
		
		TextBox getListNameTextBox();
		
		Button getAddMemberButton();

		void addHeaderToMembersGrid();
		
		void clearMembersGrid();
		
		ListBox addMembersListBox(List<String> members, int row);
		
		void addMemberNameLabel(int row, String username);

		void addMemberStatus(int row, String status);
		
		void removeMemberRow(int row);

		HasValue<Boolean> addRemoveButton(int row);
		
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
			private boolean isNew;
			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((userName == null) ? 0 : userName.hashCode());
				return result;
			}
			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				Member other = (Member) obj;
				if (userName == null) {
					if (other.userName != null)
						return false;
				} else if (!userName.equals(other.userName))
					return false;
				return true;
			}
		}

		ShoppingListDTO getShoppingListDTO();
		
		void setShoppingListDTO(ShoppingListDTO shoppingListDTO);
		
		List<String> getAllUsers();
		
		List<String> getAllAvailableUsers();
		
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
				resetAvailableUsers();
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
				resetAvailableUsers();
				fetchShoppingList(event.getShoppingListDTO().getID());
			}
		});
		
		eventBus.addHandler(ShoppingListNewEvent.TYPE, new ShoppingListNewEventHandler() {
			
			@Override
			public void onNew(ShoppingListNewEvent event) {
				display.setEmpty(false);
				resetAvailableUsers();
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
		resetAvailableUsers();
		fetchShoppingList(model.getShoppingListDTO().getID());
	}

	private void updateList() {
		if (model.getShoppingListDTO().isChanged()) {
			model.getShoppingListDTO().getMembers().clear();
			for (Model.Member member : model.getAllMembers()) {
				if (member.type.equals(Model.Member.Type.MEBMER)) {
					model.getShoppingListDTO().getMembers().add(member.userName);
				}
			}
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
		member.userName = model.getShoppingListDTO().getOwner();
		member.type = Model.Member.Type.OWNER;
		model.getAllMembers().add(member);
		model.getAllAvailableUsers().remove(member.userName);

		for (String username : model.getShoppingListDTO().getMembers()) {
			member = new Model.Member();
			member.userName = username;
			member.type = Model.Member.Type.MEBMER;
			model.getAllMembers().add(member);
			model.getAllAvailableUsers().remove(member.userName);
		}
		
		if (model.getAllMembers().size() == 0) {
			display.getAddMemberButton().setEnabled(false);
		}
		updateMembersGrid();
	}

	private void updateMembersGrid() {
		display.clearMembersGrid();
		display.addHeaderToMembersGrid();
		int row = 1;
		for (Model.Member member : model.getAllMembers()) {
			addAndBindMemberRow(row++, member, false);
		}
	}
	
	private void addAndBindMemberRow(int row, final Model.Member member, final boolean isNewRow) {
		if (isNewRow) {
			final ListBox listbox = display.addMembersListBox(model.getAllAvailableUsers(), row);
			listbox.addChangeHandler(new ChangeHandler() {
				
				@Override
				public void onChange(ChangeEvent event) {
					member.userName = model.getAllAvailableUsers().get(listbox.getSelectedIndex());
				}
			});
		} else {
			display.addMemberNameLabel(row, member.userName);
		}
		
		display.addMemberStatus(row, member.type.label);
		
		if (member.type.equals(Model.Member.Type.OWNER)) {
			return;
		}
		
		final HasValue<Boolean> removeButton = display.addRemoveButton(row);
		removeButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if(event.getValue()) {
					removeMember(member, removeButton, isNewRow);
				}
			}
		});
	}

	private void removeMember(Member removedMember, HasValue<Boolean> removeButton, boolean isNewRow) {
		removeButton.setValue(false);
		int index = model.getAllMembers().indexOf(removedMember);
		model.getAllMembers().remove(index);
		if (!isNewRow) {
			model.getAllAvailableUsers().add(removedMember.userName);
		}
		display.getAddMemberButton().setEnabled(true);
		display.removeMemberRow(index + 1); 
		if (!removedMember.isNew) {
			display.getSaveListButton().setEnabled(true);
			display.getResetListButton().setEnabled(true);
			model.getShoppingListDTO().setChanged(true);
			return;
		}
		
		for (Model.Member m : model.getAllMembers()) {
			if (m.isNew) {
				return;
			}
		}
		
		if (!model.getShoppingListDTO().isChanged()) {
			display.getSaveListButton().setEnabled(false);
			display.getResetListButton().setEnabled(false);
		}
	}
	
	private void addMember() {
		model.getShoppingListDTO().setChanged(true);
		display.getSaveListButton().setEnabled(true);
		display.getResetListButton().setEnabled(true);
		
		Model.Member prevMember = model.getAllMembers().get(model.getAllMembers().size() - 1);
		if (prevMember.isNew) {
			model.getAllAvailableUsers().remove(prevMember.userName);
			if (model.getAllAvailableUsers().size() == 1) {
				display.getAddMemberButton().setEnabled(false);
			}
			display.removeMemberRow(model.getAllMembers().size());
			addAndBindMemberRow(model.getAllMembers().size(), prevMember, false);
		}
		
		Model.Member member = new Model.Member();
		member.userName = model.getAllAvailableUsers().get(0);
		member.type = Model.Member.Type.MEBMER;
		member.isNew = true;
		model.getAllMembers().add(member);
		
		addAndBindMemberRow(model.getAllMembers().size(), member, true);
	}
	
	private void resetAvailableUsers() {
		model.getAllAvailableUsers().clear();
		model.getAllAvailableUsers().addAll(model.getAllUsers());
	}
}
