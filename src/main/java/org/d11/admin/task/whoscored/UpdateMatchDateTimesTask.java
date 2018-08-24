package org.d11.admin.task.whoscored;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.d11.admin.download.whoscored.WhoScoredMatchSeleniumDownloader;
import org.d11.admin.model.Match;
import org.d11.admin.model.MatchDay;
import org.d11.admin.model.whoscored.WSMatch;
import org.d11.admin.parse.whoscored.WhoScoredMatchParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class UpdateMatchDateTimesTask extends WhoScoredDownloaderTask<List<Match>, WhoScoredMatchSeleniumDownloader> {

    @Inject
    private WhoScoredMatchParser parser;
	private final static Logger logger = LoggerFactory.getLogger(UpdateMatchDateTimesTask.class);

	@Override
	public boolean execute() {
        if(getD11Api().isLoggedIn()) {
            setResult(new ArrayList<Match>());

            List<Match> matches = getMatches(getD11Api().getCurrentMatchDay());
            matches.addAll(getMatches(getD11Api().getUpcomingMatchDay()));

            WhoScoredMatchSeleniumDownloader downloader = getDownloader();

            for(Match match : matches) {
                downloader.reset();
                downloader.setWhoScoredId(match.getWhoScoredId());
                downloader.setMatchDay(match.getMatchDayNumber());
                downloader.setSeason(match.getSeasonName());

                File htmlFile = downloader.download();

                WSMatch wsMatch = parser.parse(htmlFile);

                if(!match.getLocalDateTime().equals(wsMatch.getLocalDateTime())) {
                    match = getD11Api().updateMatchDateTime(match.getId(), wsMatch.getDatetime());
                    if (match != null) {
                        logger.debug("Changed match datetime for match {} to {}.", match.getId(), match.getDatetime());
                        getResult().add(match);
                    }
                }
            }

            return true;
        }
	    return false;
	}

	private List<Match> getMatches(MatchDay matchDay) {
	    List<Match> matches = new ArrayList();
        if(matchDay != null) {
            logger.debug("Getting matches for match day {} ({}).", matchDay.getMatchDayNumber(), matchDay.getId());
            for(int matchId : matchDay.getMatchIds()) {
                matches.add(getD11Api().getMatch(matchId));
            }
        }
        return matches;
	}

}
