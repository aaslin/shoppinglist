package se.aaslin.developer.shoppinglist.android.service.impl;

import java.sql.SQLException;

import se.aaslin.developer.shoppinglist.android.dao.PropertyDAO;
import se.aaslin.developer.shoppinglist.android.entity.Property;
import se.aaslin.developer.shoppinglist.android.service.AuthenticationService;

import com.google.inject.Inject;

public class AuthenticationServiceImpl implements AuthenticationService {

	@Inject PropertyDAO propertyDAO;

	public void storeAuthenticationId(String uuid) {
		try {
			propertyDAO.setProperty("auth", uuid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getAuthenticationId() {
		try {
			Property prop = propertyDAO.getProperty("auth");
			return prop != null ? prop.getValue() : null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}	
}
