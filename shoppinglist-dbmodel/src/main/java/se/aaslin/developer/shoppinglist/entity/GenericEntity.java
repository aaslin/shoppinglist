package se.aaslin.developer.shoppinglist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GenericEntity<T> implements Serializable{

	private static final long serialVersionUID = -1534704826424623691L;
	private Date created;
	private Date modified;	
	
	public abstract Object getId();
	
	public final Date getCreated() {
		return created;
	}
	
	public final void setCreated(Date created) {
		this.created = created;
	}
	
	public final Date getModified() {
		return modified;
	}
	
	public final void setModified(Date modified) {
		this.modified = modified;
	}
	
	public abstract void set(T t);
}
