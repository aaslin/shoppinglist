package se.aaslin.developer.shoppinglist.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import se.aaslin.developer.shoppinglist.dao.GenericDAO;

public abstract class GenericDAOImpl<PK, E> implements GenericDAO<PK, E> {

	protected abstract EntityManager getEntityManager();

	Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
	}

	@Override
	public void create(E entity) {
		getEntityManager().persist(entity);
	}

	@Override
	public void update(E entity) {
		EntityManager em = getEntityManager();
		em.merge(entity);
	}

	@Override
	public void delete(E entity) {
		EntityManager em = getEntityManager();
		if (em.contains(entity)) {
			em.remove(entity);
		} else {
			em.merge(entity);
			em.remove(entity);
		}
	}

	@Override
	public E findById(PK id) {
		return getEntityManager().find(entityClass, id);
	}

	@Override
	public List<E> list() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> query = cb.createQuery(entityClass);
		
		query.from(entityClass);
		return getEntityManager().createQuery(query).getResultList();
	}
}
