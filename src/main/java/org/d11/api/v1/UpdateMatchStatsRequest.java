package org.d11.api.v1;

import java.net.MalformedURLException;
import java.net.URL;

import org.d11.admin.model.UpdateMatchStatsResult;

import com.google.gson.reflect.TypeToken;

public class UpdateMatchStatsRequest extends D11APIRequest<UpdateMatchStatsResult> {

	private final static String REQUEST_URL = "http://%s/api/v1/matches/%s/update_match_stats";

	public UpdateMatchStatsRequest(int matchId, String json) throws MalformedURLException {
		setUrl(new URL(String.format(REQUEST_URL, getAPIHost(), matchId)));
		setRequestMethod(RequestMethod.PUT);
		setRequestParameter("match_stats", json);
	}

	@Override
	protected TypeToken<UpdateMatchStatsResult> getTypeToken() {
		return new UpdateMatchStatsTypeToken();
	}

}
