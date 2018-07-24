package org.d11.admin.command;

import java.util.prefs.Preferences;

import org.d11.admin.D11AdminBaseObject;

public abstract class D11Command extends D11AdminBaseObject {

	private String name;
	private Preferences preferences;

	public D11Command() {
		this.preferences = Preferences.userNodeForPackage(getClass());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected Preferences getPreferences() {
		return preferences;
	}

	public abstract void execute();

}
