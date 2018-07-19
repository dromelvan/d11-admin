package org.d11.admin.write.whoscored;

import java.io.File;

import org.d11.admin.model.Match;
import org.d11.admin.write.JsonWriter;

public class MatchWriter extends JsonWriter<Match> {

	private String season;
	private int matchDayNumber;
	private Match match;

	public MatchWriter() {
		setDirectoryName("whoscored.com/matches/%s/%d");
		setFileName("%s vs %s.json");
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public int getMatchDayNumber() {
		return matchDayNumber;
	}

	public void setMatchDayNumber(int matchDayNumber) {
		this.matchDayNumber = matchDayNumber;
	}

	@Override
	public String formatDirectoryName(String directoryName) {
		return String.format(directoryName, getSeason(), getMatchDayNumber());
	}

	@Override
	public String formatFileName(String fileName) {
		return String.format(fileName, this.match.getHomeTeam().getName(), this.match.getAwayTeam().getName());
	}

	@Override
	public File write(Match match) {
		this.match = match;
		return super.write(match);
	}

}
