package se.aaslin.developer.shoppinglist.ws;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.xml.bind.JAXBElement;

import se.aaslin.developer.shoppinglist.dao.ShoppingListDAO;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;

import com.sun.jersey.api.core.InjectParam;

@Path("/shoppinglistservice")
public class ShoppingListWs extends GenericWs{
	
	@InjectParam
	private ShoppingListDAO shoppingListDao;
	
	@POST
	@Path("shoppinglist")
	@Consumes(APPLICATION_XML)
	public void add(JAXBElement<ShoppingList> shoppingListJAX){
		shoppingListDao.persist(shoppingListJAX.getValue());
	}
	
	@PUT
	@Path("shoppinglists")
	@Consumes(APPLICATION_XML)
	public void update(JAXBElement<ShoppingList> shoppingListJAX){
		shoppingListDao.update(shoppingListJAX.getValue());
	}
	
	@GET
	@Path("shoppinglist/{id}")
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public ShoppingList findById(@PathParam("id") Long id){
		return shoppingListDao.findById(id);
	}
	
	@GET
	@Path("shoppinglists")
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public List<ShoppingList> getAll(){
		return shoppingListDao.getAll();
	}
	
	@DELETE
	@Path("shoppinglist/{id}")
	public void delete(@PathParam("id") Long id){
		shoppingListDao.remove(id);
	}

}
