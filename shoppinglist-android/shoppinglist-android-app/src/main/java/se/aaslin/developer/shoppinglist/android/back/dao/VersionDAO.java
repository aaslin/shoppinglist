package se.aaslin.developer.shoppinglist.android.back.dao;

import java.util.List;

import se.aaslin.developer.shoppinglist.android.entity.Version;

public interface VersionDAO extends GenericDAO<Version, Integer>{

	Version getVersion(String version);

	List<Version> getAllVersionsInOrder();
}
