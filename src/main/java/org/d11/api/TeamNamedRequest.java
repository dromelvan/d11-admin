package org.d11.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.d11.admin.model.Team;

import com.google.gson.reflect.TypeToken;

public class TeamNamedRequest extends D11APIRequest<Map<String, Team>> {

	private final static String REQUEST_URL = "http://%s/api/v1/teams/named/%s";

	public TeamNamedRequest(String name) throws MalformedURLException {
		setUrl(new URL(String.format(REQUEST_URL, getAPIHost(), name)));
	}

	public Team getTeam() {
		return getResult().get("team");
	}

	@Override
	protected TypeToken<Map<String, Team>> getTypeToken() {
		return new TeamTypeToken();
	}

}
