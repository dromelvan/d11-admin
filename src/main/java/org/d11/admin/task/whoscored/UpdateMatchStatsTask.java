package org.d11.admin.task.whoscored;

import java.io.File;

import org.d11.admin.D11AdminProperties;
import org.d11.admin.download.whoscored.WhoScoredMatchSeleniumDownloader;
import org.d11.admin.model.Match;
import org.d11.admin.model.UpdateMatchStatsResult;
import org.d11.admin.model.whoscored.WSMatch;
import org.d11.admin.parse.whoscored.WhoScoredMatchParser;
import org.d11.admin.task.D11Task;
import org.d11.admin.write.whoscored.WhoScoredMatchWriter;
import org.d11.api.v1.D11API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class UpdateMatchStatsTask extends D11Task<UpdateMatchStatsResult> {

    private Match match;
    @Inject
    private WhoScoredMatchSeleniumDownloader downloader;
    @Inject
    private WhoScoredMatchParser parser;
    @Inject
    private WhoScoredMatchWriter writer;
    @Inject
    private D11API d11Api;
    private final static Logger logger = LoggerFactory.getLogger(UpdateMatchStatsTask.class);

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    @Override
    public boolean execute() {
        if(d11Api.isLoggedIn()) {
            logger.info("Updating match stats for match {}.", this.match.getWhoScoredId());

            downloader.setId(match.getWhoScoredId());
            downloader.setSeason(match.getSeasonName());
            downloader.setMatchDay(match.getMatchDayNumber());

            File htmlFile = downloader.download();
            downloader.close();

            if (htmlFile != null) {
                WSMatch wsMatch = parser.parse(htmlFile);

                if (wsMatch != null) {
                    writer.setSeason(match.getSeasonName());
                    writer.setMatchDayNumber(match.getMatchDayNumber());

                    File jsonFile = writer.write(wsMatch);

                    logger.info("Uploading match stats to {}.", getProperty(D11AdminProperties.API_HOST));

                    UpdateMatchStatsResult updateMatchStatsResult = d11Api.updateMatchStats(match, jsonFile);

                    logger.debug("Update complete: {}", updateMatchStatsResult);

                    setResult(updateMatchStatsResult);
                    return true;
                }
            }
        } else {
            logger.error("D11API user not logged in.");
        }
        return false;
    }

}
