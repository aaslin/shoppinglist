package se.aaslin.developer.shoppinglist.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ShoppingItem.class)
public abstract class ShoppingItem_ extends se.aaslin.developer.shoppinglist.entity.GenericEntity_ {

	public static volatile SingularAttribute<ShoppingItem, String> amount;
	public static volatile SingularAttribute<ShoppingItem, Long> id;
	public static volatile SingularAttribute<ShoppingItem, String> name;
	public static volatile SingularAttribute<ShoppingItem, ShoppingList> parent;
	public static volatile SingularAttribute<ShoppingItem, String> comment;

}

