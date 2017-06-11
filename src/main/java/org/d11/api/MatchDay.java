package org.d11.api;

public class MatchDay {

    private int id;
    private int match_day_number;
    private int[] match_ids;

    public MatchDay() {}

    public MatchDay(int id, int matchDayNumber, int[] matchIds) {
        this.id = id;
        this.match_day_number = matchDayNumber;
        this.match_ids = matchIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatchDayNumber() {
        return match_day_number;
    }

    public void setMatchDayNumber(int matchDayNumber) {
        this.match_day_number = matchDayNumber;
    }

    public int[] getMatchIds() {
        return match_ids;
    }

    public void setMatchIds(int[] matchIds) {
        this.match_ids = matchIds;
    }

}
