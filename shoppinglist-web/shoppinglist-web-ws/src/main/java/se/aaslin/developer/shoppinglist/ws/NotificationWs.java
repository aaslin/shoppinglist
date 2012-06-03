package se.aaslin.developer.shoppinglist.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.aaslin.developer.shoppinglist.entity.Notification;
import se.aaslin.developer.shoppinglist.security.UserSession;
import se.aaslin.developer.shoppinglist.service.NotificationService;
import se.aaslin.developer.shoppinglist.shared.dto.NotificationDTO;

import com.sun.jersey.api.core.InjectParam;

@Path("/notification")
@Service
@Scope("request")
@Transactional
public class NotificationWs extends GenericWs {

	@InjectParam NotificationService notificationService;
	@InjectParam UserSession userSession; 
	
	@GET
	@Produces({ APPLICATION_XML, APPLICATION_JSON })
	public Response getNotifiations() {
		String username = userSession.getCurrentSessionsUsername();
		if(username == null) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		List<Notification> notifications = notificationService.getNotifications(username);
		List<NotificationDTO> dtos = createNotificationDTOs(notifications);
		removeNotifications(notifications);
		GenericEntity<List<NotificationDTO>> entity = new GenericEntity<List<NotificationDTO>>(dtos) {};
		
		return Response.ok(entity).build();
	}

	private void removeNotifications(List<Notification> notifications) {
		List<Integer> ids = new ArrayList<Integer>();
		for (Notification notification : notifications) {
			ids.add(notification.getId());
		}
		
		notificationService.removeNotifications(ids);
	}

	private List<NotificationDTO> createNotificationDTOs(List<Notification> notifications) {
		List<NotificationDTO> dtos = new ArrayList<NotificationDTO>();
		for (Notification notification : notifications) {
			NotificationDTO dto = createNotificationDTO(notification);
			dtos.add(dto);
		}
		
		return dtos;
	}

	private NotificationDTO createNotificationDTO(Notification notification) {
		NotificationDTO dto = new NotificationDTO();
		dto.setWhat(notification.getWhat());
		dto.setType(notification.getType().toString());
		dto.setUser(notification.getCreator().getUsername());
		dto.setItem(notification.getItem().toString());
		
		return dto;
	}
}
