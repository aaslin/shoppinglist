package se.aaslin.developer.shoppinglist.android.service.impl;

import java.util.Collection;
import java.util.List;

import se.aaslin.developer.shoppinglist.android.dao.VersionDAO;
import se.aaslin.developer.shoppinglist.android.dto.VersionDTO;
import se.aaslin.developer.shoppinglist.android.entity.Version;
import se.aaslin.developer.shoppinglist.android.service.VersionService;

import com.google.inject.Inject;

public class VersionServiceImpl implements VersionService{
	@Inject VersionDAO versionDAO;

	@Override
	public Version getVersion(String version) {
		return versionDAO.getVersion(version);
	}

	@Override
	public void updateVersions(Collection<VersionDTO> versionDTOs) {
		for (VersionDTO versionDTO : versionDTOs) {
			if (versionDAO.getVersion(versionDTO.getVersionName()) == null) {
				Version version = new Version();
				version.setExecuted(false);
				version.setUpdateScript(versionDTO.getUpdateScript());
				version.setVersionName(versionDTO.getVersionName());
				version.setVersionNumber(versionDTO.getVersionNumber());
				versionDAO.create(version);
			}
		}
	}

	@Override
	public List<Version> getAllVersionsInOrder() {
		return versionDAO.getAllVersionsInOrder();
	}

	@Override
	public void update(Version version) {
		versionDAO.update(version);
	}
}
