package se.aaslin.developer.shoppinglist.service.shoppinglist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.aaslin.developer.shoppinglist.dao.ShoppingListDAO;
import se.aaslin.developer.shoppinglist.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.entity.User;
import se.aaslin.developer.shoppinglist.service.ShoppingListService;

@Service("shoppingList")
public class ShoppingListServiceImpl implements ShoppingListService {

	@Autowired ShoppingListDAO shoppingListDao;

	@Override
	public List<ShoppingListDTO> getAll() {
		List<ShoppingList> shoppingLists = shoppingListDao.list();
		List<ShoppingListDTO> dtos = new ArrayList<ShoppingListDTO>();
		for (ShoppingList list : shoppingLists) {
			dtos.add(createShoppingListDTO(list));
		}
		return dtos;
	}

	@Override
	public ShoppingListDTO findById(int id) {
		ShoppingList shoppingList = shoppingListDao.findById(id);
		if(shoppingList != null){
			return createShoppingListDTO(shoppingList);
		}
		return null;
	}

	@Override
	public void update(ShoppingListDTO dto) {
		shoppingListDao.update(createShoppingList(dto));
	}

	@Override
	public void remove(int id) {
		ShoppingList list = shoppingListDao.findById(id);
		if(list != null){
			shoppingListDao.delete(list);
		}
	}

	@Override
	public void add(ShoppingListDTO dto) {
		shoppingListDao.create(createShoppingList(dto));
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
}
