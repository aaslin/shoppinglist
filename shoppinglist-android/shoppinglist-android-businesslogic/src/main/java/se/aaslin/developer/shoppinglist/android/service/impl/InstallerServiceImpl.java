package se.aaslin.developer.shoppinglist.android.service.impl;

import java.io.InputStream;
import java.util.List;

import roboguice.application.RoboApplication;
import se.aaslin.developer.shoppinglist.android.dto.VersionDTO;
import se.aaslin.developer.shoppinglist.android.entity.Version;
import se.aaslin.developer.shoppinglist.android.service.InstallerService;
import se.aaslin.developer.shoppinglist.android.service.VersionService;
import se.aaslin.developer.shoppinglist.android.xml.ingest.VersionIngester;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.google.inject.Inject;

public class InstallerServiceImpl implements InstallerService {
	
	@Inject Context context;
	@Inject VersionService versionService;
	@Inject VersionIngester versionIngester;

	@Override
	public void runInstallProcedure() {
		String currentVersionName = getCurrentVersionName();
		if (currentVersionName == null) {
			throw new RuntimeException("An error have occurred during the installation. Please remove the application from the phone and try to install it again");
		}

		if (versionService.getVersion(currentVersionName) == null) {
			List<VersionDTO> versions = readVersions();
			versionService.updateVersions(versions);
		}

		List<Version> versions = versionService.getAllVersionsInOrder();
		for (Version version : versions) {
			if (version.isExecuted()) {
				continue;
			}
			executUpdateScript(version);
		}
	}

	private String getCurrentVersionName() {
		try {
			ComponentName comp = new ComponentName(context, this.getClass());
			PackageInfo pinfo = context.getPackageManager().getPackageInfo(comp.getPackageName(), 0);
			return pinfo.versionName;
		} catch (NameNotFoundException e) {
			Log.e(this.getClass().getName(), e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	private List<VersionDTO> readVersions() {
		InputStream in = context.getResources().openRawResource(R.raw.versions);
		List<VersionDTO> versions = versionIngester.ingest(in);

		return versions;
	}

	private void executUpdateScript(Version version) {
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			Class<?> clazz = classLoader.loadClass(version.getUpdateScript());
			if (!UpdateScript.class.isAssignableFrom(clazz)) {
				Log.e(this.getClass().getName(), String.format("%s is not assignable from %s in %s", UpdateScript.class.getName(), clazz.getName(), version.getVersionName()));
				return;
			}

			UpdateScript script = (UpdateScript) clazz.newInstance();
			script.setContext(context);
			((RoboApplication)context.getApplicationContext()).getInjector().injectMembers(script);

			script.preUpgrade();
			script.upgrade();
			script.postUpgrade();

			version.setExecuted(true);
			versionService.update(version);

		} catch (ClassNotFoundException e) {
			Log.e(this.getClass().getName(), e.getMessage());
			e.printStackTrace();
		} catch (InstantiationException e) {
			Log.e(this.getClass().getName(), e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.e(this.getClass().getName(), e.getMessage());
			e.printStackTrace();
		}
	}
}
