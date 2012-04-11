package se.aaslin.developer.shoppinglist.service.shoppinglist;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.aaslin.developer.shoppinglist.dao.ShoppingItemDAO;
import se.aaslin.developer.shoppinglist.dao.ShoppingListDAO;
import se.aaslin.developer.shoppinglist.dao.TimeStampDAO;
import se.aaslin.developer.shoppinglist.entity.ShoppingItem;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.entity.TimeStamp;
import se.aaslin.developer.shoppinglist.service.ShoppingItemService;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;

@Service("shoppingItemService")
public class ShoppingItemServiceImpl implements ShoppingItemService{

	@Autowired ShoppingItemDAO shoppingItemDAO;
	@Autowired ShoppingListDAO shoppingListDAO;
	@Autowired TimeStampDAO timeStampDAO;
	
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
			dtos.add(dto);
		}
		
		return dtos;
	}

	@Override
	public void addItemToShoppingList(ShoppingItemDTO dto) {
		ShoppingList shoppingList = shoppingListDAO.findById(dto.getShoppingListId());
		if (shoppingList == null) {
			return;
		}
		ShoppingItem item = new ShoppingItem();
		item.setAmount(dto.getAmount());
		item.setComment(dto.getComment());
		item.setName(dto.getName());
		item.setShoppingList(shoppingList);
		TimeStamp timeStamp = new TimeStamp();
		Date date = Calendar.getInstance().getTime();
		timeStamp.setCreated(date);
		timeStamp.setModified(date);
		item.setTimeStamp(timeStamp);
		
		timeStampDAO.create(timeStamp);
		shoppingItemDAO.create(item);
	}

	@Override
	public void remove(ShoppingItemDTO itemDTO) {
		ShoppingItem item = shoppingItemDAO.findById(itemDTO.getId());
		shoppingItemDAO.delete(item);
	}

	@Override
	public void update(ShoppingItemDTO itemDTO) {
		ShoppingItem shoppingItem = shoppingItemDAO.findById(itemDTO.getId());
		if (shoppingItem == null) {
			return;
		}
		ShoppingList shoppingList = shoppingListDAO.findById(itemDTO.getShoppingListId());
		if (shoppingList == null) {
			return;
		}
		shoppingItem.setAmount(itemDTO.getAmount());
		shoppingItem.setComment(itemDTO.getComment());
		shoppingItem.setName(itemDTO.getName());
		shoppingItem.setShoppingList(shoppingList);
		TimeStamp timeStamp = shoppingItem.getTimeStamp();
		Date date = Calendar.getInstance().getTime();
		timeStamp.setModified(date);
		
		timeStampDAO.update(timeStamp);
		shoppingItemDAO.update(shoppingItem);
	}
}
