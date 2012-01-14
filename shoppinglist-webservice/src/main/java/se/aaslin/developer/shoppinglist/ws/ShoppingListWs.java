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
import javax.xml.bind.annotation.XmlElement;

import se.aaslin.developer.shoppinglist.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.service.shoppinglist.ShoppingListServiceImpl;

import com.sun.jersey.api.core.InjectParam;

@Path("/shoppinglist")
public class ShoppingListWs extends GenericWs {

	@InjectParam
	private ShoppingListServiceImpl shoppingListService;

	@POST
	@Consumes(APPLICATION_XML)
	public void add(JAXBElement<ShoppingListDTO> shoppingListJAX) {
		shoppingListService.add(shoppingListJAX.getValue());
	}

	@PUT
	@Consumes(APPLICATION_XML)
	public void update(JAXBElement<ShoppingListDTO> shoppingListJAX) {
		shoppingListService.update(shoppingListJAX.getValue());
	}

	@GET
	@Path("{id}")
	@Produces({ APPLICATION_XML, APPLICATION_JSON })
	public ShoppingListDTO findById(@PathParam("id") Integer id) {
		return shoppingListService.findById(id);
	}

	@GET
	@Produces({ APPLICATION_XML, APPLICATION_JSON })
	public List<ShoppingListDTO> getAll() {
		return shoppingListService.getAll();
	}

	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") Integer id) {
		shoppingListService.remove(id);

	}

}
