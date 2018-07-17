package org.d11.admin.model.whoscored;

import java.util.Map;

import org.d11.admin.model.Player;
import org.d11.admin.model.PlayerMatchStat;

public class WSPlayerMatchStat extends PlayerMatchStat {

    public WSPlayerMatchStat(Map<String, Object> playerMatchStat) {
        Player player = new WSPlayer(playerMatchStat);
        setPlayer(player);

//        setParticipated((((String) player.get(WhoScoredMatchJavaScriptVariables.PLAYER_POSITION)).toUpperCase().equals(WhoScoredMatchJavaScriptVariables.PLAYER_POSITION_SUBSTITUTE) ? 1 : 2));
//        setPlayedPosition((String) player.get(WhoScoredMatchJavaScriptVariables.PLAYER_POSITION));
//
//        Map<String, Object> stats = (Map<String, Object>) player.get(WhoScoredMatchJavaScriptVariables.PLAYER_STATS);
//        if (stats != null) {
//            List<Object> ratings = (List<Object>) stats.get(WhoScoredMatchJavaScriptVariables.PLAYER_STATS_RATINGS);
//            if (ratings != null) {
//                Object ratingObject = ratings.get(ratings.size() - 1);
//                if (ratingObject instanceof Double) {
//                    BigDecimal bigDecimal = BigDecimal.valueOf((Double) ratingObject);
//                    bigDecimal = bigDecimal.movePointRight(2);
//                    setRating(bigDecimal.intValue());
//                } else if (ratingObject instanceof Integer) {
//                    setRating((int) ratingObject * 100);
//                }
//            }
//        }

    }
}
