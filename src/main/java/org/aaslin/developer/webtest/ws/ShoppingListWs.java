package org.aaslin.developer.webtest.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.xml.bind.JAXBElement;

import org.aaslin.developer.webtest.dao.ShoppingListDao;
import org.aaslin.developer.webtest.entity.ShoppingList;
import org.aaslin.developer.webtest.entity.User;

import com.sun.jersey.api.core.InjectParam;

@Path("/shoppinglistservice")
public class ShoppingListWs extends GenericWs{
	
	@InjectParam
	private ShoppingListDao shoppingListDao;
	
	@POST
	@Path("shoppinglist")
	@Consumes(APPLICATION_XML)
	public void add(JAXBElement<ShoppingList> shoppingListJAX){
		shoppingListDao.persist(shoppingListJAX.getValue());
	}
	
	@PUT
	@Path("shoppinglist")
	@Consumes(APPLICATION_XML)
	public void update(JAXBElement<ShoppingList> shoppingListJAX){
		shoppingListDao.update(shoppingListJAX.getValue());
	}

}
