package org.d11.admin.model;

public class PlayerMatchStat {

	private Player player;
	private Team team;

	private int lineup;
	private int substitution_on_time;
	private int substitution_off_time;
	private int goals;
	private int goal_assists;
	private int own_goals;
	private int goals_conceded;
	private int yellow_card_time;
	private int red_card_time;
	private boolean man_of_the_match;
	private boolean shared_man_of_the_match;
	private int rating;
	private String played_position;
	private int position;

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
