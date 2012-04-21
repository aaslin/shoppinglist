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
import se.aaslin.developer.shoppinglist.entity.ShoppingItem;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.entity.TimeStamp;
import se.aaslin.developer.shoppinglist.service.ShoppingItemService;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;

@Service
@Transactional
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
}
