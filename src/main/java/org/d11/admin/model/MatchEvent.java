package org.d11.admin.model;

public class MatchEvent extends PersistentD11Model {

	private Match match;
	private Team team;
	private Player player;
	private Integer time;
	private Integer added_time;

	public MatchEvent() {

	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
	    if(time > 90) {
	        setAddedTime(time - 90);
	        time = 90;
	    }
		this.time = time;
	}

	public Integer getAddedTime() {
		return added_time;
	}

	public void setAddedTime(Integer addedTime) {
		this.added_time = addedTime;
	}

}
