package org.d11.admin.model.whoscored;

import java.util.Map;

import org.d11.admin.model.Card;
import org.d11.admin.model.PlayerMatchStat;
import org.d11.admin.parse.whoscored.WhoScoredMatchJavaScriptVariables;

public class WSCard extends Card {

	public WSCard(Map cardEvent) {
		setPlayer(WSPlayer.get((int) cardEvent.get(WhoScoredMatchJavaScriptVariables.TEAM_INCIDENT_EVENT_PLAYER_ID)));
		setTeam(WSTeam.get((int) cardEvent.get(WhoScoredMatchJavaScriptVariables.TEAM_ID)));
		setTime((int) cardEvent.get(WhoScoredMatchJavaScriptVariables.TEAM_INCIDENT_EVENT_MINUTE) + 1);

		Map cardEventCardType = (Map) cardEvent.get(WhoScoredMatchJavaScriptVariables.TEAM_INCIDENT_EVENT_CARD_TYPE);
		int cardTypeId = (int) cardEventCardType.get(WhoScoredMatchJavaScriptVariables.TEAM_INCIDENT_EVENT_QUALIFIER_VALUE);
		setCardType(cardTypeId == WhoScoredMatchJavaScriptVariables.TYPE_CARD_YELLOW ? CardType.YELLOW.ordinal() : CardType.RED.ordinal());

		PlayerMatchStat playerMatchStat = WSPlayerMatchStat.get(getPlayer().getWhoScoredId());
		if (getCardType() == CardType.YELLOW.ordinal()) {
			playerMatchStat.setYellowCardTime(getTime());
		} else {
			playerMatchStat.setRedCardTime(getTime());
		}
	}

}
