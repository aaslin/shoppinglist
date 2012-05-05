package se.aaslin.developer.shoppinglist.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ShoppingListDTO implements Serializable {

	private static final long serialVersionUID = -3112563693526623930L;

	private int ID;
	private String ownerUserName;
	private int ownerID;
	private String name;
	private Date modified;
	private List<String> members;
	private boolean isChanged = false;
	private boolean isFromDB = false;

	public ShoppingListDTO() {
	}

	public boolean isChanged() {
		return isChanged;
	}

	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	public boolean isFromDB() {
		return isFromDB;
	}

	public void setFromDB(boolean isFromDB) {
		this.isFromDB = isFromDB;
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
