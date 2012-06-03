package se.aaslin.developer.shoppinglist.android.ui.common;

import java.io.Serializable;

import se.aaslin.developer.shoppinglist.android.back.dto.NotificationDTO;

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
	
	public Notification(Type type, Item item, String what, String username) {
		this.type = type;
		this.item = item;
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
	
	public static Notification convertDTO(NotificationDTO dto) {
		Notification.Type type = Notification.Type.valueOf(dto.getType());
		Notification.Item item = Notification.Item.valueOf(dto.getItem());
		Notification notification = new Notification(type, item, dto.getWhat(), dto.getUser());
		
		return notification;
	}
}
