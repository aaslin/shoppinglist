package se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter;

import java.util.ArrayList;
import java.util.List;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListsServiceAsync;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.google.gwt.event.shared.EventBus;

public class ShoppingListsPresenter {
	public interface ViewDisplay extends Display {
		ShoppingListFormPresenter.View getShoppingListFormView();
		
		ShoppingListGridPresenter.View getShoppingListGridView();
		
		ShoppingListBoxPresenter.View getShoppingListBoxView();
	}
	
	public class ShoppingListFormModel implements ShoppingListFormPresenter.Model {
		private ShoppingListDTO shoppingListDTO;
		private List<String> users = new ArrayList<String>();
		private List<Member> members = new ArrayList<ShoppingListFormPresenter.Model.Member>();		

		@Override
		public List<String> getAllUsers() {
			return users;
		}

		@Override
		public List<Member> getAllMembers() {
			return members;
		}

		@Override
		public ShoppingListDTO getShoppingListDTO() {
			return shoppingListDTO;
		}

		@Override
		public void setShoppingListDTO(ShoppingListDTO shoppingListDTO) {
			this.shoppingListDTO = shoppingListDTO;
		}
	}
	
	public class ShoppingListGridModel implements ShoppingListGridPresenter.Model {
		
		private ShoppingListDTO currentShoppingListDTO;
		private List<ShoppingItemDTO> shoppingItemDTOs = new ArrayList<ShoppingItemDTO>();

		@Override
		public void setCurrentShoppingList(ShoppingListDTO shoppingListDTO) {
			this.currentShoppingListDTO = shoppingListDTO;
		}

		@Override
		public ShoppingListDTO getCurrentShoppingList() {
			return currentShoppingListDTO;
		}

		@Override
		public void setShoppingItemDTOs(List<ShoppingItemDTO> shoppingItemDTOs) {
			this.shoppingItemDTOs = shoppingItemDTOs;
		}

		@Override
		public List<ShoppingItemDTO> getShoppingItemDTOs() {
			return shoppingItemDTOs;
		}
	}

	public class ShoppingListBoxModel implements ShoppingListBoxPresenter.Model {
		
		private List<ShoppingListDTO> shoppingListDTOs = new ArrayList<ShoppingListDTO>();
		private String currentUser;
		
		@Override
		public List<ShoppingListDTO> getShoppingListDTOs() {
			return shoppingListDTOs;
		}

		@Override
		public String getCurrentUser() {
			return currentUser;
		}
		
		public void setCurrentUser(String currentUser) {
			this.currentUser = currentUser;
		}
		
	}
	
	public ShoppingListsPresenter(final ViewDisplay display, final ShoppingListsServiceAsync srv, final EventBus eventBus) {
		ShoppingListFormModel formModel = new ShoppingListFormModel();
		ShoppingListGridModel gridModel = new ShoppingListGridModel();
		ShoppingListBoxModel boxModel = new ShoppingListBoxModel();
		boxModel.setCurrentUser("lars"); //TODO fix this!!
				
		new ShoppingListFormPresenter(display.getShoppingListFormView(), formModel, eventBus, srv);
		new ShoppingListGridPresenter(display.getShoppingListGridView(), gridModel, eventBus, srv);
		new ShoppingListBoxPresenter(display.getShoppingListBoxView(), boxModel, eventBus, srv);
	}
}
