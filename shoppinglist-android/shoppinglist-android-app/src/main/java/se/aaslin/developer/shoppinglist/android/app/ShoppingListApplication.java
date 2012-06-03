package se.aaslin.developer.shoppinglist.android.app;

import java.sql.SQLException;
import java.util.List;

import roboguice.application.RoboApplication;
import se.aaslin.developer.shoppinglist.android.back.dao.PropertyDAO;
import se.aaslin.developer.shoppinglist.android.back.dao.VersionDAO;
import se.aaslin.developer.shoppinglist.android.back.dao.impl.PropertyDAOImpl;
import se.aaslin.developer.shoppinglist.android.back.dao.impl.VersionDAOImpl;
import se.aaslin.developer.shoppinglist.android.back.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.android.back.service.InstallerService;
import se.aaslin.developer.shoppinglist.android.back.service.LoginService;
import se.aaslin.developer.shoppinglist.android.back.service.NotificationService;
import se.aaslin.developer.shoppinglist.android.back.service.ShoppingListService;
import se.aaslin.developer.shoppinglist.android.back.service.VersionService;
import se.aaslin.developer.shoppinglist.android.back.service.impl.AuthenticationServiceImpl;
import se.aaslin.developer.shoppinglist.android.back.service.impl.InstallerServiceImpl;
import se.aaslin.developer.shoppinglist.android.back.service.impl.LoginServiceImpl;
import se.aaslin.developer.shoppinglist.android.back.service.impl.NotificationServiceImpl;
import se.aaslin.developer.shoppinglist.android.back.service.impl.ShoppingListServiceImpl;
import se.aaslin.developer.shoppinglist.android.back.service.impl.VersionServiceImpl;
import se.aaslin.developer.shoppinglist.android.back.sqlhelper.ShoppinglistSqliteOpenHelper;
import se.aaslin.developer.shoppinglist.android.back.xml.ingest.VersionIngester;
import se.aaslin.developer.shoppinglist.android.entity.Property;
import se.aaslin.developer.shoppinglist.android.entity.Version;
import android.util.Log;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;

public class ShoppingListApplication extends RoboApplication {

	private ConnectionSource connectionSource;
	
	public ShoppingListApplication() {
		super();
		OrmLiteSqliteOpenHelper sqliteOpenHelper = new ShoppinglistSqliteOpenHelper(this);
		connectionSource = sqliteOpenHelper.getConnectionSource();
	}
	
	@Override
	protected void addApplicationModules(List<Module> modules) {

		/**
		 *  DAO 
		 */
		modules.add(new Module() {
			@Override
			public void configure(Binder binder) {
				try {
					RuntimeExceptionDao<Property, Integer> propertyDao = RuntimeExceptionDao.createDao(connectionSource, Property.class);
					binder.bind(new TypeLiteral<RuntimeExceptionDao<Property, Integer>>(){}).toInstance(propertyDao);
					binder.bind(PropertyDAO.class).to(PropertyDAOImpl.class);
					
					RuntimeExceptionDao<Version, Integer> versionDao = RuntimeExceptionDao.createDao(connectionSource, Version.class);
					binder.bind(new TypeLiteral<RuntimeExceptionDao<Version, Integer>>(){}).toInstance(versionDao);
					binder.bind(VersionDAO.class).to(VersionDAOImpl.class);
					
				} catch (SQLException e) {
					Log.e(ShoppingListApplication.this.getClass().getName(), e.getMessage());
					e.printStackTrace();
					throw new RuntimeException(e.getMessage());
				}
			}
		});
		
		/**
		 * Business Logic
		 */
		modules.add(new Module() {
			
			@Override
			public void configure(Binder binder) {
				binder.bind(AuthenticationService.class).to(AuthenticationServiceImpl.class);
				binder.bind(LoginService.class).to(LoginServiceImpl.class);
				binder.bind(ShoppingListService.class).to(ShoppingListServiceImpl.class);
				binder.bind(VersionService.class).to(VersionServiceImpl.class);
				binder.bind(VersionIngester.class);
				binder.bind(InstallerService.class).to(InstallerServiceImpl.class);
				binder.bind(NotificationService.class).to(NotificationServiceImpl.class);
			}
		});
	}
}
