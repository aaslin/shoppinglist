package org.aaslin.developer.webtest.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.aaslin.developer.webtest.entity.GenericEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class GenericDaoImpl<T extends GenericEntity<T>> implements GenericDao<T> {

	@PersistenceContext
	private EntityManager entityManager;
	private Class<?> entityClass;
	
	public GenericDaoImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	public GenericDaoImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		entityClass = (Class<?>) genericSuperclass.getActualTypeArguments()[0];
	}
	
	public void persist(T entity) {
		entity.setCreated(Calendar.getInstance().getTime());
		entityManager.persist(entity);
	}

	@SuppressWarnings("unchecked")
	public void update(T entity){
		entity.setModified(Calendar.getInstance().getTime());
		T managedEntity = (T) entityManager.find(entityClass, entity.getId());
		managedEntity.set(entity);
	}
	
	@SuppressWarnings("unchecked")
	public void remove(Object id) {
		T entity = (T) entityManager.find(entityClass, id);
		entityManager.remove(entity);
	}

	@SuppressWarnings("unchecked")
	public T findById(Object id) {
		return (T) entityManager.find(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		CriteriaQuery<T> criteriaQuery = createCriteriaQuery();
		Root<T> from = (Root<T>) criteriaQuery.from(entityClass);
		CriteriaQuery<T> select = criteriaQuery.select(from);
		TypedQuery<T> typedQuery = (TypedQuery<T>) entityManager.createQuery(select);
		return typedQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	protected CriteriaQuery<T> createCriteriaQuery() {
		return (CriteriaQuery<T>) entityManager.getCriteriaBuilder().createQuery(entityClass);
	}
	
}
