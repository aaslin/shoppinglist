package org.aaslin.developer.webtest.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cascade;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@Entity
public class ShoppingList extends GenericEntity<ShoppingList>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@OneToMany(mappedBy="id")
	private List<ShoppingItem> items;
	@ManyToOne
	private User owner;
	@ManyToMany
	private Set<User> members;
	private String name;

	public Object getId() {
		return id;
	}

	public final void setId(long id) {
		this.id = id;
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

	@Override
	public void set(ShoppingList t) {
		this.items = t.items;
		this.members = t.members;
		this.owner = t.owner;
	}
}
