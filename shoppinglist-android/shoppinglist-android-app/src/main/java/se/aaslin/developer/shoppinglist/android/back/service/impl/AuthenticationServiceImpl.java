package se.aaslin.developer.shoppinglist.android.back.service.impl;

import java.sql.SQLException;

import se.aaslin.developer.shoppinglist.android.back.dao.PropertyDAO;
import se.aaslin.developer.shoppinglist.android.back.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.android.entity.Property;

import android.util.Log;

import com.google.inject.Inject;

public class AuthenticationServiceImpl implements AuthenticationService {

	private static final String PASSWORD_KEY = "password";
	private static final String USERNAME_KEY = "username";
	private static final String AUTH_KEY = "auth";
	@Inject PropertyDAO propertyDAO;

	@Override
	public void storeAuthenticationId(String uuid) {
		storeProperty(AUTH_KEY, uuid);
	}
	
	@Override
	public void removeAuthenticationId() {
		try {
			removeProperty(AUTH_KEY);
		} catch (SQLException e) {
			Log.e(this.getClass().getCanonicalName(), e.getMessage(), e);
		}
	}

	@Override
	public String getAuthenticationId() {
		return getProperty(AUTH_KEY);
	}

	@Override
	public void storeUsername(String username) {
		storeProperty(USERNAME_KEY, username);
	}

	@Override
	public String getUsername() {
		return getProperty(USERNAME_KEY);
	}

	@Override
	public void storePassword(String password) {
		storeProperty(PASSWORD_KEY, password);
	}

	@Override
	public String getPassword() {
		return getProperty(PASSWORD_KEY);
	}	
	
	private void storeProperty(String key, String value) {
		try {
			propertyDAO.setProperty(key, value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getProperty(String key) {
		try {
			Property prop = propertyDAO.getProperty(key);
			return prop != null ? prop.getValue() : null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void removeProperty(String key) throws SQLException {
		propertyDAO.removeProperty(key);
	}
}
