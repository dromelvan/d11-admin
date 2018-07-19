package org.d11.admin.task.whoscored;

import java.io.File;

import org.d11.admin.download.whoscored.WhoScoredMatchSeleniumDownloader;
import org.d11.admin.model.Match;
import org.d11.admin.parse.whoscored.WhoScoredMatchParser;
import org.d11.admin.task.D11Task;
import org.d11.api.D11API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class UpdateMatchDateTimeTask extends D11Task<Match> {

	private String matchId;
	private int matchDayNumber;
	@Inject
	private WhoScoredMatchSeleniumDownloader downloader;
	@Inject
	private WhoScoredMatchParser parser;
	@Inject
	private D11API d11Api;
	private final static Logger logger = LoggerFactory.getLogger(UpdateMatchDateTimeTask.class);

	public UpdateMatchDateTimeTask() {
	}

	public UpdateMatchDateTimeTask(String matchId, int matchDayNumber) {
		this.matchId = matchId;
		this.matchDayNumber = matchDayNumber;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public int getMatchDayNumber() {
		return matchDayNumber;
	}

	public void setMatchDayNumber(int matchDayNumber) {
		this.matchDayNumber = matchDayNumber;
	}

	@Override
	public boolean execute() {
		int whoScoredId = this.d11Api.getMatch(Integer.valueOf(getMatchId())).getWhoScoredId();
		downloader.setId(whoScoredId);
		downloader.setMatchDay(getMatchDayNumber());
		File htmlFile = downloader.download();

		Match match = parser.parse(htmlFile);
		if (match != null) {
			match = this.d11Api.updateMatchDateTime(Integer.valueOf(getMatchId()), match.getDatetime());
			if (match == null) {
				logger.error("Could not update match datetime for match {}.", getMatchId());
				return false;
			} else {
				logger.info("Changed match datetime for match {} to {}.", getMatchId(), match.getDatetime());
				setResult(match);
				return true;
			}
		}
		return false;
	}
}
