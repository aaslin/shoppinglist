package se.aaslin.developer.shoppinglist.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import se.aaslin.developer.shoppinglist.dao.ShoppingItemDAO;
import se.aaslin.developer.shoppinglist.entity.ShoppingItem;
import se.aaslin.developer.shoppinglist.entity.ShoppingItem_;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;
import se.aaslin.developer.shoppinglist.entity.ShoppingList_;

@Repository
public class ShoppingItemDAOImpl extends GenericDAOImpl<Integer, ShoppingItem> implements ShoppingItemDAO {

	@PersistenceContext EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public List<ShoppingItem> getShoppingListItems(int shoppingListId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ShoppingItem> query = cb.createQuery(ShoppingItem.class);
		
		Root<ShoppingItem> shoppingItem = query.from(ShoppingItem.class);
		Join<ShoppingItem, ShoppingList> shoppingList = shoppingItem.join(ShoppingItem_.shoppingList);
		
		query.where(cb.equal(shoppingList.get(ShoppingList_.ID), shoppingListId));
		
		return em.createQuery(query).getResultList();
	}


}
