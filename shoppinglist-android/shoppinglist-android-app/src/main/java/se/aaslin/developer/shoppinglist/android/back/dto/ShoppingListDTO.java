package se.aaslin.developer.shoppinglist.android.back.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("shoppingListDTO")
public class ShoppingListDTO implements Serializable {

	private static final long serialVersionUID = -3112563693526623930L;

	@XStreamAlias("ID")
	private int ID;
	@XStreamAlias("ownerUserName")
	private String ownerUserName;
	@XStreamAlias("ownerID")
	private int ownerID;
	@XStreamAlias("name")
	private String name;
	@XStreamAlias("modified")
	private Date modified;
	@XStreamAlias("members")
	private List<String> members;
	@XStreamAlias("changed")
	private boolean changed = false;
	@XStreamAlias("fromDB")
	private boolean fromDB = false;

	public ShoppingListDTO() {
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean isChanged) {
		this.changed = isChanged;
	}

	public boolean isFromDB() {
		return fromDB;
	}

	public void setFromDB(boolean isFromDB) {
		this.fromDB = isFromDB;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getOwnerUserName() {
		return ownerUserName;
	}

	public void setOwnerUsername(String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShoppingListDTO other = (ShoppingListDTO) obj;
		if (ID != other.ID)
			return false;
		return true;
	}
}
