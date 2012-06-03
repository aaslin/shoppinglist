package se.aaslin.developer.shoppinglist.android.back.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("notificationDTO")
public class NotificationDTO {
	
	private String what;
	private String type;
	private String user;
	private String item;
	
	public NotificationDTO() {
	}
	
	public String getWhat() {
		return what;
	}
	
	public void setWhat(String what) {
		this.what = what;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
}
