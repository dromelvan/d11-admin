package org.d11.admin.model;

import com.google.gson.Gson;

public class D11Model {

	private final static Gson gson = new Gson();

	@Override
	public String toString() {
		return String.format("%s %s", getClass().getSimpleName(), gson.toJson(this));
	}

}
