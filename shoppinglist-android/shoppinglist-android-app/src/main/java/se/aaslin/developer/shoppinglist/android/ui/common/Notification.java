package se.aaslin.developer.shoppinglist.android.ui.common;

import java.io.Serializable;

public class Notification implements Serializable {
	
	private static final long serialVersionUID = 1621449023697895648L;

	public enum Type  {
		ADDED, UPDATED, REMOVED
	}
	
	public enum Item {
		LIST, ITEM;
	}
	
	private Type type;
	private Item item;
	private String what;
	private String username;
	
	public Notification(Type type, String what, String username) {
		this.type = type;
		this.what = what;
		this.username = username;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getWhat() {
		return what;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
