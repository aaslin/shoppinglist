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
		userDao.create(userJAX.getValue());
	}
	
	@PUT
	@Path("user")
	@Consumes(APPLICATION_XML)
	public void update(JAXBElement<User> userJAX){
		userDao.update(userJAX.getValue());
	}
	
	@GET
	@Path("user/{username}")
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public User findById(@PathParam("username") String username){
		return userDao.findByUsername(username);
	}
	
	@GET
	@Path("users")
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public List<User> getAll(){
		return userDao.list();
	}
	
	@DELETE
	@Path("user/{username}")
	public void delete(@PathParam("username") String username){
		User user = userDao.findByUsername(username);
		if(user != null){
			userDao.delete(user);
		}
	}
}
