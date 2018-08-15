package org.d11.admin.task.whoscored;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.d11.admin.download.whoscored.WhoScoredMatchSeleniumDownloader;
import org.d11.admin.model.Match;
import org.d11.admin.model.MatchDay;
import org.d11.admin.model.Season;
import org.d11.admin.parse.whoscored.WhoScoredMatchParser;
import org.d11.admin.task.D11Task;
import org.d11.admin.write.whoscored.WhoScoredMatchWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class CreateMatchDayMatchFilesTask extends D11Task<List<File>> {

	private String seasonName;
	private Integer matchDayNumber;
	private int matchId;
	@Inject
	private WhoScoredMatchSeleniumDownloader downloader;
	@Inject
	private WhoScoredMatchParser parser;
	@Inject
	private WhoScoredMatchWriter writer;
	private final static Logger logger = LoggerFactory.getLogger(CreateMatchDayMatchFilesTask.class);

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public Integer getMatchDayNumber() {
		return matchDayNumber;
	}

	public void setMatchDayNumber(Integer matchDayNumber) {
		this.matchDayNumber = matchDayNumber;
	}

	public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    @Override
	public boolean execute() {
		Season season = (this.seasonName != null ? getD11Api().getSeason(this.seasonName) : getD11Api().getCurrentSeason());
		if (season == null) {
			logger.error("Could not find season with name {}.", this.seasonName);
			return false;
		}

		MatchDay matchDay = null;
		if (this.matchDayNumber == null) {
			matchDay = getD11Api().getCurrentMatchDay();
		} else {
			matchDay = getD11Api().getMatchDayBySeasonAndMatchDayNumber(season.getName(), this.matchDayNumber);
		}

		if (matchDay == null) {
			logger.error("Could not find match day with number {} in season {}", this.matchDayNumber, season.getName());
			return false;
		}

        logger.debug("Creating match files for match day {}, season {}.", matchDay.getMatchDayNumber(), season.getName());
        List<File> jsonFiles = new ArrayList<File>();

        for (int i = 0; i < matchDay.getMatchIds().length; ++i) {
            int matchId = matchDay.getMatchIds()[i];
            Match match = getD11Api().getMatch(matchId);
            downloader.setId(match.getWhoScoredId());
            downloader.setSeason(season.getName());
            downloader.setMatchDay(matchDay.getMatchDayNumber());

            logger.debug("==> Handling match {}/{}: {} ({}).", i + 1, matchDay.getMatchIds().length, match.getId(), match.getWhoScoredId());

            File htmlFile = downloader.download();
            downloader.reset();

            if (htmlFile != null) {
                match = parser.parse(htmlFile);

                if (match != null) {
                    writer.setSeason(season.getName());
                    writer.setMatchDayNumber(matchDay.getMatchDayNumber());

                    File jsonFile = writer.write(match);
                    jsonFiles.add(jsonFile);
                }
            }
            logger.debug("<== Match done.");
        }

        downloader.close();

        setResult(jsonFiles);
        logger.debug("Match files done.");
		return true;
	}

}
