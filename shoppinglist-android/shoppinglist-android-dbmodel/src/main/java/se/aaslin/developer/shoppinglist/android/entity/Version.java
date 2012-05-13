package se.aaslin.developer.shoppinglist.android.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "version")
public class Version {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ID;

	@Column(name = "versionName", nullable = false, length = 256)
	private String versionName;

	@Column(name = "versionNumber", nullable = false)
	private int versionNumber;

	@Column(name = "updateScript", nullable = false, length = 256)
	private String updateScript;

	@Column(name = "executed", nullable = false)
	private boolean executed;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getUpdateScript() {
		return updateScript;
	}

	public void setUpdateScript(String updateScript) {
		this.updateScript = updateScript;
	}

	public boolean isExecuted() {
		return executed;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

}
