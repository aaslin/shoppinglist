package se.aaslin.developer.shoppinglist.service.shoppinglist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.aaslin.developer.shoppinglist.dao.ShoppingItemDAO;
import se.aaslin.developer.shoppinglist.entity.ShoppingItem;
import se.aaslin.developer.shoppinglist.service.ShoppingItemService;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;

@Service("shoppingItemService")
public class ShoppingItemServiceImpl implements ShoppingItemService{

	@Autowired ShoppingItemDAO shoppingItemDAO;
	
	@Override
	public List<ShoppingItemDTO> getAllShoppingListItems(int shoppingListId) {
		List<ShoppingItem> shoppingItems = shoppingItemDAO.getShoppingListItems(shoppingListId);
		List<ShoppingItemDTO> dtos = new ArrayList<ShoppingItemDTO>();
		for (ShoppingItem item : shoppingItems) {
			ShoppingItemDTO dto = new ShoppingItemDTO();
			dto.setName(item.getName());
			dto.setComment(item.getComment());
			dto.setAmount(item.getAmount());
			dtos.add(dto);
		}
		
		return dtos;
	}

	
}
