package se.aaslin.developer.shoppinglist.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import se.aaslin.developer.shoppinglist.dao.UserDAO;
import se.aaslin.developer.shoppinglist.entity.User;
import se.aaslin.developer.shoppinglist.entity.User_;

@Repository
public class UserDAOImpl extends GenericDAOImpl<Integer, User> implements UserDAO{

	@PersistenceContext EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public User findByUsername(String username) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> query = cb.createQuery(User.class);
		
		Root<User> user = query.from(User.class);
		query.where(cb.equal(user.get(User_.username), username));
		
		try{
			return em.createQuery(query).setMaxResults(1).getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}

}
