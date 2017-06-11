package org.d11.admin.parser.whoscored.match;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.d11.admin.parser.match.PlayerParserObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhoScoredPlayerParserObject extends PlayerParserObject {

    private String playedPosition;
    private final static Logger logger = LoggerFactory.getLogger(WhoScoredPlayerParserObject.class);

	public WhoScoredPlayerParserObject(Map player) {
		setWhoScoredId((int) player.get(WhoScoredMatchJavaScriptVariables.PLAYER_ID));
		setName((String) player.get(WhoScoredMatchJavaScriptVariables.PLAYER_NAME));
		setParticipated((((String) player.get(WhoScoredMatchJavaScriptVariables.PLAYER_POSITION)).toUpperCase().equals(WhoScoredMatchJavaScriptVariables.PLAYER_POSITION_SUBSTITUTE) ? 1 : 2));
		setPlayedPosition((String) player.get(WhoScoredMatchJavaScriptVariables.PLAYER_POSITION));

		Map<String, Object> stats = (Map<String, Object>) player.get(WhoScoredMatchJavaScriptVariables.PLAYER_STATS);
		if (stats != null) {
			List<Object> ratings = (List<Object>) stats.get(WhoScoredMatchJavaScriptVariables.PLAYER_STATS_RATINGS);
			if (ratings != null) {
				Object ratingObject = ratings.get(ratings.size() - 1);
				if (ratingObject instanceof Double) {
					BigDecimal bigDecimal = BigDecimal.valueOf((Double) ratingObject);
					bigDecimal = bigDecimal.movePointRight(2);
					setRating(bigDecimal.intValue());
				} else if (ratingObject instanceof Integer) {
					setRating((int) ratingObject * 100);
				}
			}
		}
	}

	public String getPlayedPosition() {
        return playedPosition;
    }

    public void setPlayedPosition(String playedPosition) {
        this.playedPosition = playedPosition;
    }

    public int getPosition() {
        switch(getPlayedPosition().toUpperCase()) {
            case "GK": return 1;
            case "DC": case "DL": case "DR": return 3;
            case "MC": case "ML": case "MR": case "DMC": case "DML": case "DMR": case "AMC": case "AML": case "AMR":   return 4;
            case "FW": case "FWL": case "FWR": return 5;
            case "SUB": return 6;
            default:
                logger.warn("Unknown played position {}.", getPlayedPosition());
              return 6;
        }
    }

    @Override
	public String toString() {
		return String.format("Name: %s (%s) Participated: %s Rating: %s Assists: %s", getName(), getWhoScoredId(), getParticipated(), getRating(), getAssists());
	}
}
