package se.aaslin.developer.shoppinglist.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import se.aaslin.developer.shoppinglist.dao.TimeStampDAO;
import se.aaslin.developer.shoppinglist.entity.TimeStamp;

@Repository
public class TimeStampDAOImpl extends GenericDAOImpl<Integer, TimeStamp> implements TimeStampDAO{

	@PersistenceContext EntityManager entityManager;
	
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
