package org.d11.admin.parser.premierleague;

import java.util.List;

import org.d11.admin.parser.ParserObject;

public class TeamLineup extends ParserObject {

    private TeamParserObject teamParserObject;
    private List<PlayerParserObject> playerParserObjects;

    public TeamLineup() {}

    public TeamParserObject getTeamParserObject() {
        return teamParserObject;
    }

    public void setTeamParserObject(TeamParserObject teamParserObject) {
        this.teamParserObject = teamParserObject;
    }

    public List<PlayerParserObject> getPlayerParserObjects() {
        return playerParserObjects;
    }

    public void setPlayerParserObjects(List<PlayerParserObject> playerParserObjects) {
        this.playerParserObjects = playerParserObjects;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("TeamLineup\n");
        stringBuilder.append(getTeamParserObject() + "\n");
        for(PlayerParserObject playerParserObject : getPlayerParserObjects()) {
            stringBuilder.append("    " + playerParserObject + "\n");
        }
        return stringBuilder.toString();
    }

}
