package se.aaslin.developer.shoppinglist.android.back.dao.impl;

import java.sql.SQLException;
import java.util.List;

import se.aaslin.developer.shoppinglist.android.back.dao.AbstractDAO;
import se.aaslin.developer.shoppinglist.android.back.dao.VersionDAO;
import se.aaslin.developer.shoppinglist.android.entity.Version;
import se.aaslin.developer.shoppinglist.android.entity.meta.Version_;

import com.google.inject.Inject;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;


public class VersionDAOImpl extends AbstractDAO<Version, Integer> implements VersionDAO {

	@Inject	RuntimeExceptionDao<Version, Integer> dao;

	@Override
	protected RuntimeExceptionDao<Version, Integer> getDAO() {
		return dao;
	}

	@Override
	public Version getVersion(String version) {
		try {
			QueryBuilder<Version, Integer> query = dao.queryBuilder();

			Where<Version, Integer> where = query.where();
			where.eq(Version_.versionName, version);

			PreparedQuery<Version> preparedQuery = query.prepare();

			return dao.queryForFirst(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Version> getAllVersionsInOrder() {
		try {
			QueryBuilder<Version, Integer> query = dao.queryBuilder();

			query.orderBy(Version_.versionNumber, true);

			PreparedQuery<Version> preparedQuery = query.prepare();

			return dao.query(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
