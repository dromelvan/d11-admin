package org.d11.api.v1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.d11.admin.model.Match;

import com.google.gson.reflect.TypeToken;

public class UpdateMatchDateTimeRequest extends D11APIRequest<Map<String, Match>> {

	private Map<String, String> requestParameters = new HashMap<String, String>();
	private final static String REQUEST_URL = "http://%s/api/v1/matches/%s";

	public UpdateMatchDateTimeRequest(int matchId, String dateTime) throws MalformedURLException {
		setUrl(new URL(String.format(REQUEST_URL, getAPIHost(), matchId)));
		setRequestMethod(RequestMethod.PUT);
		setRequestParameter("match[datetime]", dateTime);
	}

	public Match getMatch() {
		return getResult().get("match");
	}

	@Override
	protected TypeToken getTypeToken() {
		return new MatchTypeToken();
	}

	@Override
	public Map<String, String> getRequestParameters() {
		return requestParameters;
	}
}
