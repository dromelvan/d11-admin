package org.d11.admin.command;

import java.util.prefs.Preferences;

import org.d11.admin.D11AdminBaseObject;
import org.d11.api.v1.D11API;

import com.google.inject.Inject;

public abstract class D11Command extends D11AdminBaseObject {

	private String name;
	private Preferences preferences;
	@Inject
	private D11API d11Api;

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

	protected D11API getD11Api() {
	    return this.d11Api;
	}

	public abstract void execute();

}
