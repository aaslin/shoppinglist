package se.aaslin.developer.shoppinglist.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {
	public enum Type {
		ADDED, UPDATED, REMOVED
	}
	
	public enum Item {
		LIST, ITEM;
	}
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "what", nullable = false)
	private String what;
	
	@Column(name = "type", nullable = false)
	private Type type;
	
	@Column(name = "item", nullable = false)
	private Item item;
	
	@ManyToOne
	@JoinColumn(name = "userID", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "creatorID", nullable = false)
	private User creator;
	
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "`time_stampID`", nullable = false)
	private TimeStamp timeStamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWhat() {
		return what;
	}

	public void setWhat(String what) {
		this.what = what;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public TimeStamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(TimeStamp timeStamp) {
		this.timeStamp = timeStamp;
	}
}
