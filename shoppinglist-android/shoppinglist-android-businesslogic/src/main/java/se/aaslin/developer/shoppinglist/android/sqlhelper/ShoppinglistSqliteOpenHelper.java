package se.aaslin.developer.shoppinglist.android.sqlhelper;

import java.sql.SQLException;

import se.aaslin.developer.shoppinglist.android.entity.Property;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class ShoppinglistSqliteOpenHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "shoppinglist.db";
	private static final int DATABASE_VERSION = 1;

	public ShoppinglistSqliteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.i(ShoppinglistSqliteOpenHelper.class.getName(), "constructor");
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource conn) {
		Log.i(ShoppinglistSqliteOpenHelper.class.getName(), "onCreate");
		try {
			TableUtils.createTable(conn, Property.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource conn, int currentVersion, int targetVersion) {
		Log.i(ShoppinglistSqliteOpenHelper.class.getName(), "onUpgrade");
	}
}
