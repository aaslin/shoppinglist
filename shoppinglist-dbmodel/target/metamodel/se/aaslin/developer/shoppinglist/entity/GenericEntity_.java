package se.aaslin.developer.shoppinglist.entity;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(GenericEntity.class)
public abstract class GenericEntity_ {

	public static volatile SingularAttribute<GenericEntity, Date> created;
	public static volatile SingularAttribute<GenericEntity, Date> modified;

}

