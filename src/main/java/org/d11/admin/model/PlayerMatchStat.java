package org.d11.admin.model;

public class PlayerMatchStat extends D11Model {

	private Player player;
	private Team team;

	private Integer lineup = 0;
	private Integer substitution_on_time = 0;
	private Integer substitution_off_time = 0;
	private Integer goals = 0;
	private Integer goal_assists = 0;
	private Integer own_goals = 0;
	private Integer goals_conceded = 0;
	private Integer yellow_card_time = 0;
	private Integer red_card_time = 0;
	private Boolean man_of_the_match = false;
	private Boolean shared_man_of_the_match = false;
	private Integer rating = 0;
	private String played_position = "?";
	private Integer position = 0;

	public PlayerMatchStat() {
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public int getLineup() {
		return lineup;
	}

	public void setLineup(int lineup) {
		this.lineup = lineup;
	}

	public int getSubstitutionOnTime() {
		return substitution_on_time;
	}

	public void setSubstitutionOnTime(int substitutionOnTime) {
		this.substitution_on_time = substitutionOnTime;
	}

	public int getSubstitutionOffTime() {
		return substitution_off_time;
	}

	public void setSubstitutionOffTime(int substitutionOffTime) {
		this.substitution_off_time = substitutionOffTime;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public int getGoalAssists() {
		return goal_assists;
	}

	public void setGoalAssists(int goalAssists) {
		this.goal_assists = goalAssists;
	}

	public int getOwnGoals() {
		return own_goals;
	}

	public void setOwnGoals(int ownGoals) {
		this.own_goals = ownGoals;
	}

	public int getGoalsConceded() {
		return goals_conceded;
	}

	public void setGoalsConceded(int goalsConceded) {
		this.goals_conceded = goalsConceded;
	}

	public int getYellowCardTime() {
		return yellow_card_time;
	}

	public void setYellowCardTime(int yellowCardTime) {
		this.yellow_card_time = yellowCardTime;
	}

	public int getRedCardTime() {
		return red_card_time;
	}

	public void setRedCardTime(int redCardTime) {
		this.red_card_time = redCardTime;
	}

	public boolean isManOfTheMatch() {
		return man_of_the_match;
	}

	public void setManOfTheMatch(boolean manOfTheMatch) {
		this.man_of_the_match = manOfTheMatch;
	}

	public boolean isSharedManOfTheMatch() {
		return shared_man_of_the_match;
	}

	public void setSharedManOfTheMatch(boolean sharedManOfTheMatch) {
		this.shared_man_of_the_match = sharedManOfTheMatch;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getPlayedPosition() {
		return played_position;
	}

	public void setPlayedPosition(String playedPosition) {
		this.played_position = playedPosition;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
