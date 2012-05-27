package se.aaslin.developer.shoppinglist.shared.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NotificationDTO implements Serializable {
	
	private static final long serialVersionUID = -532778768122879997L;
	
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
