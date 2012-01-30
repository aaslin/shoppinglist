package se.aaslin.developer.shoppinglist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "shopping_item")
public class ShoppingItem {
	
	@Id
	@Column(name = "`ID`", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "`name`", nullable = false, length = 255)
	private String name;
	
	@Column(name = "`amount`", nullable = true, length = 255)
	private String amount;
	
	@Column(name = "`comment`", nullable = true, length = 255)
	private String comment;
	
	@ManyToOne
	@JoinColumn(name="`shopping_listID`", nullable = false)
	private ShoppingList shoppingList;
	
	@OneToOne
	@JoinColumn(name = "`time_stampID`", nullable = false)
	private TimeStamp timeStamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public ShoppingList getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(ShoppingList shoppingList) {
		this.shoppingList = shoppingList;
	}

	public TimeStamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(TimeStamp timeStamp) {
		this.timeStamp = timeStamp;
	}

}
