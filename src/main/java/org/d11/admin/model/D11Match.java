package org.d11.admin.model;

public class D11Match extends PersistentD11Model {

    private int d11_match_day_id;
	private D11Team home_d11_team;
	private D11Team away_d11_team;

	public int getD11MatchDayId() {
        return d11_match_day_id;
    }

    public void setD11MatchDayId(int d11MatchDayId) {
        this.d11_match_day_id = d11MatchDayId;
    }

    public D11Team getHomeD11Team() {
		return home_d11_team;
	}

	public void setHomeD11Team(D11Team homeD11Team) {
		this.home_d11_team = homeD11Team;
	}

	public D11Team getAwayD11Team() {
		return away_d11_team;
	}

	public void setAwayD11Team(D11Team awayD11Team) {
		this.away_d11_team = awayD11Team;
	}

}
