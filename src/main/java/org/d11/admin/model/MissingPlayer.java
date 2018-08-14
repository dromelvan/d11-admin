package org.d11.admin.model;

public class MissingPlayer extends D11Model {

    private Integer player_whoscored_id;
    private String player_name;
    private Integer team_id;
    private String team_name;
    private Integer position_id;

    public Integer getPlayerWhoScoredId() {
        return player_whoscored_id;
    }

    public void setPlayerWhoScoredId(Integer player_whoscored_id) {
        this.player_whoscored_id = player_whoscored_id;
    }

    public String getPlayerName() {
        return player_name;
    }

    public void setPlayerName(String player_name) {
        this.player_name = player_name;
    }

    public Integer getTeamId() {
        return team_id;
    }

    public void setTeamId(Integer team_id) {
        this.team_id = team_id;
    }

    public String getTeamName() {
        return team_name;
    }

    public void setTeamName(String team_name) {
        this.team_name = team_name;
    }

    public Integer getPositionId() {
        return position_id;
    }

    public void setPositionId(Integer position_id) {
        this.position_id = position_id;
    }

}
