package org.d11.api.v1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.d11.admin.model.Player;

import com.google.gson.reflect.TypeToken;

public class PlayerRequest extends D11APIRequest<Map<String, Player>> {

	private final static String REQUEST_URL = "http://%s/api/v1/players/%s";

	public PlayerRequest(int playerId) throws MalformedURLException {
		setUrl(new URL(String.format(REQUEST_URL, getAPIHost(), playerId)));
	}

	public Player getPlayer() {
		return getResult().get("player");
	}

	@Override
	protected TypeToken<Map<String, Player>> getTypeToken() {
		return new PlayerTypeToken();
	}

}
