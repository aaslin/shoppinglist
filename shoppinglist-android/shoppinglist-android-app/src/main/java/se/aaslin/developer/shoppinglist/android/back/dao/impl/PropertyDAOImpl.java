package se.aaslin.developer.shoppinglist.android.back.dao.impl;

import java.sql.SQLException;

import se.aaslin.developer.shoppinglist.android.back.dao.AbstractDAO;
import se.aaslin.developer.shoppinglist.android.back.dao.PropertyDAO;
import se.aaslin.developer.shoppinglist.android.entity.Property;
import se.aaslin.developer.shoppinglist.android.entity.meta.Property_;

import com.google.inject.Inject;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;

public class PropertyDAOImpl extends AbstractDAO<Property, Integer> implements PropertyDAO {

	@Inject	RuntimeExceptionDao<Property, Integer> dao;
	
	@Override
	protected RuntimeExceptionDao<Property, Integer> getDAO() {
		return dao;
	}

	@Override
	public Property getProperty(String key) throws SQLException {
		QueryBuilder<Property, Integer> query = dao.queryBuilder();
		query.where().eq(Property_.key, key);
		
		return dao.queryForFirst(query.prepare());
	}

	@Override
	public void setProperty(String key, String value) throws SQLException {
		Property property = getProperty(key);
		if (property != null) {
			property.setValue(value);
			dao.update(property);
		} else {
			property = new Property();
			property.setKey(key);
			property.setValue(value);
			dao.create(property);
		}
	}
}
