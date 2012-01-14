package se.aaslin.developer.shoppinglist.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public abstract class User_ extends se.aaslin.developer.shoppinglist.entity.GenericEntity_ {

	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, String> password;

}

