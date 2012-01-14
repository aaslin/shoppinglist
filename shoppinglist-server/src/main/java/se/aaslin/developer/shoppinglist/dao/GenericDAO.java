package se.aaslin.developer.shoppinglist.dao;

import java.util.List;

/**
 * 
 * @author lars
 *
 * @param <PK> Primary Key
 * @param <E> Entity
 */
public interface GenericDAO<PK, E> {
	void create(E entity);
	
	void update(E entity);
    
	void delete(E entity);
    
	E findById(PK id);
    
	List<E> list();
}
