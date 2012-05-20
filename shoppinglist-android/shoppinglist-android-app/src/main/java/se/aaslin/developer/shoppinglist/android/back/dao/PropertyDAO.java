package se.aaslin.developer.shoppinglist.android.back.dao;

import java.sql.SQLException;

import se.aaslin.developer.shoppinglist.android.entity.Property;

public interface PropertyDAO extends GenericDAO<Property, Integer> {
	
	Property getProperty(String key) throws SQLException;
	
	void setProperty(String key, String value) throws SQLException;
	
	void removeProperty(String key) throws SQLException;
}
