package org.d11.admin.model.whoscored;

import java.util.HashMap;
import java.util.Map;

import org.d11.admin.model.Player;
import org.d11.admin.parse.whoscored.WhoScoredMatchJavaScriptVariables;

public class WSPlayer extends Player {

	private final static Map<Integer, Player> players = new HashMap<Integer, Player>();

	public WSPlayer(Map<String, Object> player) {
		setWhoScoredId((int) player.get(WhoScoredMatchJavaScriptVariables.PLAYER_ID));
		setName((String) player.get(WhoScoredMatchJavaScriptVariables.PLAYER_NAME));

		players.put(getWhoScoredId(), this);
	}

	protected static Player get(int whoScoredId) {
		return players.get(whoScoredId);
	}

}
