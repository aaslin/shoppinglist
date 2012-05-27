package se.aaslin.developer.shoppinglist.dao.impl;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import se.aaslin.developer.shoppinglist.dao.NotificationDAO;
import se.aaslin.developer.shoppinglist.entity.Notification;
import se.aaslin.developer.shoppinglist.entity.Notification_;
import se.aaslin.developer.shoppinglist.entity.TimeStamp;
import se.aaslin.developer.shoppinglist.entity.TimeStamp_;
import se.aaslin.developer.shoppinglist.entity.User;
import se.aaslin.developer.shoppinglist.entity.User_;

@Repository
public class NotificationDAOImpl extends GenericDAOImpl<Integer, Notification> implements NotificationDAO {

	private static final Logger logger = Logger.getLogger(NotificationDAOImpl.class);
			
	@PersistenceContext EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<Notification> getNotifications(String username) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Notification> query = cb.createQuery(Notification.class);
		
		Root<Notification> notification = query.from(Notification.class);
		Join<Notification, User> user = notification.join(Notification_.user);
		Join<Notification, TimeStamp> timeStamp = notification.join(Notification_.timeStamp);
		
		Predicate p1 = cb.equal(user.get(User_.username), username);
		
		query.select(notification);
		query.where(p1);
		query.orderBy(cb.desc(timeStamp.get(TimeStamp_.modified)));
		
		return em.createQuery(query).getResultList();
	}

	@Override
	public void removeNotifications(List<Integer> ids) {
		if (ids.size() == 0) {
			return;
		}
		Query query = em.createQuery("delete from Notification n where n.id in :notifiactionIds");
		query.setParameter("notifiactionIds", ids);
		int deleted = query.executeUpdate();
		
		logger.debug(String.format("%d notifications was deleted", deleted));
	}
}
