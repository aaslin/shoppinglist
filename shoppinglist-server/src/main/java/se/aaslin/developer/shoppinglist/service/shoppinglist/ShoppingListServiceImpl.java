package se.aaslin.developer.shoppinglist.service.shoppinglist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.aaslin.developer.shoppinglist.dao.ShoppingListDAO;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.entity.User;
import se.aaslin.developer.shoppinglist.service.ShoppingListService;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

@Service("shoppingListService")
public class ShoppingListServiceImpl implements ShoppingListService {

	@Autowired ShoppingListDAO shoppingListDAO;

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
	public void update(ShoppingListDTO dto) {
		shoppingListDAO.update(createShoppingList(dto));
	}

	@Override
	public void remove(int id) {
		ShoppingList list = shoppingListDAO.findById(id);
		if(list != null){
			shoppingListDAO.delete(list);
		}
	}

	@Override
	public void add(ShoppingListDTO dto) {
		shoppingListDAO.create(createShoppingList(dto));
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
		dto.setOwnerUserName(list.getOwner().getUsername());
		return dto;
	}

	private ShoppingList createShoppingList(ShoppingListDTO dto) {
		ShoppingList list = new ShoppingList();
		list.setID(dto.getID());
		list.setName(dto.getName());
		User user = new User();
		user.setID(dto.getOwnerID());
		user.setUsername(dto.getOwnerUserName());
		list.setOwner(user);
		
		return list;
	}

	@Override
	public List<ShoppingListDTO> getAllShoppingListsForUser(User user) {
		List<ShoppingList> shoppingLists = shoppingListDAO.getShoppingListsForUser(user.getID());
		List<ShoppingListDTO> dtos = new ArrayList<ShoppingListDTO>();
		for(ShoppingList list : shoppingLists) {
			ShoppingListDTO dto = new ShoppingListDTO();
			dto.setID(list.getID());
			dto.setName(list.getName());
			dto.setOwnerID(user.getID());
			dtos.add(dto);
		}
		
		return dtos;
	}
}
