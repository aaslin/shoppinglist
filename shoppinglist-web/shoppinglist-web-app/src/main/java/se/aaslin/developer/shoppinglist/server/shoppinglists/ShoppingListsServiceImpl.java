package se.aaslin.developer.shoppinglist.server.shoppinglists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListsService;
import se.aaslin.developer.shoppinglist.entity.ShoppingItem;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.entity.User;
import se.aaslin.developer.shoppinglist.security.UserSession;
import se.aaslin.developer.shoppinglist.service.ShoppingItemService;
import se.aaslin.developer.shoppinglist.service.ShoppingListService;
import se.aaslin.developer.shoppinglist.service.UserService;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.shared.exception.NotAuthorizedException;
import se.aaslin.developer.shoppinglist.shared.exception.NotFoundException;

@Service
@Scope("request")
@Transactional
public class ShoppingListsServiceImpl implements ShoppingListsService {
	public static class ShoppingListComparator implements Comparator<ShoppingListDTO> {
		
		@Override
		public int compare(ShoppingListDTO dto0, ShoppingListDTO dto1) {
			return dto0.getModified().compareTo(dto1.getModified());
		}
	}
	
	@Autowired ShoppingListService shoppingListService;
	@Autowired ShoppingItemService shoppingItemService;
	@Autowired UserService userService;
	@Autowired UserSession userSession;
	
	@Override
	public List<ShoppingListDTO> getShoppingLists() {
		List<ShoppingList> shoppingLists = shoppingListService.getAllShoppingListsForUser(userSession.getCurrentSessionsUsername());
		List<ShoppingListDTO> dtos = createShoppingListDTOs(shoppingLists);
		Collections.sort(dtos, new ShoppingListComparator());
		 
		return dtos;
	}

	@Override
	public List<ShoppingItemDTO> getShoppingItems(int shoppingListId) throws NotAuthorizedException, NotFoundException {
		ShoppingList list = shoppingListService.findShoppingListById(shoppingListId, userSession.getCurrentSessionsUsername());
		return createShoppingItemDTOs(shoppingListId, list.getItems()); 
	}

	@Override
	public List<ShoppingListDTO> saveShoppingList(ShoppingListDTO dto) throws NotAuthorizedException, NotFoundException {
		ShoppingList list = extractShoppingList(dto);
		String username = userSession.getCurrentSessionsUsername();
		List<String> members = dto.getMembers();
		if (dto.isFromDB() && dto.getID() != 0) {
			shoppingListService.update(list, members, username);
		} else {
			shoppingListService.create(list, members, username);
		}
		
		return getShoppingLists();
	}

	@Override
	public List<ShoppingItemDTO> saveShoppingItems(int shoppingListId, List<ShoppingItemDTO> itemDTOs) throws NotAuthorizedException, NotFoundException {
		for (ShoppingItemDTO dto : itemDTOs) {
			ShoppingItem item = extractShoppingItem(dto);
			if (dto.isFromDB() && dto.getId() != 0) {
				shoppingItemService.update(item, userSession.getCurrentSessionsUsername());
			} else {
				shoppingItemService.create(shoppingListId, item, userSession.getCurrentSessionsUsername());
			}
		}
		
		return getShoppingItems(shoppingListId);
	}

	@Override
	public List<ShoppingListDTO> removeShoppingList(ShoppingListDTO dto) throws NotAuthorizedException {
		ShoppingList list = extractShoppingList(dto);
		shoppingListService.remove(list, userSession.getCurrentSessionsUsername());
		
		return getShoppingLists();
	}

	@Override
	public List<ShoppingItemDTO> removeShoppingItem(ShoppingItemDTO itemDTO) throws NotAuthorizedException, NotFoundException {
		ShoppingItem item = extractShoppingItem(itemDTO);
		shoppingItemService.remove(item, userSession.getCurrentSessionsUsername());
		
		return getShoppingItems(itemDTO.getShoppingListId());
	}

	@Override
	public List<String> getAllUsers() {
		return userService.getAllUsers(userSession.getCurrentSessionsUsername());
	}

	@Override
	public ShoppingListDTO getShoppingList(int shoppingListId) throws NotAuthorizedException, NotFoundException {
		ShoppingList list = shoppingListService.findShoppingListById(shoppingListId, userSession.getCurrentSessionsUsername());
		return createShoppingListDTO(list);
	}

	@Override
	public ShoppingListDTO updateShoppingList(ShoppingListDTO dto) throws NotAuthorizedException, NotFoundException {
		ShoppingList list = extractShoppingList(dto);
		String username = userSession.getCurrentSessionsUsername();
		List<String> members = dto.getMembers();
		shoppingListService.update(list, members, username);
		
		return getShoppingList(dto.getID());
	}
	
	private List<ShoppingItemDTO> createShoppingItemDTOs(int shoppingListId, List<ShoppingItem> items) {
		List<ShoppingItemDTO> dtos = new ArrayList<ShoppingItemDTO>();
		for (ShoppingItem item : items) {
			dtos.add(createShoppingItemDTO(shoppingListId, item));
		}
		
		return dtos;
	}
	
	private ShoppingItemDTO createShoppingItemDTO(int shoppingListId, ShoppingItem item) {
		ShoppingItemDTO dto = new ShoppingItemDTO();
		dto.setName(item.getName());
		dto.setComment(item.getComment());
		dto.setAmount(item.getAmount());
		dto.setId(item.getId());
		dto.setShoppingListId(shoppingListId);
		dto.setFromDB(true);
		
		return dto;
	}

	private List<ShoppingListDTO> createShoppingListDTOs(List<ShoppingList> lists) {
		List<ShoppingListDTO> dtos = new ArrayList<ShoppingListDTO>();
		for (ShoppingList list : lists) {
			dtos.add(createShoppingListDTO(list));
		}
		
		return dtos;
	}
	
	private ShoppingListDTO createShoppingListDTO(ShoppingList list) {
		ShoppingListDTO dto = new ShoppingListDTO();
		dto.setID(list.getID());
		List<String> members = new ArrayList<String>();
		for (User member : list.getMembers()) {
			members.add(member.getUsername());
		}
		dto.setMembers(members);
		dto.setModified(list.getTimeStamp().getModified());
		dto.setName(list.getName());
		dto.setOwnerID(list.getOwner().getID());
		dto.setOwner(list.getOwner().getUsername());
		dto.setID(list.getID());
		dto.setFromDB(true);
		
		return dto;
	}
	
	private ShoppingList extractShoppingList(ShoppingListDTO dto) {
		ShoppingList list = new ShoppingList();
		if (dto.isFromDB() && dto.getID() != 0) {
			list.setID(dto.getID());
		}
		list.setName(dto.getName());
			
		return list;
	}
	
	private ShoppingItem extractShoppingItem(ShoppingItemDTO dto) {
		ShoppingItem item = new ShoppingItem();
		if(dto.getId() != null) {
			item.setId(dto.getId());
		}
		item.setAmount(dto.getAmount());
		item.setComment(dto.getComment());
		item.setName(dto.getName());
		
		return item;
	}
}
