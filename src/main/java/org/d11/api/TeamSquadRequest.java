package org.d11.api;

import java.net.MalformedURLException;
import java.net.URL;

import org.d11.admin.model.TeamSquad;

import com.google.gson.reflect.TypeToken;

public class TeamSquadRequest extends D11APIRequest<TeamSquad> {

	private final static String REQUEST_URL = "http://%s/api/v1/teams/%s/lineup";

	public TeamSquadRequest(int teamId) throws MalformedURLException {
		setUrl(new URL(String.format(REQUEST_URL, getAPIHost(), teamId)));
	}

	public TeamSquad getTeamSquad() {
		return getResult();
	}

	@Override
	protected TypeToken getTypeToken() {
		return new TeamSquadTypeToken();
	}

}
