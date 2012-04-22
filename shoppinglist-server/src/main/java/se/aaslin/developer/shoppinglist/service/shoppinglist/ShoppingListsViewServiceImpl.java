package se.aaslin.developer.shoppinglist.service.shoppinglist;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.aaslin.developer.shoppinglist.dao.ShoppingItemDAO;
import se.aaslin.developer.shoppinglist.dao.ShoppingListDAO;
import se.aaslin.developer.shoppinglist.dao.TimeStampDAO;
import se.aaslin.developer.shoppinglist.dao.UserDAO;
import se.aaslin.developer.shoppinglist.entity.ShoppingItem;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.entity.TimeStamp;
import se.aaslin.developer.shoppinglist.entity.User;
import se.aaslin.developer.shoppinglist.service.ShoppingListsViewService;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

@Service
@Transactional
public class ShoppingListsViewServiceImpl implements ShoppingListsViewService {
	public static class ShoppingListComparator implements Comparator<ShoppingListDTO> {
		
		@Override
		public int compare(ShoppingListDTO dto0, ShoppingListDTO dto1) {
			return dto0.getModified().compareTo(dto1.getModified());
		}
	}
	
	@Autowired ShoppingListDAO shoppingListDAO;
	@Autowired ShoppingItemDAO shoppingItemDAO;
	@Autowired UserDAO userDAO;
	@Autowired TimeStampDAO timeStampDAO;

	@Override
	public List<ShoppingListDTO> getAll() {
		List<ShoppingList> shoppingLists = shoppingListDAO.list();	
		return createShoppingListDTOs(shoppingLists);
	}

	@Override
	public ShoppingListDTO findById(int id) {
		ShoppingList shoppingList = shoppingListDAO.findById(id);
		if(shoppingList != null){
			return createShoppingListDTO(shoppingList);
		}
		return null;
	}

	@Override
	public void remove(int id) {
		ShoppingList list = shoppingListDAO.findById(id);
		if(list != null){
			List<ShoppingItem> items = list.getItems();
			for (ShoppingItem item : items) {
				shoppingItemDAO.delete(item);
			}
			shoppingListDAO.delete(list);
		}
	}

	@Override
	public void save(ShoppingListDTO shoppingListDTO, String username) {
		shoppingListDTO.setOwnerUsername(username);
		
		Date date = Calendar.getInstance().getTime();
		if (shoppingListDTO.isFromDB() && shoppingListDTO.getID() != 0) {
			ShoppingList shoppingList = shoppingListDAO.findById(shoppingListDTO.getID());
			shoppingList.setMembers(userDAO.findUsers(shoppingListDTO.getMembers()));
			shoppingList.setName(shoppingListDTO.getName());
			shoppingList.setOwner(userDAO.findByUsername(shoppingListDTO.getOwnerUserName()));
			TimeStamp timeStamp = shoppingList.getTimeStamp();
			timeStamp.setModified(date);
			
			timeStampDAO.update(timeStamp);
			shoppingListDAO.update(shoppingList);
		} else {
			ShoppingList shoppingList = new ShoppingList();
			shoppingList.setMembers(userDAO.findUsers(shoppingListDTO.getMembers()));
			shoppingList.setName(shoppingListDTO.getName());
			shoppingList.setOwner(userDAO.findByUsername(shoppingListDTO.getOwnerUserName()));
			TimeStamp timeStamp = new TimeStamp();
			timeStamp.setCreated(date);
			timeStamp.setModified(date);
			shoppingList.setTimeStamp(timeStamp);
			
			timeStampDAO.create(timeStamp);
			shoppingListDAO.create(shoppingList);
		}
	}

	@Override
	public List<ShoppingListDTO> getOwnedShoppingListsForUser(String username) {
		User user = userDAO.findByUsername(username);
		List<ShoppingList> shoppingLists = shoppingListDAO.getOwnedShoppingListsForUser(user.getID());
		
		List<ShoppingListDTO> dtos = createShoppingListDTOs(shoppingLists);
		Collections.sort(dtos, new ShoppingListComparator());
		 
		return dtos;
	}
	
	@Override
	public List<ShoppingListDTO> getAllShoppingListsForUser(String username) {
		User user = userDAO.findByUsername(username);

		List<ShoppingList> ownedLists = shoppingListDAO.getOwnedShoppingListsForUser(user.getID());
		List<ShoppingList> memberLists = shoppingListDAO.getShoppingListsForUser(user.getID());
		
		List<ShoppingListDTO> result = new ArrayList<ShoppingListDTO>();
		result.addAll(createShoppingListDTOs(ownedLists));
		result.addAll(createShoppingListDTOs(memberLists));
		Collections.sort(result, new ShoppingListComparator());
		 
		return result;
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
		dto.setOwnerUsername(list.getOwner().getUsername());
		dto.setID(list.getID());
		dto.setFromDB(true);
		
		return dto;
	}
	
	@Override
	public List<ShoppingItemDTO> getAllShoppingListItems(int shoppingListId) {
		List<ShoppingItem> shoppingItems = shoppingItemDAO.getShoppingListItems(shoppingListId);
		List<ShoppingItemDTO> dtos = new ArrayList<ShoppingItemDTO>();
		for (ShoppingItem item : shoppingItems) {
			ShoppingItemDTO dto = new ShoppingItemDTO();
			dto.setName(item.getName());
			dto.setComment(item.getComment());
			dto.setAmount(item.getAmount());
			dto.setId(item.getId());
			dto.setShoppingListId(item.getShoppingList().getID());
			dto.setFromDB(true);
			dtos.add(dto);
		}
		
		return dtos;
	}

	@Override
	public void remove(ShoppingItemDTO itemDTO) {
		ShoppingItem item = shoppingItemDAO.findById(itemDTO.getId());
		item.getShoppingList().getItems().remove(item);
		shoppingItemDAO.delete(item);
	}

	@Override
	public void saveItemsToShoppingList(Integer shoppingListId, List<ShoppingItemDTO> dtos) {
		ShoppingList shoppingList = shoppingListDAO.findById(shoppingListId);
		if (shoppingList == null) {
			return;
		}
		
		Date date = Calendar.getInstance().getTime();
		for (ShoppingItemDTO dto : dtos) {
			if (dto.isFromDB()) {
				ShoppingItem item = shoppingItemDAO.findById(dto.getId());
				item.setAmount(dto.getAmount());
				item.setComment(dto.getComment());
				item.setName(dto.getName());
				TimeStamp timeStamp = item.getTimeStamp();
				timeStamp.setModified(date);
				
				timeStampDAO.update(timeStamp);
				shoppingItemDAO.update(item);		
			} else {
				ShoppingItem item = new ShoppingItem();
				item.setAmount(dto.getAmount());
				item.setComment(dto.getComment());
				item.setName(dto.getName());
				item.setShoppingList(shoppingList);
				TimeStamp timeStamp = new TimeStamp();
				timeStamp.setCreated(date);
				timeStamp.setModified(date);
				item.setTimeStamp(timeStamp);
				
				timeStampDAO.create(timeStamp);
				shoppingItemDAO.create(item);	
			}
		}
	}

	@Override
	public List<String> getAllUsers() {
		List<String> result = new ArrayList<String>();
		for (User user : userDAO.list()) {
			result.add(user.getUsername());
		}
		
		return result;
	}
}
