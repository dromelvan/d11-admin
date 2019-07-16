package org.d11.admin.model;

import org.joda.time.LocalDateTime;

public class Fixture extends D11Model {

	private String datetime;
	private Team home_team;
	private Team away_team;
	private String season_name;	
	
	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public Team getHome_team() {
		return home_team;
	}

	public void setHome_team(Team home_team) {
		this.home_team = home_team;
	}

	public Team getAway_team() {
		return away_team;
	}

	public void setAway_team(Team away_team) {
		this.away_team = away_team;
	}

	public String getSeason_name() {
		return season_name;
	}

	public void setSeason_name(String season_name) {
		this.season_name = season_name;
	}

	public LocalDateTime getLocalDateTime() {
	    if(getDatetime() != null) {
    	    LocalDateTime localDateTime= LocalDateTime.parse(getDatetime().replace("Z", "").replace(" ", "T"));
    	    return localDateTime;
	    }
	    return null;
	}
	
}
