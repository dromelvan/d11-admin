package org.d11.admin.model.whoscored;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.d11.admin.model.PlayerMatchStat;
import org.d11.admin.model.Team;
import org.d11.admin.parser.whoscored.match.WhoScoredMatchJavaScriptVariables;

public class WSTeam extends Team {

    private List<PlayerMatchStat> playerMatchStats = new ArrayList<PlayerMatchStat>();

    public WSTeam(Map<String, Object> team) {
        setName((String) team.get(WhoScoredMatchJavaScriptVariables.TEAM_NAME));
        setWhoScoredId((int) team.get(WhoScoredMatchJavaScriptVariables.TEAM_ID));

        Map<Integer, PlayerMatchStat> playerMap = new HashMap<Integer, PlayerMatchStat>();
        List<Map> playerMatchStats = (List<Map>) team.get(WhoScoredMatchJavaScriptVariables.TEAM_PLAYERS);
        for (Map playerMatchStat : playerMatchStats) {
            WSPlayerMatchStat wsPlayerMatchStat = new WSPlayerMatchStat(playerMatchStat);
            getPlayerMatchStats().add(wsPlayerMatchStat);
            playerMap.put(wsPlayerMatchStat.getPlayer().getWhoScoredId(), wsPlayerMatchStat);
        }

    }

    public List<PlayerMatchStat> getPlayerMatchStats() {
        return playerMatchStats;
    }

    public void setPlayerMatchStats(List<PlayerMatchStat> playerMatchStats) {
        this.playerMatchStats = playerMatchStats;
    }
}
