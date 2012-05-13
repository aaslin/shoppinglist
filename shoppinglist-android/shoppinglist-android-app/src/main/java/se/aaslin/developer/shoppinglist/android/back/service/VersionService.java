package se.aaslin.developer.shoppinglist.android.back.service;

import java.util.Collection;
import java.util.List;

import se.aaslin.developer.shoppinglist.android.back.dto.VersionDTO;
import se.aaslin.developer.shoppinglist.android.entity.Version;

public interface VersionService {
	
	Version getVersion(String version);

	void updateVersions(Collection<VersionDTO> versions);

	List<Version> getAllVersionsInOrder();

	void update(Version version);
}
