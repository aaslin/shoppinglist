package se.aaslin.developer.shoppinglist.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ShoppingList.class)
public abstract class ShoppingList_ extends se.aaslin.developer.shoppinglist.entity.GenericEntity_ {

	public static volatile SingularAttribute<ShoppingList, Long> id;
	public static volatile ListAttribute<ShoppingList, ShoppingItem> items;
	public static volatile SingularAttribute<ShoppingList, String> name;
	public static volatile SingularAttribute<ShoppingList, User> owner;
	public static volatile SetAttribute<ShoppingList, User> members;

}

