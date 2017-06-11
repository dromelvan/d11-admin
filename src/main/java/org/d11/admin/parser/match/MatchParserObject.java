package org.d11.admin.parser.match;

import java.util.HashSet;
import java.util.Set;

import org.d11.admin.parser.ParserObject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatchParserObject extends ParserObject {

	private DateTime dateTime;
	private String timeElapsed;
	private TeamParserObject homeTeam;
	private TeamParserObject awayTeam;
	private final static Logger logger = LoggerFactory.getLogger(MatchParserObject.class);

	public MatchParserObject() {
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getTimeElapsed() {
		return timeElapsed;
	}

	public void setTimeElapsed(String timeElapsed) {
		this.timeElapsed = timeElapsed;
	}

	public TeamParserObject getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(TeamParserObject homeTeam) {
		this.homeTeam = homeTeam;
	}

	public TeamParserObject getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(TeamParserObject awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Set<TeamParserObject> getTeams() {
		Set<TeamParserObject> teams = new HashSet<TeamParserObject>();
		teams.add(this.homeTeam);
		teams.add(this.awayTeam);
		return teams;
	}

	public TeamParserObject getTeam(String name) {
		if (name.equalsIgnoreCase(this.homeTeam.getName())) {
			return this.homeTeam;
		} else if (name.equalsIgnoreCase(this.awayTeam.getName())) {
			return this.awayTeam;
		}
		logger.warn("Could not find team {} in {}.", name, this);
		return null;
	}

	@Override
	public String toString() {
		return String.format("Match: %s vs %s Kick off: %s Time elapsed: %s", getHomeTeam(), getAwayTeam(), getDateTime(), getTimeElapsed());
	}
}
