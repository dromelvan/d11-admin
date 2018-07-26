package org.d11.api;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.d11.admin.model.Player;

import com.google.gson.reflect.TypeToken;

public class PlayersNamedRequest extends D11APIRequest<Map<String, List<Player>>> {

	private final static String REQUEST_URL = "http://%s/api/v1/players/named/%s";

	public PlayersNamedRequest(String name) throws MalformedURLException {
		try {
			setUrl(new URL(String.format(REQUEST_URL, getAPIHost(), URLEncoder.encode(name, "UTF-8"))));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Player> getPlayers() {
		return getResult().get("players");
	}

	@Override
	protected TypeToken<Map<String, List<Player>>> getTypeToken() {
		return new PlayersTypeToken();
	}

}
