package org.d11.admin.model.whoscored;

import java.util.HashMap;
import java.util.Map;

import org.d11.admin.model.Team;
import org.d11.admin.parser.whoscored.match.WhoScoredMatchJavaScriptVariables;

public class WSTeam extends Team {

	private final static Map<Integer, Team> teams = new HashMap<Integer, Team>();

	public WSTeam(Map<String, Object> team) {
		setName((String) team.get(WhoScoredMatchJavaScriptVariables.TEAM_NAME));
		setWhoScoredId((int) team.get(WhoScoredMatchJavaScriptVariables.TEAM_ID));

		teams.put(getWhoScoredId(), this);
	}

	protected static Team get(int whoScoredId) {
		return teams.get(whoScoredId);
	}

}
