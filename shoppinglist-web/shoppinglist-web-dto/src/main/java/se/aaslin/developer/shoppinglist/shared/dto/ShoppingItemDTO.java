package se.aaslin.developer.shoppinglist.shared.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShoppingItemDTO implements Serializable{

	private static final long serialVersionUID = -2583911167795796148L;
	
	private Integer id;
	private Integer shoppingListId;
	private String name;
	private String comment;
	private String amount;
	private boolean isChanged = false;
	private boolean isFromDB = false;
	
	public ShoppingItemDTO() {
	}

	public boolean isChanged() {
		return isChanged;
	}

	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	public boolean isFromDB() {
		return isFromDB;
	}

	public void setFromDB(boolean isFromDB) {
		this.isFromDB = isFromDB;
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
