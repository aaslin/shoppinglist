package se.aaslin.developer.shoppinglist.shared.dto;

import java.io.Serializable;

public class DashboardListPortletDTO implements Serializable {
	
	private static final long serialVersionUID = 3482668868526716652L;
	
	private String name;
	private String owner;
	private int shoppingListId;

	public DashboardListPortletDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getShoppingListId() {
		return shoppingListId;
	}

	public void setShoppingListId(int shoppingListId) {
		this.shoppingListId = shoppingListId;
	}
}
