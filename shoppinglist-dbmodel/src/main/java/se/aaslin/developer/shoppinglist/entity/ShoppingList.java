package se.aaslin.developer.shoppinglist.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="`shopping_list`", uniqueConstraints = @UniqueConstraint(columnNames = {"`name`", "`userID`"}))
public class ShoppingList implements Serializable{

	private static final long serialVersionUID = -5708566252461176384L;

	@Id
	@Column(name = "`ID`", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ID;
	
	@OneToMany(mappedBy = "shoppingList", targetEntity = ShoppingItem.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ShoppingItem> items;
	
	@ManyToOne
	@JoinColumn(name = "`userID`", nullable = false)
	private User owner;

	@ManyToMany(targetEntity = User.class, cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinTable(name = "`shopping_list_user`", joinColumns = { @JoinColumn(name = "`shopping_listID`") }, inverseJoinColumns = { @JoinColumn(name = "`userID`")})
	private Set<User> members;
	
	@Column(name = "`name`", nullable = false, length=255)
	private String name;
	
	@OneToOne
	@JoinColumn(name = "`time_stampID`", nullable = false)
	private TimeStamp timeStamp;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public List<ShoppingItem> getItems() {
		return items;
	}

	public void setItems(List<ShoppingItem> items) {
		this.items = items;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Set<User> getMembers() {
		return members;
	}

	public void setMembers(Set<User> members) {
		this.members = members;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TimeStamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(TimeStamp timeStamp) {
		this.timeStamp = timeStamp;
	}

}
