package se.aaslin.developer.shoppinglist.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import se.aaslin.developer.shoppinglist.dao.GenericDAO;
import se.aaslin.developer.shoppinglist.entity.GenericEntity;

@Transactional
public abstract class GenericDAOImpl<T extends GenericEntity<T>> implements GenericDAO<T> {

	@PersistenceContext
	private EntityManager entityManager;
	private Class<?> entityClass;
	
	public GenericDAOImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	public GenericDAOImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		entityClass = (Class<?>) genericSuperclass.getActualTypeArguments()[0];
	}
	
	@Override
	public void persist(T entity) {
		entity.setCreated(Calendar.getInstance().getTime());
		entityManager.persist(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(T entity){
		entity.setModified(Calendar.getInstance().getTime());
		T managedEntity = (T) entityManager.find(entityClass, entity.getId());
		managedEntity.set(entity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void remove(Object id) {
		T entity = (T) entityManager.find(entityClass, id);
		entityManager.remove(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
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
