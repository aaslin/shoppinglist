package org.aaslin.developer.webtest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GenericEntity<T> implements Serializable{
	
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
