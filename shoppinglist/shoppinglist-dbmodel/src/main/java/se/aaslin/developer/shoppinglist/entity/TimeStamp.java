package se.aaslin.developer.shoppinglist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "time_stamp")
public class TimeStamp implements Serializable{

	private static final long serialVersionUID = -3000914429196174006L;

	@Id
	@Column(name = "`ID`", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ID;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "`created`", nullable = false)
	private Date created;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "`modified`", nullable = true)
	private Date modified;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}	
}
