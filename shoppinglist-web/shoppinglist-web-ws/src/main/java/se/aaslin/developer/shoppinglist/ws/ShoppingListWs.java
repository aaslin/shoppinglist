package se.aaslin.developer.shoppinglist.ws;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.aaslin.developer.shoppinglist.entity.ShoppingItem;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.entity.User;
import se.aaslin.developer.shoppinglist.security.ShoppingListSessionManager;
import se.aaslin.developer.shoppinglist.security.UserSession;
import se.aaslin.developer.shoppinglist.service.shoppinglist.ShoppingListServiceImpl;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.sun.jersey.api.core.InjectParam;

@Path("/shoppinglist")
@Service
@Transactional
public class ShoppingListWs extends GenericWs {
	public static class ShoppingListComparator implements Comparator<ShoppingListDTO> {
		
		@Override
		public int compare(ShoppingListDTO dto0, ShoppingListDTO dto1) {
			return dto0.getModified().compareTo(dto1.getModified());
		}
	}
	
	@InjectParam ShoppingListServiceImpl shoppingListService;
	@InjectParam ShoppingListSessionManager shoppingListSessionManager;
	@InjectParam UserSession userSession;
	
	@GET
	@Produces({ APPLICATION_XML, APPLICATION_JSON })
	public Response getAll() {
		String username = userSession.getCurrentSessionsUsername();
		if(username == null) {
			return Response.status(Status.FORBIDDEN).build();
//			throw new HTTPException(Response.Status.FORBIDDEN.getStatusCode());
		}
		List<ShoppingList> shoppingLists = shoppingListService.getAllShoppingListsForUser(username);
		List<ShoppingListDTO> dtos = createShoppingListDTOs(shoppingLists);
		Collections.sort(dtos, new ShoppingListComparator());
		GenericEntity<List<ShoppingListDTO>> entity = new GenericEntity<List<ShoppingListDTO>>(dtos) {};
		
		return Response.ok(entity).build();
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
