package se.aaslin.developer.shoppinglist.dao.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;

import se.aaslin.developer.shoppinglist.dao.UserDAO;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.entity.ShoppingList_;
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

	@Override
	public Set<User> findUsers(List<String> usernames) {
		if (usernames == null || usernames.size() == 0) {
			return new HashSet<User>();
		}
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> query = cb.createQuery(User.class);
		
		Root<User> user = query.from(User.class);
		query.where(user.get(User_.username).in(usernames));
		
		return new HashSet<User>(em.createQuery(query).getResultList());
	}

	@Override
	public void register(String username, String registration) {
		User user = findByUsername(username);
		user.setRegistration(registration);
	}

	@Override
	public List<User> getAllUsers(ShoppingList list, Collection<String> except) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> query = cb.createQuery(User.class);
		
		Root<User> user = query.from(User.class);
		
		Subquery<Integer> subQuery = query.subquery(Integer.class);
		Root<ShoppingList> shoppingList = subQuery.from(ShoppingList.class);
		Join<ShoppingList, User> member = shoppingList.join(ShoppingList_.members);
		Join<ShoppingList, User> owner = shoppingList.join(ShoppingList_.owner);
		
		Predicate p1 = cb.not(user.get(User_.username).in(except));
		Predicate p2 = cb.equal(shoppingList, list);
		Predicate p3 = cb.or(user.in(member), user.in(owner));
		
		subQuery.select(cb.literal(1));
		subQuery.where(p1, p2, p3);
		
		query.select(user);
		query.where(cb.exists(subQuery));
		
		return em.createQuery(query).getResultList();
	}
}
