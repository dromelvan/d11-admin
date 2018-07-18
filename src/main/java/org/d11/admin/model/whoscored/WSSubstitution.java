package org.d11.admin.model.whoscored;

import java.util.Map;

import org.d11.admin.model.PlayerMatchStat;
import org.d11.admin.model.Substitution;
import org.d11.admin.parse.whoscored.WhoScoredMatchJavaScriptVariables;

public class WSSubstitution extends Substitution {

	public WSSubstitution(Map substitutionOutEvent) {
		setPlayer(WSPlayer.get((int) substitutionOutEvent.get(WhoScoredMatchJavaScriptVariables.TEAM_INCIDENT_EVENT_PLAYER_ID)));
		setTime((int) substitutionOutEvent.get(WhoScoredMatchJavaScriptVariables.TEAM_INCIDENT_EVENT_MINUTE) + 1);
		setPlayerIn(WSPlayer.get((int) substitutionOutEvent.get(WhoScoredMatchJavaScriptVariables.TEAM_INCIDENT_EVENT_RELATED_PLAYER_ID)));
		setTeam(WSTeam.get((int) substitutionOutEvent.get(WhoScoredMatchJavaScriptVariables.TEAM_ID)));

		PlayerMatchStat playerMatchStat = WSPlayerMatchStat.get(getPlayer().getWhoScoredId());
		playerMatchStat.setSubstitutionOffTime(getTime());

		playerMatchStat = WSPlayerMatchStat.get(getPlayerIn().getWhoScoredId());
		playerMatchStat.setSubstitutionOnTime(getTime());
	}
}
