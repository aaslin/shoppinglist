package se.aaslin.developer.shoppinglist.service.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.aaslin.developer.shoppinglist.entity.ShoppingItem;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.exception.NotAuthorizedException;
import se.aaslin.developer.shoppinglist.service.DashboardViewService;
import se.aaslin.developer.shoppinglist.service.ShoppingItemService;
import se.aaslin.developer.shoppinglist.service.ShoppingListService;
import se.aaslin.developer.shoppinglist.shared.dto.DashboardItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.DashboardListPortletDTO;

@Service
@Transactional
public class DashboardViewServiceImpl implements DashboardViewService {
	
	@Autowired ShoppingListService shoppingListService;
	@Autowired ShoppingItemService shoppingItemService;
	
	@Override
	public List<DashboardListPortletDTO> getAllListPortlets(String username) {
		List<ShoppingList> lists = shoppingListService.getAllShoppingListsForUser(username);
		return createDashboardListPortletDTOs(lists);
	}

	@Override
	public List<DashboardItemDTO> getAllShoppingListItems(int shoppingListId, String username) {
		try {
			ShoppingList list = shoppingListService.findShoppingListById(shoppingListId, username);
			return createDashboardItemDTOs(list.getItems());
		} catch (NotAuthorizedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<DashboardItemDTO> saveShoppingItems(int shoppingListId, List<DashboardItemDTO> dtos, String username) {
		try {
			for (DashboardItemDTO dto : dtos) {
				ShoppingItem item = extractShoppingItem(dto);
				if (dto.isFromDB() && dto.getId() != 0) {
					shoppingItemService.update(item, username);
				} else {
					shoppingItemService.create(shoppingListId, item,username);
				}
			}
			
			return getAllShoppingListItems(shoppingListId, username);
		} catch (NotAuthorizedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<DashboardItemDTO> removeShoppingItem(DashboardItemDTO dto, String username) {
		try {
			ShoppingItem item = extractShoppingItem(dto);
			shoppingItemService.remove(item, username);
			return getAllShoppingListItems(dto.getShoppingListId(), username);
		} catch (NotAuthorizedException e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<ShoppingItem> extractShoppingItems(List<DashboardItemDTO> dtos) {
		List<ShoppingItem> result = new ArrayList<ShoppingItem>();
		for (DashboardItemDTO dto : dtos) {
			result.add(extractShoppingItem(dto));
		}
		
		return result;
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

	private List<DashboardItemDTO> createDashboardItemDTOs(List<ShoppingItem> items) {
		List<DashboardItemDTO> dtos = new ArrayList<DashboardItemDTO>();
		for (ShoppingItem item : items) {
			dtos.add(createDashboardItemDTO(item));
		}
		
		return dtos;
	}
	
	private DashboardItemDTO createDashboardItemDTO(ShoppingItem item) {
		DashboardItemDTO dto = new DashboardItemDTO();
		dto.setName(item.getName());
		dto.setComment(item.getComment());
		dto.setAmount(item.getAmount());
		dto.setId(item.getId());
		dto.setShoppingListId(item.getShoppingList().getID());
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
