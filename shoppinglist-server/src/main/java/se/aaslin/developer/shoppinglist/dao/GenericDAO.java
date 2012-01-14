package se.aaslin.developer.shoppinglist.dao;

import java.util.List;


public interface GenericDAO<T> {
	void persist(T entity);
	
	void update(T entity);
    
	void remove(Object id);
    
	T findById(Object id);
    
	List<T> getAll();
}
