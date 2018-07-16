package org.d11.admin.model;

import org.joda.time.LocalDateTime;

public class Match extends PersistentD11Model {

    private Integer whoscored_id;
    private LocalDateTime datetime;
    private Team home_team;
    private Team away_team;

    public Match(int id) {
        super(id);
    }

    public Match(int id, int whoScoredId) {
        super(id);
        this.whoscored_id = whoScoredId;
    }

    public int getWhoScoredId() {
        return whoscored_id;
    }

    public void setWhoScoredId(int whoScoredId) {
        this.whoscored_id = whoScoredId;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public Team getHomeTeam() {
        return home_team;
    }

    public void setHomeTeam(Team homeTeam) {
        this.home_team = homeTeam;
    }

    public Team getAwayTeam() {
        return away_team;
    }

    public void setAwayTeam(Team awayTeam) {
        this.away_team = awayTeam;
    }


}
