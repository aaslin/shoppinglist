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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShoppingItemDTO other = (ShoppingItemDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
