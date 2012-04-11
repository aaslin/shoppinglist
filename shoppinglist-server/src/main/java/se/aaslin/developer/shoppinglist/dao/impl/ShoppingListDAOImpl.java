package se.aaslin.developer.shoppinglist.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import se.aaslin.developer.shoppinglist.dao.ShoppingListDAO;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.entity.ShoppingList_;
import se.aaslin.developer.shoppinglist.entity.User;
import se.aaslin.developer.shoppinglist.entity.User_;

@Repository
public class ShoppingListDAOImpl extends GenericDAOImpl<Integer, ShoppingList> implements ShoppingListDAO {

	@PersistenceContext EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	//TODO also include members. Maybe do some changes in the model like introducing a membership relation instead of member/owner
	public List<ShoppingList> getShoppingListsForUser(int userId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ShoppingList> query = cb.createQuery(ShoppingList.class);
		
		Root<ShoppingList> shoppingList = query.from(ShoppingList.class);
		Join<ShoppingList, User> user = shoppingList.join(ShoppingList_.owner);
		
		query.where(cb.equal(user.get(User_.ID), userId));
		
		return em.createQuery(query).getResultList();
	}

}
