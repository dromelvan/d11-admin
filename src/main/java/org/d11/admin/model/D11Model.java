package org.d11.admin.model;

import java.text.Normalizer;

import com.google.gson.Gson;

public class D11Model {

	private final static Gson gson = new Gson();

	protected String parameterize(String string) {
        String parameterizedString = string.toLowerCase();
        parameterizedString = parameterizedString.replace("_", " ");
        parameterizedString  = Normalizer.normalize(parameterizedString, Normalizer.Form.NFD);
        parameterizedString = parameterizedString.replaceAll("'", "-");
        parameterizedString = parameterizedString.replaceAll("[^a-z &,-]", "").trim();
        parameterizedString = parameterizedString.replace(" ", "-");
        return parameterizedString;
	}

	@Override
	public String toString() {
		return String.format("%s %s", getClass().getSimpleName(), gson.toJson(this));
	}

}
