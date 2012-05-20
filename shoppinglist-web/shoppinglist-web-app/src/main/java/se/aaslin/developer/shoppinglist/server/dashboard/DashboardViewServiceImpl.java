package se.aaslin.developer.shoppinglist.server.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.aaslin.developer.shoppinglist.client.content.dashboard.service.DashboardViewService;
import se.aaslin.developer.shoppinglist.entity.ShoppingItem;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.security.UserSession;
import se.aaslin.developer.shoppinglist.service.ShoppingItemService;
import se.aaslin.developer.shoppinglist.service.ShoppingListService;
import se.aaslin.developer.shoppinglist.shared.dto.DashboardItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.DashboardListPortletDTO;
import se.aaslin.developer.shoppinglist.shared.exception.NotAuthorizedException;
import se.aaslin.developer.shoppinglist.shared.exception.NotFoundException;

@Service
@Scope("request")
@Transactional
public class DashboardViewServiceImpl implements DashboardViewService {
	
	@Autowired ShoppingListService shoppingListService;
	@Autowired ShoppingItemService shoppingItemService;
	@Autowired UserSession userSession;
	
	@Override
	public List<DashboardListPortletDTO> getAllListPortlets() {
		List<ShoppingList> lists = shoppingListService.getAllShoppingListsForUser(userSession.getCurrentSessionsUsername());
		return createDashboardListPortletDTOs(lists);
	}

	@Override
	public List<DashboardItemDTO> getShoppingItems(int shoppingListId) {
		try {	
			ShoppingList list = shoppingListService.findShoppingListById(shoppingListId, userSession.getCurrentSessionsUsername());
			return createDashboardItemDTOs(shoppingListId, list.getItems());
		} catch (NotAuthorizedException e) {
			e.printStackTrace();
			return null;
		} catch (NotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<DashboardItemDTO> saveShoppingItems(int shoppingListId, List<DashboardItemDTO> dtos) {
		try {
			for (DashboardItemDTO dto : dtos) {
				ShoppingItem item = extractShoppingItem(dto);
				if (dto.isFromDB() && dto.getId() != 0) {
					shoppingItemService.update(item, userSession.getCurrentSessionsUsername());
				} else {
					shoppingItemService.create(shoppingListId, item, userSession.getCurrentSessionsUsername());
				}
			}
			
			return getShoppingItems(shoppingListId);
		} catch (NotAuthorizedException e) {
			e.printStackTrace();
			return null;
		} catch (NotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<DashboardItemDTO> removeShoppingItem(DashboardItemDTO dto) {
		try {
			ShoppingItem item = extractShoppingItem(dto);
			shoppingItemService.remove(item, userSession.getCurrentSessionsUsername());
			return getShoppingItems(dto.getShoppingListId());
		} catch (NotAuthorizedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private ShoppingItem extractShoppingItem(DashboardItemDTO dto) {
		ShoppingItem item = new ShoppingItem();
		if(dto.getId() != null) {
			item.setId(dto.getId());
		}
		item.setAmount(dto.getAmount());
		item.setComment(dto.getComment());
		item.setName(dto.getName());
		
		return item;
	}

	private List<DashboardItemDTO> createDashboardItemDTOs(int shoppingListId, List<ShoppingItem> items) {
		List<DashboardItemDTO> dtos = new ArrayList<DashboardItemDTO>();
		for (ShoppingItem item : items) {
			dtos.add(createDashboardItemDTO(shoppingListId, item));
		}
		
		return dtos;
	}
	
	private DashboardItemDTO createDashboardItemDTO(int shoppingListId,ShoppingItem item) {
		DashboardItemDTO dto = new DashboardItemDTO();
		dto.setName(item.getName());
		dto.setComment(item.getComment());
		dto.setAmount(item.getAmount());
		dto.setId(item.getId());
		dto.setShoppingListId(shoppingListId);
		dto.setFromDB(true);
		
		return dto;
	}

	private List<DashboardListPortletDTO> createDashboardListPortletDTOs(List<ShoppingList> lists) {
		List<DashboardListPortletDTO> dtos = new ArrayList<DashboardListPortletDTO>();
		for (ShoppingList list : lists) {
			dtos.add(createDashboardListPortletDTO(list));
		}
		
		return dtos;
	}
	
	private DashboardListPortletDTO createDashboardListPortletDTO(ShoppingList list) {
		DashboardListPortletDTO dto = new DashboardListPortletDTO();
		dto.setName(list.getName());
		dto.setOwner(list.getOwner().getUsername());
		dto.setShoppingListId(list.getID());
		
		return dto;
	}
}
