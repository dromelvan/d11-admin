package org.d11.admin.model;

public class Season extends PersistentD11Model {

    private String name;
    private int[] d11_team_ids;
    private int[] d11_match_day_ids;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getD11TeamIds() {
        return d11_team_ids;
    }

    public void setD11TeamIds(int[] d11TeamIds) {
        this.d11_team_ids = d11TeamIds;
    }

    public int[] getD11MatchDayIds() {
        return d11_match_day_ids;
    }

    public void setD11MatchDayIds(int[] d11MatchDayIds) {
        this.d11_match_day_ids = d11MatchDayIds;
    }


}
