package org.d11.api;

public class Match {

    private int id;
    private int whoscored_id;
    private String datetime;

    public Match() {}

    public Match(int id, int whoScoredId) {
        this.id = id;
        this.whoscored_id = whoScoredId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWhoScoredId() {
        return whoscored_id;
    }

    public void setWhoScoredId(int whoScoredId) {
        this.whoscored_id = whoScoredId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return String.format("Match %d (%d) %s", getId(), getWhoScoredId(), getDatetime());
    }
}
