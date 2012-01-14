package se.aaslin.developer.shoppinglist.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ShoppingItem extends GenericEntity<ShoppingItem> {
	
	private static final long serialVersionUID = 687696414711466510L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String amount;
	private String comment;
	@OneToOne
	@JoinColumn(name="id")
	private ShoppingList parent;
	
	public Object getId(){
		return id;
	}

	public final void setId(long id) {
		this.id = id;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ShoppingList getParent() {
		return parent;
	}

	public void setParent(ShoppingList parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void set(ShoppingItem t) {
		this.amount = t.amount;
		this.comment = t.comment;
		this.name = t.name;
		this.parent = t.parent;
	}
}
