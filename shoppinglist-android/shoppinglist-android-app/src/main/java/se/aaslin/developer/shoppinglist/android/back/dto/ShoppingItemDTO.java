package se.aaslin.developer.shoppinglist.android.back.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("shoppingItemDTO")
public class ShoppingItemDTO implements Serializable {

	private static final long serialVersionUID = 2099641092504099916L;

	@XStreamAlias("id")
	private Integer id;
 
	@XStreamAlias("shoppingListId")
	private Integer shoppingListId;
	
	@XStreamAlias("name")
	private String name;
	
	@XStreamAlias("comment")
	private String comment;
	
	@XStreamAlias("amount")
	private String amount;
	
	@XStreamAlias("changed")
	private boolean changed = false;
	
	@XStreamAlias("fromDB")
	private boolean fromDB = false;
	
	public ShoppingItemDTO() {
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean isChanged) {
		this.changed = isChanged;
	}

	public boolean isFromDB() {
		return fromDB;
	}

	public void setFromDB(boolean isFromDB) {
		this.fromDB = isFromDB;
	}

	public Integer getShoppingListId() {
		return shoppingListId;
	}

	public void setShoppingListId(Integer shoppingListId) {
		this.shoppingListId = shoppingListId;
	}

	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
