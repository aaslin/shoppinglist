package se.aaslin.developer.shoppinglist.android.back.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("version")
public class VersionDTO {

	@XStreamAlias("name")
	private String versionName;

	@XStreamAlias("number")
	private int versionNumber;

	@XStreamAlias("script")
	private String updateScript;

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String name) {
		this.versionName = name;
	}

	public String getUpdateScript() {
		return updateScript;
	}

	public void setUpdateScript(String updateScript) {
		this.updateScript = updateScript;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}
}
