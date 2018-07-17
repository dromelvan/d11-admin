package org.d11.admin.model.whoscored;

import java.util.Map;

import org.d11.admin.model.Player;
import org.d11.admin.parser.whoscored.match.WhoScoredMatchJavaScriptVariables;

public class WSPlayer extends Player {

    public WSPlayer(Map<String, Object> player) {
        setWhoScoredId((int) player.get(WhoScoredMatchJavaScriptVariables.PLAYER_ID));
        setName((String) player.get(WhoScoredMatchJavaScriptVariables.PLAYER_NAME));
    }
}
