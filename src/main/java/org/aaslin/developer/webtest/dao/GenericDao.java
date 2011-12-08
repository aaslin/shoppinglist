package org.aaslin.developer.webtest.dao;

import java.util.List;


public interface GenericDao<T> {
	
	public void persist(T entity);
	public void update(T entity);
    public void remove(Object id);
    public T findById(Object id);
    public List<T> getAll();
}
