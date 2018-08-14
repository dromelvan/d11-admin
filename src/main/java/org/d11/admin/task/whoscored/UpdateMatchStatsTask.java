package org.d11.admin.task.whoscored;

import java.io.File;

import org.d11.admin.download.whoscored.WhoScoredMatchSeleniumDownloader;
import org.d11.admin.model.Match;
import org.d11.admin.model.whoscored.WSMatch;
import org.d11.admin.parse.whoscored.WhoScoredMatchParser;
import org.d11.admin.read.whoscored.MatchReader;
import org.d11.admin.task.D11Task;
import org.d11.admin.write.whoscored.WhoScoredMatchWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class UpdateMatchStatsTask extends D11Task<Match> {

    private Match match;
    @Inject
    private WhoScoredMatchSeleniumDownloader downloader;
    @Inject
    private WhoScoredMatchParser parser;
    @Inject
    private WhoScoredMatchWriter writer;
    @Inject
    private MatchReader reader;
    private final static Logger logger = LoggerFactory.getLogger(UpdateMatchStatsTask.class);

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    @Override
    public boolean execute() {
        logger.info("Downloading match stats for match {}.", this.match.getWhoScoredId());

        downloader.setId(match.getWhoScoredId());
        downloader.setSeason(match.getSeasonName());
        downloader.setMatchDay(match.getMatchDayNumber());

        File htmlFile = downloader.download();
        downloader.close();

        if (htmlFile != null) {
            logger.info("Parsing HTML file {}.", htmlFile);
            WSMatch wsMatch = parser.parse(htmlFile);

            if (wsMatch != null) {
                writer.setSeason(match.getSeasonName());
                writer.setMatchDayNumber(match.getMatchDayNumber());

                File jsonFile = writer.write(wsMatch);

                // TODO Upload stats
                setResult(reader.read(jsonFile));
                return true;
            }
        }
        return false;
    }

}
