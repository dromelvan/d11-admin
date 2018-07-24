package org.d11.admin.model.whoscored;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.d11.admin.model.Goal;
import org.d11.admin.model.Match;
import org.d11.admin.model.PlayerMatchStat;
import org.d11.admin.model.Team;
import org.d11.admin.parse.whoscored.WhoScoredMatchJavaScriptVariables;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WSMatch extends Match {

	private String season;
	private Integer matchDayNumber;
	public final static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm");
	private final static Logger logger = LoggerFactory.getLogger(WSMatch.class);

	public WSMatch() {
	}

	public WSMatch(Map<String, Object> match) {
		setWhoScoredId((Integer) match.get(WhoScoredMatchJavaScriptVariables.MATCH_ID));

		Map matchCentreData = (Map) match.get(WhoScoredMatchJavaScriptVariables.MATCH_CENTRE_DATA);
		Pattern startTimePattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}T\\d{1,2}:\\d{1,2}:\\d{1,2}).*");
		Matcher startTimeMatcher = startTimePattern.matcher((String) matchCentreData.get(WhoScoredMatchJavaScriptVariables.START_TIME));
		if (startTimeMatcher.matches()) {
			DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(startTimeMatcher.group(1), dateTimeFormat);
			setDatetime(dateTime.plusHours(2).toString(DateTimeFormat.forPattern("YYYY-MM-dd HH:mm")));
		} else {
			logger.warn("Could not parse start time from input {}.", matchCentreData.get(WhoScoredMatchJavaScriptVariables.START_TIME));
		}

		setElapsed((String) matchCentreData.get(WhoScoredMatchJavaScriptVariables.ELAPSED));

		Map homeTeamMap = (Map) matchCentreData.get(WhoScoredMatchJavaScriptVariables.HOME_TEAM);
		Map awayTeamMap = (Map) matchCentreData.get(WhoScoredMatchJavaScriptVariables.AWAY_TEAM);
		setHomeTeam(parseTeam(homeTeamMap));
		setAwayTeam(parseTeam(awayTeamMap));

		int homeTeamGoals = 0;
		int awayTeamGoals = 0;
		for (Goal goal : getGoals()) {
			if (goal.getOwnGoal()) {
				goal.setTeam(goal.getTeam() == getHomeTeam() ? getAwayTeam() : getHomeTeam());
			}
			if (goal.getTeam() == getHomeTeam()) {
				homeTeamGoals++;
			} else {
				awayTeamGoals++;
			}
		}

		for (PlayerMatchStat playerMatchStat : getPlayerMatchStats()) {
			if (playerMatchStat.getTeam() == getHomeTeam()) {
				playerMatchStat.setGoalsConceded(awayTeamGoals);
			} else {
				playerMatchStat.setGoalsConceded(homeTeamGoals);
			}
		}
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public Integer getMatchDayNumber() {
		return matchDayNumber;
	}

	public void setMatchDayNumber(Integer matchDayNumber) {
		this.matchDayNumber = matchDayNumber;
	}

	private Team parseTeam(Map teamMap) {
		Team team = new WSTeam(teamMap);
		Map<Integer, PlayerMatchStat> playerMap = new HashMap<Integer, PlayerMatchStat>();

		int maxRating = 0;
		List<PlayerMatchStat> moms = new ArrayList<PlayerMatchStat>();

		List<Map> playerMatchStatMaps = (List<Map>) teamMap.get(WhoScoredMatchJavaScriptVariables.TEAM_PLAYERS);
		for (Map playerMatchStatMap : playerMatchStatMaps) {
			WSPlayerMatchStat playerMatchStat = new WSPlayerMatchStat(playerMatchStatMap);
			playerMatchStat.setTeam(team);
			getPlayerMatchStats().add(playerMatchStat);
			playerMap.put(playerMatchStat.getPlayer().getWhoScoredId(), playerMatchStat);

			if (playerMatchStat.getRating() > maxRating) {
				maxRating = playerMatchStat.getRating();
				moms.clear();
				moms.add(playerMatchStat);
			} else if (playerMatchStat.getRating() == maxRating) {
				moms.add(playerMatchStat);
			}
		}

		for (PlayerMatchStat mom : moms) {
			if (moms.size() > 1) {
				mom.setSharedManOfTheMatch(true);
			} else {
				mom.setManOfTheMatch(true);
			}
		}

		List<Map> incidentEvents = (List<Map>) teamMap.get(WhoScoredMatchJavaScriptVariables.TEAM_INCIDENT_EVENTS);

		for (Map incidentEvent : incidentEvents) {
			Map type = (Map) incidentEvent.get(WhoScoredMatchJavaScriptVariables.TEAM_INCIDENT_EVENT_TYPE);
			int typeValue = (int) type.get(WhoScoredMatchJavaScriptVariables.TEAM_INCIDENT_EVENT_VALUE);

			if (typeValue == WhoScoredMatchJavaScriptVariables.TYPE_GOAL) {
				WSGoal goal = new WSGoal(incidentEvent);
				getGoals().add(goal);
			} else if (typeValue == WhoScoredMatchJavaScriptVariables.TYPE_CARD) {
				// For example Stoke - Everton 6.2 2016
				if (incidentEvent.get(WhoScoredMatchJavaScriptVariables.TEAM_INCIDENT_EVENT_PLAYER_ID) == null) {
					logger.error("Value playerId missing in incident event {}.", incidentEvent);
					continue;
				}
				WSCard card = new WSCard(incidentEvent);
				getCards().add(card);
			} else if (typeValue == WhoScoredMatchJavaScriptVariables.TYPE_SUBSTITUTION_OFF) {
				WSSubstitution substitution = new WSSubstitution(incidentEvent);
				getSubstitutions().add(substitution);
			}
		}

		return team;
	}

}
