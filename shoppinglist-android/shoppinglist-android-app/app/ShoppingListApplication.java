package se.aaslin.developer.shoppinglist.app;

import java.sql.SQLException;
import java.util.List;

import roboguice.application.RoboApplication;
import se.aaslin.developer.shoppinglist.android.dao.PropertyDAO;
import se.aaslin.developer.shoppinglist.android.dao.impl.PropertyDAOImpl;
import se.aaslin.developer.shoppinglist.android.entity.Property;
import se.aaslin.developer.shoppinglist.android.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.android.service.impl.AuthenticationServiceImpl;
import se.aaslin.developer.shoppinglist.android.sqlhelper.ShoppinglistSqliteOpenHelper;
import android.util.Log;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;

public class ShoppingListApplication extends RoboApplication {

	public static String DOMAIN = "http://192.168.0.12:8080";
	public static String PATH = "/shoppinglist";
	public static String URL = String.format("%s%s/gwt.shoppinglist/", DOMAIN, PATH);
	public static String LOGIN_URL = String.format("%s%s/gwt.login/", DOMAIN, PATH);
	public static String URL_REST = String.format("%s/rest/", URL);
	
	private ConnectionSource connectionSource;
	
	public ShoppingListApplication() {
		super();
		OrmLiteSqliteOpenHelper sqliteOpenHelper = new ShoppinglistSqliteOpenHelper(this);
		connectionSource = sqliteOpenHelper.getConnectionSource();
	}
	
	@Override
	protected void addApplicationModules(List<Module> modules) {

		/**
		 *  DAO module
		 */
		modules.add(new Module() {
			@Override
			public void configure(Binder binder) {
				try {
					RuntimeExceptionDao<Property, Integer> dao = RuntimeExceptionDao.createDao(connectionSource, Property.class);
					binder.bind(new TypeLiteral<RuntimeExceptionDao<Property, Integer>>(){}).toInstance(dao);
					binder.bind(PropertyDAO.class).to(PropertyDAOImpl.class);
				} catch (SQLException e) {
					Log.e(ShoppingListApplication.this.getClass().getName(), e.getMessage());
					e.printStackTrace();
					throw new RuntimeException(e.getMessage());
				}
			}
		});
		
		/**
		 * Business Logic module
		 */
		modules.add(new Module() {
			
			@Override
			public void configure(Binder binder) {
				binder.bind(AuthenticationService.class).to(AuthenticationServiceImpl.class);
			}
		});
	}
}
