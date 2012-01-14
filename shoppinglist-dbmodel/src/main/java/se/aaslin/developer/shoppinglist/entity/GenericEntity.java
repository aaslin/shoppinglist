package se.aaslin.developer.shoppinglist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;

/**
 * 
 * @author lars
 *
 * @param <PK> Primary Key
 * @param <E> Entity
 */
@MappedSuperclass
public abstract class GenericEntity<PK, E> implements Serializable{

	private static final long serialVersionUID = -1534704826424623691L;
		
	
}
