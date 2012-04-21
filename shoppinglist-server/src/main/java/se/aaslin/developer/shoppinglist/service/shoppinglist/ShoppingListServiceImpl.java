package se.aaslin.developer.shoppinglist.service.shoppinglist;

import java.util.ArrayList;
import java.util.Calendar;
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
import se.aaslin.developer.shoppinglist.service.ShoppingListService;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

@Service("shoppingListService")
@Transactional
public class ShoppingListServiceImpl implements ShoppingListService {

	@Autowired ShoppingListDAO shoppingListDAO;
	@Autowired ShoppingItemDAO shoppingItemDAO;
	@Autowired UserDAO userDAO;
	@Autowired TimeStampDAO timeStampDAO;

	@Override
	public List<ShoppingListDTO> getAll() {
		List<ShoppingList> shoppingLists = shoppingListDAO.list();
		List<ShoppingListDTO> dtos = new ArrayList<ShoppingListDTO>();
		for (ShoppingList list : shoppingLists) {
			dtos.add(createShoppingListDTO(list));
		}
		return dtos;
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
	public void save(ShoppingListDTO shoppingListDTO) {
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
	public List<ShoppingListDTO> getAllShoppingListsForUser(User user) {
		List<ShoppingList> shoppingLists = shoppingListDAO.getShoppingListsForUser(user.getID());
		List<ShoppingListDTO> dtos = new ArrayList<ShoppingListDTO>();
		for(ShoppingList list : shoppingLists) {
			ShoppingListDTO dto = createShoppingListDTO(list);
			dtos.add(dto);
		}
		
		return dtos;
	}
}
