package org.d11.api;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.d11.admin.model.Team;

import com.google.gson.reflect.TypeToken;

public class TeamNamedRequest extends D11APIRequest<Map<String, Team>> {

	private final static String REQUEST_URL = "http://%s/api/v1/teams/named/%s";

	public TeamNamedRequest(String name) throws MalformedURLException {
		try {
			setUrl(new URL(String.format(REQUEST_URL, getAPIHost(), URLEncoder.encode(name, "UTF-8"))));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public Team getTeam() {
		return getResult().get("team");
	}

	@Override
	protected TypeToken<Map<String, Team>> getTypeToken() {
		return new TeamTypeToken();
	}

}
