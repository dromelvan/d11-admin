package org.d11.admin.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;

public class Match extends PersistentD11Model {

	private Integer whoscored_id;
	private LocalDateTime datetime;
	private Team home_team;
	private Team away_team;
	private String elapsed;
	private List<PlayerMatchStat> player_match_stats = new ArrayList<PlayerMatchStat>();
	private List<Goal> goals = new ArrayList<Goal>();
	private List<Card> cards = new ArrayList<Card>();
	private List<Substitution> substitutions = new ArrayList<Substitution>();

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

	public String getElapsed() {
		return elapsed;
	}

	public void setElapsed(String elapsed) {
		this.elapsed = elapsed;
	}

	public List<PlayerMatchStat> getPlayerMatchStats() {
		return player_match_stats;
	}

	public void setPlayerMatchStats(List<PlayerMatchStat> playerMatchStats) {
		this.player_match_stats = playerMatchStats;
	}

	public List<Goal> getGoals() {
		return goals;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public List<Substitution> getSubstitutions() {
		return substitutions;
	}

	public void setSubstitutions(List<Substitution> substitutions) {
		this.substitutions = substitutions;
	}

}
