package org.d11.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.d11.admin.model.MatchDay;

import com.google.gson.reflect.TypeToken;

public class MatchDaysBySeasonRequest extends D11APIRequest<Map<String, List<MatchDay>>> {

	private final static String REQUEST_URL = "http://%s/api/v1/match_days/by_season/%d";

	public MatchDaysBySeasonRequest(int seasonId) throws MalformedURLException {
		setUrl(new URL(String.format(REQUEST_URL, getAPIHost(), seasonId)));
	}

	public List<MatchDay> getMatchDays() {
		return getResult().get("match_days");
	}

	@Override
	protected TypeToken getTypeToken() {
		return new MatchDaysTypeToken();
	}

}
