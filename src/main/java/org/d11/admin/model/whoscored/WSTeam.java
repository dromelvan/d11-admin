package org.d11.admin.model.whoscored;

import java.util.Map;

import org.d11.admin.model.Team;
import org.d11.admin.parser.whoscored.match.WhoScoredMatchJavaScriptVariables;

public class WSTeam extends Team {

	public WSTeam(Map<String, Object> team) {
		setName((String) team.get(WhoScoredMatchJavaScriptVariables.TEAM_NAME));
		setWhoScoredId((int) team.get(WhoScoredMatchJavaScriptVariables.TEAM_ID));
	}

}
