package se.aaslin.developer.shoppinglist.shared.dto;

import java.io.Serializable;


public class ShoppingItemDTO implements Serializable{

	private static final long serialVersionUID = -2583911167795796148L;
	
	private String name;
	private String comment;
	private String amount;
	
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
}
