package se.aaslin.developer.shoppinglist.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.aaslin.developer.shoppinglist.entity.ShoppingItem;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.security.ShoppingListSessionManager;
import se.aaslin.developer.shoppinglist.security.UserSession;
import se.aaslin.developer.shoppinglist.service.ShoppingItemService;
import se.aaslin.developer.shoppinglist.service.ShoppingListService;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.exception.NotAuthorizedException;
import se.aaslin.developer.shoppinglist.shared.exception.NotFoundException;

import com.sun.jersey.api.core.InjectParam;

@Path("/shoppingitem")
@Service
@Transactional
public class ShoppingItemWs extends GenericWs {

	@InjectParam ShoppingListService shoppingListService;
	@InjectParam ShoppingItemService shoppingItemService;
	@InjectParam ShoppingListSessionManager shoppingListSessionManager;
	@InjectParam UserSession userSession;
	
	@GET
	@Produces({ APPLICATION_XML, APPLICATION_JSON })
	@Path("/{shoppingListId}")
	public Response getShoppingItems(@PathParam("shoppingListId") int shoppingListId) {
		try {
			String username = userSession.getCurrentSessionsUsername();
			if(username == null) {
				throw new NotAuthorizedException();
			}
			ShoppingList list = shoppingListService.findShoppingListById(shoppingListId, userSession.getCurrentSessionsUsername());
			List<ShoppingItemDTO> dtos = createShoppingItemDTOs(shoppingListId, list.getItems()); 
			GenericEntity<List<ShoppingItemDTO>> entity = new GenericEntity<List<ShoppingItemDTO>>(dtos) {};
			
			return Response.ok(entity).build();
		} catch (NotAuthorizedException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		} catch (NotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@POST
	@Consumes({APPLICATION_XML})
	@Path("/{shoppingListId}")
	public Response saveShoppingItem(@PathParam("shoppingListId") int shoppingListId, ShoppingItemDTO dto) {
		try {
			ShoppingItem item = extractShoppingItem(dto);
			if (dto.isFromDB() && dto.getId() != 0) {
				shoppingItemService.update(item, userSession.getCurrentSessionsUsername());
			} else {
				shoppingItemService.create(shoppingListId, item, userSession.getCurrentSessionsUsername());
			}
			
			return Response.ok().build();
		} catch (NotAuthorizedException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		} catch (NotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@DELETE
	@Consumes({APPLICATION_XML})
	@Path("/{shoppingItemId}")
	public Response deleteShoppingItem(@PathParam("shoppingItemId") int shoppingItemId) {
		try {
			ShoppingItem item = new ShoppingItem();
			item.setId(shoppingItemId);
			shoppingItemService.remove(item, userSession.getCurrentSessionsUsername());
			
			return Response.ok().build();
		} catch (NotAuthorizedException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
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
}
