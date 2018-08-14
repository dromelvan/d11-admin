package org.d11.api.v1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.d11.admin.model.Match;

import com.google.gson.reflect.TypeToken;

public class MatchRequest extends D11APIRequest<Map<String, Match>> {

	private final static String REQUEST_URL = "http://%s/api/v1/matches/%s";

	public MatchRequest(int matchId) throws MalformedURLException {
		setUrl(new URL(String.format(REQUEST_URL, getAPIHost(), matchId)));
	}

	public Match getMatch() {
		return getResult().get("match");
	}

	@Override
	protected TypeToken getTypeToken() {
		return new MatchTypeToken();
	}

}
