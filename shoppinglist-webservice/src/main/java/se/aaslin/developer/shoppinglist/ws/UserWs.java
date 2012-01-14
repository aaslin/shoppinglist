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

import se.aaslin.developer.shoppinglist.dao.UserDAO;
import se.aaslin.developer.shoppinglist.entity.User;

import com.sun.jersey.api.core.InjectParam;

@Path("/userservice")
public class UserWs extends GenericWs{

	@InjectParam
	private UserDAO userDao;
	
	@POST
	@Path("user")
	@Consumes(APPLICATION_XML)
	public void add(JAXBElement<User> userJAX){
		userDao.persist(userJAX.getValue());
	}
	
	@PUT
	@Path("user")
	@Consumes(APPLICATION_XML)
	public void update(JAXBElement<User> userJAX){
		userDao.update(userJAX.getValue());
	}
	
	@GET
	@Path("user/{id}")
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public User findById(@PathParam("id") String id){
		return userDao.findById(id);
	}
	
	@GET
	@Path("users")
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public List<User> getAll(){
		return userDao.getAll();
	}
	
	@DELETE
	@Path("user/{id}")
	public void delete(@PathParam("id") String id){
		userDao.remove(id);
	}
}
