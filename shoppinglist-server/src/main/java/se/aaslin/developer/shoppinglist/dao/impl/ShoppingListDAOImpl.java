package se.aaslin.developer.shoppinglist.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import se.aaslin.developer.shoppinglist.dao.ShoppingListDAO;
import se.aaslin.developer.shoppinglist.entity.ShoppingList;

@Repository("shoppingListDAO")
public class ShoppingListDAOImpl extends GenericDAOImpl<Integer, ShoppingList> implements ShoppingListDAO {

	@PersistenceContext EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
